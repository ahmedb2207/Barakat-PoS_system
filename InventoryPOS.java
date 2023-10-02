import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.util.List;



@SuppressWarnings("serial")
public class InventoryPOS extends JFrame implements ListSelectionListener, CartObserver {

    private static InventoryPOS instance = null; // Singleton Pattern

    ImageIcon wallpater;

    JButton addItemInPOs = new JButton("Add Product");
    JButton setSpecialRequest = new JButton("Special Request");
    JButton removeBtn = new JButton("Remove Product");
    JButton addToCart = new JButton("Add to Cart");
    JButton proceedPayment = new JButton("Proceed Payment");

    DefaultListModel<String> data = new DefaultListModel<String>();
    ArrayList<Product> dataProduct = new ArrayList<>();

    DefaultListModel<String> cart_data = new DefaultListModel<String>();
    ArrayList<Product> cart_dataProduct = new ArrayList<>();

    JList<String> productList = new JList<String>(data);
    JList<String> cartList = new JList<String>(cart_data);

    JScrollPane jsp = new JScrollPane(productList);
    JScrollPane jsp1 = new JScrollPane(cartList);

    private Cart cart = new Cart();

    private InventoryPOS() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        p.add(jsp, BorderLayout.CENTER);
        productList.addListSelectionListener(this);
        p.add(jsp1, BorderLayout.EAST);
        cartList.addListSelectionListener(this);

        JPanel buttons = new JPanel();
        buttons.add(addItemInPOs);
        buttons.add(removeBtn);
        buttons.add(setSpecialRequest);
        buttons.add(addToCart);
        buttons.add(proceedPayment);

        p.add(buttons, BorderLayout.SOUTH);
        add(p);

        CartListener cartListener = new CartListener(this, cart_dataProduct);
        addToCart.addActionListener(cartListener);
        removeBtn.addActionListener(cartListener);
        proceedPayment.addActionListener(cartListener);

		String testing[] = {"Cheesburger", "Double Cheesburger","Fires", "Kepab", "Pizza","Ice Tea" , "Donut" , "Coca Cola" , "Salad" , "Sauce"};
		double pr[] = {2.50,3.50,2,5,10,2,2,2,4,1.5};
		for (int i = 0; i < 10; i++) {
			Product temp = new Product(testing[i],pr[i],"");
			String namePrice = temp.getName() + "  " + String.valueOf(temp.getPrice()) + "€";
			data.addElement(namePrice);
			dataProduct.add(temp);
		}

        addItemInPOs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("POS");
                String ProductName = JOptionPane.showInputDialog(frame, "Enter Product name", "Add Product", JOptionPane.PLAIN_MESSAGE);
                String Price = JOptionPane.showInputDialog(frame, "Enter Product Price", "Add Product Price", JOptionPane.PLAIN_MESSAGE);
                String special = JOptionPane.showInputDialog(frame, "Enter Product Specialities", "Add Product Specialities", JOptionPane.PLAIN_MESSAGE);

                if (ProductName != null && Price != null && special != null) {
                    int priccc = Integer.parseInt(Price);
                    Product product = ProductFactory.createProduct(ProductName, priccc, special);
                    String tempNamePrice = ProductName + "  " + Price + "€";
                    data.addElement(tempNamePrice);
                    dataProduct.add(product);
                }
            }
        });

        setSpecialRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = productList.getSelectedIndex();
                if (i > -1) {
                    String request$ = JOptionPane.showInputDialog(InventoryPOS.this, "What is your request?", "", JOptionPane.QUESTION_MESSAGE);
                    if (request$ != null) {
                        Product temp = dataProduct.get(i);
						temp.setSpecialRequest(request$);
                    }
                } else {
                    JOptionPane.showMessageDialog(InventoryPOS.this, "Kindly select item to add Instructions", "Attention", JOptionPane.ERROR_MESSAGE, wallpater);
                }
            }
        });

        cart.addObserver(this); // Observer Pattern
    }

    public static InventoryPOS getInstance() {
        if (instance == null) {
            instance = new InventoryPOS();
        }
        return instance;
    }

    public Product getSelectedProduct() {
        int i = productList.getSelectedIndex();
        if (i > -1) {
            return dataProduct.get(i);
        }
        return null;
    }

    public static void main(String[] args) {
        InventoryPOS app = InventoryPOS.getInstance();
        app.setSize(1000, 700);
        app.setTitle(app.getClass().getCanonicalName());
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setFocus();
    }

    private void setFocus() {
        addItemInPOs.requestFocus();
    }

    public void valueChanged(ListSelectionEvent lse) {
    }

    @Override
	public void updateCartItems(List<Product> cartItems) {
		cart_data.clear();
		cart_dataProduct.clear();
		for (Product product : cartItems) {
			cart_data.addElement(product.getName() + "  " + product.getPrice() + "€");
			cart_dataProduct.add(product);
		}
	}

}
