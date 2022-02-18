package budget;

public class Purchase {
    private Category category;
    private String name;
    private String price;

    public Purchase(Category category, String name, String price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " $" + price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
