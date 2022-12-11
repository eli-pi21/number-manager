import java.util.*;

public class Radical {

    private int rooting;
    private int root;
    private int factor;
    private Map<Integer, Integer> rootingFactorization;

    public Radical(final int factor, final int root, final int rooting) {
        setRooting(rooting);
        this.root = root;
        this.rooting = rooting;
        setFactor(factor);
        this.rootingFactorization = Factorization.factorize(rooting);
    }

    public Radical(final int root, final int rooting) {
        this(1, root, rooting);
    }

    public Radical(final int factor) {
        this(factor, 1, 1);
    }

    public int getRooting() {
        return this.rooting;
    }

    public int getRoot() {
        return this.root;
    }

    public int getFactor() {
        return this.factor;
    }

    public void setRooting(final int rooting) {
        if (this.root % 2 == 0 && rooting < 0)
            throw new MathObjectException();
        this.rooting = rooting;
        this.rootingFactorization = Factorization.factorize(rooting);
    }

    public void setFactor(final int factor) {
        this.factor = factor;
    }

    public void setRoot(final int root) {
        this.root = root;
    }

    public Radical copy() {
        final Radical r = new Radical(this.getFactor(), this.getRoot(), this.getRooting());
        return r;
    }

    public void simplify() {
        int newFactor = 1;
        int newRooting = 1;
        for (final Map.Entry<Integer, Integer> entry : this.rootingFactorization.entrySet()) {
            final int exponentIn;
            if (entry.getValue() >= this.root) {
                final int exponentOut = entry.getValue() / this.root;
                exponentIn = entry.getValue() % this.root;
                newFactor *= (int) Math.pow(entry.getKey(), exponentOut);
            } else {
                exponentIn = entry.getValue();
            }
            newRooting *= (int) Math.pow(entry.getKey(), exponentIn);
        }
        this.setFactor(newFactor);
        this.setRooting(newRooting);
        this.simplifyExponentsWithRoot();
    }

    private void simplifyExponentsWithRoot() {
        final Set<Integer> exponents = new HashSet<>(this.rootingFactorization.values());
        exponents.add(getRoot());
        final int gcd = Factorization.greatestCommonDivisor(exponents);
        setRoot(getRoot() / gcd);
        for (final Map.Entry<Integer, Integer> entry : this.rootingFactorization.entrySet()) {
            this.rootingFactorization.replace(entry.getKey(), entry.getValue() / gcd);
        }
        setRooting(solveFactorization());
    }

    private int solveFactorization() {
        int result = 1;
        for (final Map.Entry<Integer, Integer> entry : this.rootingFactorization.entrySet()) {
            result *= (int) Math.pow(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public boolean isRational() {
        return this.rooting == 1 || this.rooting == -1 || this.root == 1;
    }

    public int getInteger() {
        if (!isRational())
            throw new MathObjectException();
        else if (this.rooting == -1)
            return -this.factor;
        else if (this.root == 1)
            return this.factor * this.rooting;
        else
            return this.factor;
    }

    public Radical takeFactorUnderRoot() { // Factor taken under root
        final int factorIn = (int) Math.pow(this.factor, this.root);
        final Radical r = new Radical(this.root, factorIn * this.rooting);
        return r;
    }

    public static Radical toRadical(final int number) {
        return new Radical(number);
    }

    private Radical multiplyExponentsAndRootFor(final int i) {
        final Radical r = copy();
        r.setRoot(r.getRoot() * i);
        for (final Map.Entry<Integer, Integer> entry : r.rootingFactorization.entrySet()) {
            r.rootingFactorization.replace(entry.getKey(), entry.getValue() * i);
        }
        r.setRooting(r.solveFactorization());
        return r;
    }

    public static Radical multiply(final Radical radical1, final Radical radical2) {
        final Radical allIn1 = radical1.takeFactorUnderRoot();
        final Radical allIn2 = radical2.takeFactorUnderRoot();
        final int lcm = Factorization.leastCommonMultiple(radical1.getRoot(), radical2.getRoot());
        final int factorRoot1 = lcm / radical1.getRoot();
        final int factorRoot2 = lcm / radical2.getRoot();
        final Radical r1 = allIn1.multiplyExponentsAndRootFor(factorRoot1);
        final Radical r2 = allIn2.multiplyExponentsAndRootFor(factorRoot2);
        final int rootingRes = r1.getRooting() * r2.getRooting();
        final Radical res = new Radical(lcm, rootingRes);
        res.simplify();
        return res;
    }

    @Override
    public String toString() {
        if (this.rooting == 1)
            return this.factor + "";
        if (this.rooting == 0 || this.factor == 0)
            return 0 + "";
        if (this.factor == 1)
            return "[" + this.root + "]√" + this.rooting + "";
        if (this.factor == -1)
            return "-[" + this.root + "]√" + this.rooting + "";
        return this.factor + "*[" + this.root + "]√" + this.rooting + "";
    }
}
