# FLAMEGRAPH_PLAYGROUND

https://github.com/petrbouda/async-profiler-playground

### Must Read

https://www.amazon.com/Performance-Tools-Addison-Wesley-Professional-Computing-ebook/dp/B081ZDXNL3/ref=sr_1_1?dchild=1&keywords=bpf&qid=1584993664&sr=8-1
- chapter 12 - Languages Java

### Presentations

- Sasha Goldshtein: [GOLDSHTEIN_SLIDES.md](slides/GOLDSHTEIN_SLIDES.pdf)
- Andrei Pangin: [PANGIN_SLIDES.md](slides/PANGIN_SLIDES.pdf)

### Inlining

https://www.baeldung.com/jvm-method-inlining

### Collecting Stack-traces
http://jeremymanson.blogspot.com/2013/07/lightweight-asynchronous-sampling.html
http://jeremymanson.blogspot.com/2010/07/why-many-profilers-have-serious.html

### FlameGraphs
https://github.com/brendangregg/FlameGraph
https://github.com/jvm-profiling-tools/async-profiler
https://medium.com/@maheshsenni/java-performance-profiling-using-flame-graphs-e29238130375
https://www.slideshare.net/brendangregg/java-performance-analysis-on-linux-with-flame-graphs
https://hackernoon.com/profiling-java-applications-with-async-profiler-049s2790

### PERF
https://perf.wiki.kernel.org/index.php/Tutorial
http://www.brendangregg.com/perf.html
https://shuheikagawa.com/blog/2018/09/16/node-js-under-a-microscope/

### PERF-MAP-AGENT
https://github.com/jvm-profiling-tools/perf-map-agent

- [ASYNC_PROFILER.md](ASYNC_PROFILER.md)
- [PERF](PERF.md)
- [PERF_JAVA_FLAMES](PERF_JAVA_FLAMES.md)

### FramePointer

[FRAME_POINTERS](FRAMEPOINTERS.md)

```
-XX:+PreserveFramePointer 

Selects between using the RBP register as a general purpose register (-XX:-PreserveFramePointer) 
and using the RBP register to hold the frame pointer of the currently executing method 
(-XX:+PreserveFramePointer). If the frame pointer is available, then external 
profiling tools (for example, Linux perf) can construct more accurate stack traces.
```

### De-optimization

- A process of replacing Compiled frames with Interpreter frames right on the Java stack on a running application.
- It happens in the context of a running Java Thread - trashes the currently running stack.
- It could cause not-walkable stack in the middle of this process.
- Use Java Flight Recorder to catch this:

```
jdk.Deoptimization {
  startTime = 12:33:57.271
  compileId = 1655
  compiler = "c2"
  method = jdk.jfr.internal.EventWriter.putStackTrace()
  lineNumber = 170
  bci = 7
  instruction = "ifeq"
  reason = "unstable_if"
  action = "reinterpret"
  eventThread = "JFR Periodic Tasks" (javaThreadId = 22)
  stackTrace = [
    jdk.jfr.internal.EventWriter.putStackTrace() line: 170
    jdk.jfr.internal.handlers.EventHandler1872_1577964835419-26342.write(long, long, long)
    jdk.jfr.events.ExceptionStatisticsEvent.commit()
    jdk.jfr.internal.instrument.JDKEvents.emitExceptionStatistics() line: 136
    jdk.jfr.internal.instrument.JDKEvents$$Lambda$37.1361501771.run()
    ...
  ]
}
```

## PERF_MAP_FILE

![PERF_MAP_FILE](img/PERF_MAP_FILE.png)

## Extras - Async-Profiler 

#### Wall-clock

- https://github.com/jvm-profiling-tools/async-profiler/issues/69
- https://github.com/jvm-profiling-tools/async-profiler/issues/187

####  Java Method Profiling

```
Java Classes:
-e java.util.Properties.getProperty

Native Classes:
-e Java_java_lang_Throwable_fillInStackTrace
```

#### Humongous Allocation

```
bpftrace -e "u:$JAVA_HOME/lib/server/libjvm.so:_ZN15G1CollectedHeap22humongous_obj_allocateEm { printf(\"Humongous allocation of %d bytes\n\", arg1 * 8 ); }"
```
```
profiler.sh -e _ZN15G1CollectedHeap22humongous_obj_allocateEm -d 10 -f humongous.svg `pgrep -f <application>`
```
![HUMONGOUS_ALLOCATION](img/extras/humongous.svg)

#### Exception Throwing

![EXCEPTION_HANDLING](img/extras/stacktraces.svg)

![EXCEPTION_HANDLING](img/extras/exception_handling.png)

#### Closing a Connection

![CLOSING_CONNECTION](img/extras/closing_connection.png)

#### Attaching VisualVM

![ATTACHING_VISUALVM](img/extras/attaching_visualvm.png)

#### GC Threads

![GC_THREADS](img/extras/gc_threads.png)

#### Code Instrumentation / GET_TIME to measure the instrumented time

![INSTRUMENTATION](img/extras/instrumentation.png)

#### Matched Time 

![MATCHED_TIME](img/extras/matched_time.png)

#### Thread Parking/Un-parking

![PARKING](img/extras/parking.png)

