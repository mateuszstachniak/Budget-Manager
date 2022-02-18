package budget;

import java.util.Comparator;

public class PurchaseComparator implements Comparator<Purchase> {

    @Override
    public int compare(Purchase t, Purchase t1) {
        Double price1 = Double.parseDouble(t.getPrice());
        Double price2 = Double.parseDouble(t1.getPrice());
        if (price1 > price2) return -1;
        if (price1 < price2) return 1;
        else {
            return t1.getName().compareTo(t.getName());

        }
    }
}