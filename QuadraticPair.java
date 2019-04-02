public class QuadraticPair {
    private double c1;
    private double c2;

    public QuadraticPair(double c1, double c2){
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final QuadraticPair other = (QuadraticPair) obj;
        if (this.c1 != other.c1 || this.c2 != other.c2)
            return false;
        else
            return true;
    }

    @Override
    public int hashCode() {
        String temp = "" + c1 + c2;
        return temp.hashCode();
    }

    @Override
    public String toString(){
        return "(" + String.format("%.1f", c1) + ", " + String.format("%.1f", c2) + ")";
    }
}
