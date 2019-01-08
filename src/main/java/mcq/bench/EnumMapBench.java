package mcq.bench;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;
import static org.openjdk.jmh.annotations.Scope.Thread;

/**
 * Test to see how fast is a {@link java.util.EnumMap} compared to a {@link java.util.HashMap}.
 * Results on my mac (2018) i7 2.2 GHz 16 GB:
 * <pre>
 *     Benchmark                Mode  Cnt  Score   Error  Units
 *     EnumMapBench.baseLine    avgt    5  0.256 ± 0.002  ns/op
 *     EnumMapBench.enumMapGet  avgt    5  2.832 ± 0.068  ns/op
 *     EnumMapBench.hashMapGet  avgt    5  2.821 ± 0.037  ns/op
 * </pre>
 */
@State(Thread)
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
        return enumMap.get(MyEnum.E3);
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
