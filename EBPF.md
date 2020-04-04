# eBPF_TOOLS Profiling  

### DebugInfo

- Files with the same name as binary and with .debuginfo extension
- `openjdk-11-jre` (libjvm.so) > `openjdk-11-dbg` (libjvm.debuginfo)
- Now we are supposed to see frames belonging to JVM

### Symbol Tables
 
- `perf-map-agent` https://github.com/jvm-profiling-tools/perf-map-agent
- must be generated on demand and can be quickly outdated
- JVM can recompile methods and invalidate our Symbol Table
- keep short period between SymbolTable generation and running BPF tools
- agent can be callend inside BPFTRACE scripts

