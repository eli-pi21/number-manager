public class Main {

    public static void main(final String[] args) {
        final Radical r = new Radical(6, 8);
        final Radical d = r.copy();
        d.simplify();
        System.out.println(r);
        System.out.println(d);
        final Radical t = new Radical(2, 5);
        final Radical s = new Radical(3, 4);
        final Radical f = Radical.multiply(t, s);
        System.out.println(f);

    }

}
