import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Factorization {

    public static Map<Integer, Integer> factorize(int number) {
        final List<Integer> factorList = new ArrayList<>();
        final Map<Integer, Integer> factors = new HashMap<>();
        if (number == 0) {
            factors.put(0, 0);
            return factors;
        }
        int divider = 2;
        while (number != 1) {
            if (number % divider == 0) {
                number = number / divider;
                factorList.add(divider);
            } else {
                divider++;
            }
        } // There is not 1
        final Map<Integer, Long> map = factorList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (final Map.Entry<Integer, Long> entry : map.entrySet()) {
            factors.put(entry.getKey(), (int) (long) (entry.getValue()));
        }
        return factors;
    }

    public static int greatestCommonDivisor(final int a, final int b) {
        if (a == b)
            return a;
        else if (a > b)
            return greatestCommonDivisor(a - b, b);
        else
            return greatestCommonDivisor(a, b - a);
    }

    public static int greatestCommonDivisor(final Set<Integer> numbers) {
        final Iterator<Integer> iterator = numbers.iterator();
        int lastGdc = iterator.next();
        for (final Integer i : numbers) {
            lastGdc = Factorization.greatestCommonDivisor(i, lastGdc);
        }
        return lastGdc;
    }

    public static int leastCommonMultiple(final int a, final int b) {
        return a / greatestCommonDivisor(a, b) * b;
    }
}
