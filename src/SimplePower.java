import java.util.Objects;

public class SimplePower {

    private int base;
    private Fraction exponent;

    public SimplePower(final int base, final Fraction exponent) {
        if (base < 0)
            throw new MathObjectException();
        this.base = base;
        this.exponent = exponent;
    }

    public SimplePower(final int base) {
        this(base, Fraction.toFraction(1));
    }

    public int getBase() {
        return this.base;
    }

    public Fraction getExponent() {
        return this.exponent;
    }
    
    public int getNumeratorExponent() {
        return this.exponent.getNumerator();
    }

    public void setBase(final int base) {
        this.base = base;
    }

    public void setExponent(final Fraction exponent) {
        this.exponent = exponent;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerBasePower that = (IntegerBasePower) o;
        return base == that.base &&
                Objects.equals(exponent, that.exponent);
    }
     */

    @Override
    public int hashCode() {
        return Objects.hash(this.base, this.exponent);
    }
    
    /*
    public void setNumeratorExponent(final int numeratorExponent) {
        this.exponent.setNumerator(numeratorExponent);
    }
    */


}
