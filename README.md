# FLAMEGRAPH_PLAYGROUND

### Inlining

https://www.baeldung.com/jvm-method-inlining

### FlameGraphs
https://github.com/brendangregg/FlameGraph
https://github.com/jvm-profiling-tools/async-profiler
https://medium.com/@maheshsenni/java-performance-profiling-using-flame-graphs-e29238130375
https://www.slideshare.net/brendangregg/java-performance-analysis-on-linux-with-flame-graphs

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

![FRAME_POINTERS](FRAMEPOINTERS.md)

```
-XX:+PreserveFramePointer 

Selects between using the RBP register as a general purpose register (-XX:-PreserveFramePointer) 
and using the RBP register to hold the frame pointer of the currently executing method 
(-XX:+PreserveFramePointer). If the frame pointer is available, then external 
profiling tools (for example, Linux perf) can construct more accurate stack traces.
```

## Extras - Async-Profiler 

#### Humongous Allocation

![HUMONGOUS_ALLOCATION](img/extras/humongous_allocation.png)

#### Exception Throwing

![EXCEPTION_HANDLING](img/extras/exception_handling.png)

#### Closing a Connection

![CLOSING_CONNECTION](img/extras/closing_connection.png)

#### Attaching VisualVM

![ATTACHING_VISUALVM](img/extras/attaching_visualvm.png)