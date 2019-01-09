package mcq.bench;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;
import static org.openjdk.jmh.annotations.Scope.Benchmark;

/**
 * Test to see how fast is a synchronized {@link EnumMap} compared to a {@link java.util.concurrent.ConcurrentHashMap}.
 * Results on a mac (2018) i7 2.2 GHz 6 cores 16 GB with one thread:
 * <pre>
 *      Benchmark                                       Mode  Cnt   Score   Error  Units
 *      SynchronizedEnumMapBench.baseLine               avgt    5   0.256 ± 0.003  ns/op
 *      SynchronizedEnumMapBench.synchronizedEnumMapGet avgt    5  17.136 ± 0.313  ns/op
 *      SynchronizedEnumMapBench.concurrentHashMapGet   avgt    5   4.346 ± 0.071  ns/op
 * </pre>
 * With 3 threads:
 * <pre>
 *      Benchmark                                       Mode  Cnt   Score   Error  Units
 *      SynchronizedEnumMapBench.baseLine               avgt    5    0.258 ±  0.001  ns/op
 *      SynchronizedEnumMapBench.synchronizedEnumMapGet avgt    5  102.402 ±  3.991  ns/op
 *      SynchronizedEnumMapBench.concurrentHashMapGet   avgt    5    4.481 ±  0.046  ns/op
 * </pre>
 * With 6 threads:
 * <pre>
 *      Benchmark                                       Mode  Cnt   Score   Error  Units
 *      SynchronizedEnumMapBench.baseLine               avgt    5    0.297 ± 0.007  ns/op
 *      SynchronizedEnumMapBench.synchronizedEnumMapGet avgt    5  314.540 ± 1.833  ns/op
 *      SynchronizedEnumMapBench.concurrentHashMapGet   avgt    5    5.472 ± 0.600  ns/op
 * </pre>
 */
@State(Benchmark)
@BenchmarkMode(AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = SECONDS)
@Fork(1)
public class SynchronizedEnumMapBench {
    private Map<MyEnum, Object> synchronizedEnumMap;
    private Map<MyEnum, Object> concurrentHashMap;

    @Benchmark
    public void baseLine() {
    }

    @Benchmark
    public Object synchronizedEnumMapGet() {
        return synchronizedEnumMap.get(MyEnum.E3);
    }

    @Benchmark
    public Object concurrentHashMapGet() {
        return concurrentHashMap.get(MyEnum.E3);
    }

    @Setup
    public void setup() {
        synchronizedEnumMap = Collections.synchronizedMap(new EnumMap<>(MyEnum.class));
        synchronizedEnumMap.put(MyEnum.E1, new Object());
        synchronizedEnumMap.put(MyEnum.E2, new Object());
        synchronizedEnumMap.put(MyEnum.E3, new Object());
        synchronizedEnumMap.put(MyEnum.E4, new Object());
        synchronizedEnumMap.put(MyEnum.E5, new Object());

        concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(MyEnum.E1, new Object());
        concurrentHashMap.put(MyEnum.E2, new Object());
        concurrentHashMap.put(MyEnum.E3, new Object());
        concurrentHashMap.put(MyEnum.E4, new Object());
        concurrentHashMap.put(MyEnum.E5, new Object());
    }

    enum MyEnum {E1, E2, E3, E4, E5}

}
