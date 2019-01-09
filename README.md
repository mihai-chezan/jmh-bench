# Some JMH benchmarks
See https://openjdk.java.net/projects/code-tools/jmh/ for details about JMH.

## [EnumMap vs HashMap](/src/main/java/mcq/bench/EnumMapBench.java)
Test to compare [`EnumMap`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/EnumMap.html) vs. [`HashMap`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashMap.html).

**TLDR: the advice to use a `EnumMap` instead of a `HashMap` makes sense when you have a _Enum_ as key.**
### Results on a [mac (2018) i7-8750H 2.2 GHz 6 cores 16 GB](https://ark.intel.com/products/134906/Intel-Core-i7-8750H-Processor-9M-Cache-up-to-4-10-GHz-):

#### Singler thread:

| Benchmark                  | Mode | Cnt  | Score     | Error     | Units |
|----------------------------|------|-----:|----------:|----------:|-------|
| EnumMapBench.baseLine      | avgt | 5    | 0.256     | ± 0.006   | ns/op |
| EnumMapBench.enumMapGet    | avgt | 5    | 2.809     | ± 0.032   | ns/op |
| EnumMapBench.hashMapGet    | avgt | 5    | 4.337     | ± 0.078   | ns/op |

#### With 3 threads:

| Benchmark                  | Mode | Cnt  | Score     | Error     | Units |
|----------------------------|------|-----:|----------:|----------:|-------|
| EnumMapBench.baseLine      | avgt | 5    | 0.258     | ± 0.001   | ns/op |
| EnumMapBench.enumMapGet    | avgt | 5    | 2.957     | ± 0.191   | ns/op |
| EnumMapBench.hashMapGet    | avgt | 5    | 4.668     | ± 0.336   | ns/op |

#### With 6 threads:

| Benchmark                  | Mode | Cnt  | Score     | Error     | Units |
|----------------------------|------|-----:|----------:|----------:|-------|
| EnumMapBench.baseLine      | avgt | 5    | 0.277     | ± 0.035   | ns/op |
| EnumMapBench.enumMapGet    | avgt | 5    | 3.641     | ± 0.415   | ns/op |
| EnumMapBench.hashMapGet    | avgt | 5    | 5.549     | ± 0.131   | ns/op |


But what about when you need this in a multithreaded usage scenario? `EnumMap` java docs just says that you shoud use `Collections.synchronizedMap` but it doesn't mention anything about performance compared with a [`ConcurrentHashMap`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html).

So let's compare that.

## [synchronized EnumMap vs ConcurrentHashMap](/src/main/java/mcq/bench/SynchronizedEnumMapBench.java)
Test to compare [`Collections.synchronizedMap(EnumMap)`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/EnumMap.html) vs. [`ConcurrentHashMap`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html).

**TLDR: Use `ConcurrentHashMap` instead of `Collections.synchronizedMap(EnumMap)`.**
### Results on a [mac (2018) i7-8750H 2.2 GHz 6 cores 16 GB](https://ark.intel.com/products/134906/Intel-Core-i7-8750H-Processor-9M-Cache-up-to-4-10-GHz-):

#### Singler thread:

| Benchmark                           | Mode | Cnt  | Score     | Error     | Units |
|-------------------------------------|------|-----:|----------:|----------:|-------|
| EnumMapBench.baseLine               | avgt | 5    | 0.256     | ± 0.003   | ns/op |
| EnumMapBench.synchronizedEnumMapGet | avgt | 5    | 17.136    | ± 0.313   | ns/op |
| EnumMapBench.concurrentHashMapGet   | avgt | 5    | 4.346     | ± 0.071   | ns/op |

#### With 3 threads:

| Benchmark                           | Mode | Cnt  | Score     | Error     | Units |
|-------------------------------------|------|-----:|----------:|----------:|-------|
| EnumMapBench.baseLine               | avgt | 5    | 0.258     | ± 0.001   | ns/op |
| EnumMapBench.synchronizedEnumMapGet | avgt | 5    | 102.40    | ± 3.991   | ns/op |
| EnumMapBench.concurrentHashMapGet   | avgt | 5    | 4.481     | ± 0.046   | ns/op |

#### With 6 threads:

| Benchmark                           | Mode | Cnt  | Score     | Error     | Units |
|-------------------------------------|------|-----:|----------:|----------:|-------|
| EnumMapBench.baseLine               | avgt | 5    | 0.297     | ± 0.007   | ns/op |
| EnumMapBench.synchronizedEnumMapGet | avgt | 5    | 314.540   | ± 1.833   | ns/op |
| EnumMapBench.concurrentHashMapGet   | avgt | 5    | 5.472     | ± 0.600   | ns/op |

