
public class ProductFactory {
    public static Product createProduct(String name, double price, String specialities) {
        return new Product(name, price, specialities);
    }
}
