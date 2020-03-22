import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Script expects to have those libraries on path:
 * - Perf
 * - https://github.com/brendangregg/FlameGraph
 * - https://github.com/jvm-profiling-tools/perf-map-agent
 */
public class Flames {

    private static final Duration PAUSE = Duration.ofSeconds(1);
    private static final String PID_FORMAT = "pgrep -f %s";
    private static final String PERF_RECORD_FORMAT = "perf record -F %s -p %s -o %s.data -g  -- sleep %s";
    private static final String PERF_SCRIPT_FORMAT = "perf script -i %s.data --header > %s.perf";
    private static final String PERF_MAP_AGENT_FORMAT = "create-java-perf-map.sh %s";
    private static final String STACK_COLLAPSED_FORMAT = "stackcollapse-perf.pl --all %s.perf > %s.folded";
    private static final String FLAMEGRAPH_FORMAT = "flamegraph.pl --color=java --hash %s.folded > %s.svg";
    private static final String CLEANUP_FORMAT = "rm %s.data %s.folded";

    public static void main(String[] args) throws Exception {
        Context context = resolveContext(args);

        List<Action> actions = List.of(
                new ResolvePid(),
                new PerfRecord(),
                new PerfMapAgent(),
                new PerfScript(),
                new StackCollapsed(),
                new FlameGraph(),
                new Cleanup());

        for (Action action : actions) {
            action.run(context);
            Thread.sleep(PAUSE.toMillis());
            System.out.println("\n ------------------------------------ \n");
        }
    }

    private static class Context {
        private final int frequency;
        private final String name;
        private final String output;
        private final Duration duration;

        private int pid;

        public Context(int frequency, String name, String output, Duration duration) {
            this.frequency = frequency;
            this.name = name;
            this.output = output;
            this.duration = duration;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }

    private static abstract class Action {
        abstract void run(Context context) throws Exception;

        void runProcess(String command) throws Exception {
            System.out.println("$ " + command);
            Process process = new ProcessBuilder()
                    .command("bash", "-c", command)
                    .inheritIO()
                    .start();

            process.waitFor();

            int exitValue = process.exitValue();
            if (exitValue != 0) {
                throw new RuntimeException("Command: '" + command + "' throw an exception");
            }
        }
    }

    private static class ResolvePid extends Action {
        @Override
        public void run(Context context) throws Exception {
            String pidCommand = String.format(PID_FORMAT, context.name);
            int pid = resolvePid(pidCommand).orElseThrow(() -> new IllegalArgumentException("Cannot find a PID"));
            System.out.println("Resolved PID: " + pid);
            context.setPid(pid);
        }

        private static Optional<Integer> resolvePid(String command) throws IOException {
            System.out.println("$ " + command);
            return new ProcessBuilder()
                    .command("bash", "-c", command)
                    .start()
                    .onExit()
                    .thenApply(ResolvePid::readPid)
                    .join();
        }

        private static Optional<Integer> readPid(Process process) {
            BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return output.lines().findFirst().map(Integer::parseInt);
        }
    }

    private static class PerfRecord extends Action {
        @Override
        void run(Context context) throws Exception {
            String command = String.format(
                    PERF_RECORD_FORMAT, context.frequency, context.pid, context.output, context.duration.toSeconds());
            runProcess(command);
        }
    }

    private static class PerfMapAgent extends Action {
        @Override
        void run(Context context) throws Exception {
            String command = String.format(PERF_MAP_AGENT_FORMAT, context.pid);
            runProcess(command);
        }
    }

    private static class PerfScript extends Action {
        @Override
        void run(Context context) throws Exception {
            String command = String.format(PERF_SCRIPT_FORMAT, context.output, context.output);
            runProcess(command);
        }
    }

    private static class StackCollapsed extends Action {
        @Override
        void run(Context context) throws Exception {
            String command = String.format(STACK_COLLAPSED_FORMAT, context.output, context.output);
            runProcess(command);
        }
    }

    private static class FlameGraph extends Action {
        @Override
        void run(Context context) throws Exception {
            String command = String.format(FLAMEGRAPH_FORMAT, context.output, context.output);
            runProcess(command);
        }
    }

    private static class Cleanup extends Action {
        @Override
        void run(Context context) throws Exception {
            String command = String.format(CLEANUP_FORMAT, context.output, context.output);
            runProcess(command);
        }
    }

    private static Context resolveContext(String[] args) {
        Map<Flag, String> params = resolveFlags(args);
        return new Context(
                Integer.parseInt(params.get(Flag.FREQUENCY)),
                params.get(Flag.NAME),
                params.get(Flag.OUTPUT),
                Duration.ofSeconds(Integer.parseInt(params.get(Flag.DURATION))));
    }

    private static Map<Flag, String> resolveFlags(String[] args) {
        /*
         * Resolve only incoming params
         */
        Map<Flag, String> params = new EnumMap<>(Flag.class);
        for (int i = 0; i < args.length; i++) {
            String name = args[i];
            String value = args[++i];
            params.put(Flag.FLAGS.get(name), value);
        }

        /*
         * Fill in default values.
         */
        Map<Flag, String> finalParams = new EnumMap<>(Flag.class);
        for (Flag flag : Flag.values()) {
            String value = params.get(flag);
            if (value == null) {
                Object defaultValue = flag.defaultValue;
                if (defaultValue == null) {
                    Flag pointerValue = flag.defaultPointerValue;
                    if (pointerValue != null) {
                        value = finalParams.get(pointerValue);
                    } else {
                        throw new InvalidParameterException("Flag '" + flag + "' is mandatory");
                    }
                } else {
                    value = defaultValue.toString();
                }
            }

            finalParams.put(flag, value);
        }

        return finalParams;
    }

    private enum Flag {
        NAME("-n"),
        FREQUENCY("-f", 99),
        DURATION("-d", 60),
        OUTPUT("-o", NAME);

        private final String shortcut;
        private Object defaultValue;
        private Flag defaultPointerValue;

        private static final Map<String, Flag> FLAGS;

        static {
            FLAGS = Arrays.stream(values())
                    .collect(Collectors.toUnmodifiableMap(flag -> flag.shortcut, Function.identity()));
        }

        Flag(String shortcut) {
            this.shortcut = shortcut;
        }

        Flag(String shortcut, Flag defaultPointerValue) {
            this.shortcut = shortcut;
            this.defaultPointerValue = defaultPointerValue;
        }

        Flag(String shortcut, Object defaultValue) {
            this.shortcut = shortcut;
            this.defaultValue = defaultValue;
        }
    }
}
