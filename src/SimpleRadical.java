import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleRadical {

    private Fraction rooting;
    private int root;

    public SimpleRadical(final int root, final Fraction rooting) {
        setRooting(rooting);
        setRoot(root);
    }

    public Fraction getRooting() {
        return this.rooting;
    }

    public int getRoot() {
        return this.root;
    }

    public void setRooting(final Fraction rooting) {
        if (this.root % 2 == 0 && rooting.isNegative())
            throw new MathObjectException();
        this.rooting = rooting;
    }

    public void setRoot(final int root) {
        this.root = root;
    }

    public SimpleRadical copy() {
        final SimpleRadical r = new SimpleRadical(this.getRoot(), this.getRooting());
        return r;
    }

    public boolean isRational() {
        return this.rooting.equals(1) || this.rooting.equals(-1) || this.root == 1;
    }
}
