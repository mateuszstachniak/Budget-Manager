package budget;

public class TypeSorter implements Comparable<TypeSorter> {
    String type;
    double sum;

    public TypeSorter(String type, double sum) {
        this.type = type;
        this.sum = sum;
    }

    @Override
    public int compareTo(TypeSorter typeToSum) {
        if (sum > typeToSum.sum) return -1;
        if (sum < typeToSum.sum) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return type + " - $" + String.format("%.2f", sum);
    }
}
