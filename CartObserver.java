import java.util.ArrayList;
import java.util.List;

public interface CartObserver {
    void updateCartItems(List<Product> cartItems);
}
