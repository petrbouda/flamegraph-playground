# OFF-CPU FLAMEGRAPHS

- http://www.brendangregg.com/offcpuanalysis.html
- http://www.brendangregg.com/blog/2015-02-26/linux-perf-off-cpu-flame-graph.html
- http://www.brendangregg.com/FlameGraphs/offcpuflamegraphs.html
- http://youtu.be/nZfNehCzGdw?t=1h11m52s

- Install: https://github.com/iovisor/bcc

### Problems with CPU

- `Real Code-Path`: Very hard to find a full reason of a blocking situation, another thread blocked the first one
- `Scheduler Latency`: Saturated CPU blocked a runnable threads which is waiting for the CPU
- `Involuntary Context-Switching`: The CPU time is saturated and thread needs to be kicked off, 
even in the middle of heavy code-path, off-CPU stack-trace does not mean any sense.

```
$ offcputime -df -p `pgrep -f OffCpuThreadPool` 30 > out.stacks
$ flamegraph.pl --color=io --countname=us < out.stacks > out.svg
```