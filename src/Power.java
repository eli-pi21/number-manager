import java.util.*;

public class Power {

    private final Set<SimplePower> numeratorFactorization;
    private final Set<SimplePower> denominatorFactorization;

    public Power(final Fraction base, final Fraction exponent) {
        final Set<SimplePower> baseNumFactorization = base.getNumeratorFactorization();
        final Set<SimplePower> baseDenFactorization = base.getDenominatorFactorization();
        for (final SimplePower p : baseNumFactorization) {
            p.setExponent(Fraction.multiply(p.getExponent(), exponent));
        }
        for (final SimplePower p : baseDenFactorization) {
            p.setExponent(Fraction.multiply(p.getExponent(), exponent));
        }
        this.numeratorFactorization = baseDenFactorization;
        this.denominatorFactorization = baseDenFactorization;
    }

    public Power(final Set<SimplePower> numeratorFactorization, final Set<SimplePower> denominatorFactorization) {
        this.numeratorFactorization = numeratorFactorization;
        this.denominatorFactorization = denominatorFactorization;
    }

    public void simplify() {
        for (final SimplePower p : this.numeratorFactorization) {
            p.getExponent().toLowestTerms();
        }
        for (final SimplePower p : this.denominatorFactorization) {
            p.getExponent().toLowestTerms();
        }
    }

    private static Map<Integer, Fraction> getMapFromFactorization(final Set<SimplePower> set) {
        final Map<Integer, Fraction> map = new HashMap<>();
        for (final SimplePower x : set) {
            map.put(x.getBase(), x.getExponent());
        }
        return map;
    }

    private static Set<SimplePower> getFactorizationFromMap(final Map<Integer, Fraction> map) {
        final Set<SimplePower> set = new HashSet<>();
        for (final Map.Entry<Integer, Fraction> entry : map.entrySet()) {
            set.add(new SimplePower(entry.getKey(), entry.getValue()));
        }
        return set;
    }

    private static Map<Integer, Fraction> findAndMultiplySinglePowers(final Map<Integer, Fraction> map1, final Map<Integer, Fraction> map2) {
        final Map<Integer, Fraction> map = new HashMap<>();
        for (final Map.Entry<Integer, Fraction> entry : map1.entrySet()) {
            if (map2.containsKey(entry.getKey())) {
                final Fraction newExp = Fraction.add(entry.getValue(), map2.get(entry.getKey()));
                map.put(entry.getKey(), newExp);
            }
        }
        map.putAll(map1);
        map.putAll(map2);
        return map;
    }

    public static Power multiply(final Power p1, final Power p2) {

        final Map<Integer, Fraction> mapNum1 = getMapFromFactorization(p1.numeratorFactorization);
        final Map<Integer, Fraction> mapNum2 = getMapFromFactorization(p2.numeratorFactorization);
        final Map<Integer, Fraction> num = findAndMultiplySinglePowers(mapNum1, mapNum2);

        final Map<Integer, Fraction> mapDen1 = getMapFromFactorization(p1.denominatorFactorization);
        final Map<Integer, Fraction> mapDen2 = getMapFromFactorization(p2.denominatorFactorization);
        final Map<Integer, Fraction> den = findAndMultiplySinglePowers(mapDen1, mapDen2);

        final Set<SimplePower> numFact = getFactorizationFromMap(num);
        final Set<SimplePower> denFact = getFactorizationFromMap(den);

        return new Power(numFact, denFact);
    }

    /*
    public Fraction raise() {
        final int num = 1;
    }

    private static int getSimplePowersProduct(final Set<SimplePower> set) {
        int num = 1;
        for (final SimplePower p : set) {
            num *= Math.pow(p.getBase(), p.getNumeratorExponent());
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowerFactorized that = (PowerFactorized) o;
        return Objects.equals(numeratorFactorization, that.numeratorFactorization) &&
                Objects.equals(denominatorFactorization, that.denominatorFactorization);
    }
    */

/*
    public void raise() {
        final int baseNum = (int) Math.pow(this.base.getNumerator(), this.exponent.getNumerator());
        final int baseDen = (int) Math.pow(this.base.getNumerator(), this.exponent.getDenominator());
        setBase(new Fraction(baseNum, baseDen));
        setExponent(new Fraction(1, this.exponent.getDenominator()));
    }
    
 */
}
