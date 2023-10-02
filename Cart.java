import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> items;
    private List<CartObserver> observers;

    public Cart() {
        items = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void addObserver(CartObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CartObserver observer) {
        observers.remove(observer);
    }

    public void addItem(Product product) {
        items.add(product);
        notifyObservers();
    }

    public void removeItem(Product product) {
        items.remove(product);
        notifyObservers();
    }

    private void notifyObservers() {
        for (CartObserver observer : observers) {
            observer.updateCartItems(items);
        }
    }

    public List<Product> getItems() {
        return items;
    }
}
