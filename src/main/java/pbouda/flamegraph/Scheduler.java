package pbouda.flamegraph;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    public static void main(String[] args) {
        NamedThreadFactory threadFactory = new NamedThreadFactory("custom-thread");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(threadFactory);

        executor.scheduleAtFixedRate(new ComputeTask(), 0, 1, TimeUnit.SECONDS);
    }
}
