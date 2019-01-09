package mcq.bench;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.EnumMap;
import java.util.HashMap;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;
import static org.openjdk.jmh.annotations.Scope.Benchmark;

/**
 * Test to see how fast is a {@link java.util.EnumMap} compared to a {@link java.util.HashMap}.
 * Results on a mac (2018) i7 2.2 GHz 6 cores 16 GB:
 <pre>
 *      Benchmark                            Mode  Cnt   Score   Error  Units
 *      EnumMapBench.baseLine                avgt    5   0.256 ± 0.006  ns/op
 *      EnumMapBench.enumMapGet              avgt    5   2.809 ± 0.032  ns/op
 *      EnumMapBench.hashMapGet              avgt    5   4.337 ± 0.078  ns/op
 * </pre>
 * With 3 threads:
 * <pre>
 *      Benchmark                            Mode  Cnt   Score   Error  Units
 *      EnumMapBench.baseLine                avgt    5    0.258 ±  0.001  ns/op
 *      EnumMapBench.enumMapGet              avgt    5    2.957 ±  0.191  ns/op
 *      EnumMapBench.hashMapGet              avgt    5    4.668 ±  0.336  ns/op
 * </pre>
 * With 6 threads:
 * <pre>
 *      Benchmark                Mode  Cnt  Score   Error  Units
 *      EnumMapBench.baseLine                avgt    5    0.277 ± 0.035  ns/op
 *      EnumMapBench.enumMapGet              avgt    5    3.641 ± 0.415  ns/op
 *      EnumMapBench.hashMapGet              avgt    5    5.549 ± 0.131  ns/op
 * </pre>
 */
@State(Benchmark)
@BenchmarkMode(AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = SECONDS)
@Fork(1)
public class EnumMapBench {
    private EnumMap<MyEnum, Object> enumMap;
    private HashMap<MyEnum, Object> hashMap;

    @Benchmark
    public void baseLine() {
    }

    @Benchmark
    public Object enumMapGet() {
        return enumMap.get(MyEnum.E3);
    }

    @Benchmark
    public Object hashMapGet() {
        return hashMap.get(MyEnum.E3);
    }

    @Setup
    public void setup() {
        enumMap = new EnumMap<>(MyEnum.class);
        enumMap.put(MyEnum.E1, new Object());
        enumMap.put(MyEnum.E2, new Object());
        enumMap.put(MyEnum.E3, new Object());
        enumMap.put(MyEnum.E4, new Object());
        enumMap.put(MyEnum.E5, new Object());

        hashMap = new HashMap<>();
        hashMap.put(MyEnum.E1, new Object());
        hashMap.put(MyEnum.E2, new Object());
        hashMap.put(MyEnum.E3, new Object());
        hashMap.put(MyEnum.E4, new Object());
        hashMap.put(MyEnum.E5, new Object());
    }

    enum MyEnum {E1, E2, E3, E4, E5}

}
