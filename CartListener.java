import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class CartListener implements ActionListener {

    private InventoryPOS pos;
    private ArrayList<Product> cartDataProduct;
    private double totalPrice = 0;
    public static int flagg = 0;


    public CartListener(InventoryPOS pos, ArrayList<Product> cartDataProduct) {
        this.pos = pos;
        this.cartDataProduct = cartDataProduct;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(pos.addToCart.getText())) {
            Product selectedProduct = pos.getSelectedProduct();
            if (selectedProduct != null) {
                cartDataProduct.add(selectedProduct);
                pos.cart_data.addElement(selectedProduct.getName() + "  " + selectedProduct.getPrice() + "€");
                totalPrice += selectedProduct.getPrice();
            }
        } else if (command.equals(pos.removeBtn.getText())) {
            int selectedIndex = pos.cartList.getSelectedIndex();
            if (selectedIndex != -1) {
                totalPrice -= cartDataProduct.get(selectedIndex).getPrice();
                cartDataProduct.remove(selectedIndex);
                pos.cart_data.remove(selectedIndex);
            }
        } else if (command.equals(pos.proceedPayment.getText())) {
            showPaymentDialog();
        }
    }


    private void showPaymentDialog() {
        JFrame frame = new JFrame("Payment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(560, 400);

        Object[] options = {"Credit", "Cash Payment", "Check", "Voucher"};
        int n = JOptionPane.showOptionDialog(frame,
                "Total Price: " + (flagg == 1 ? discountedPrice() : totalPrice) + "€\nSelect Payment Method:",
                "Payment Method",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        if (n == 0) {
            handleCreditPayment();
        } else if (n == 1) {
            handleCashPayment();
        } else if (n == 2) {
            handleCheckPayment();
        } else if (n == 3) {
            handleVoucher();
        }
    }

    private double discountedPrice() {
        return totalPrice * 0.8; // 20% discount
    }

    private void handleCreditPayment() {
        double bill = flagg == 1 ? discountedPrice() : totalPrice;
        JFrame frame = new JFrame("Credit");
        frame.setSize(560, 400);
        JOptionPane.showInputDialog(frame,
                "Bill: " + bill + "€ Enter Credit Card to Proceed!",
                "Credit",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void handleCashPayment() {
        double bill = flagg == 1 ? discountedPrice() : totalPrice;
        JFrame frame = new JFrame("Cash");
        frame.setSize(560, 400);
        Icon wallpater = null;
        JOptionPane.showMessageDialog(frame,
                "Bill: " + bill + "€ Payment will be received on delivery",
                "Success",
                JOptionPane.PLAIN_MESSAGE,
                wallpater);
    }

    private void handleCheckPayment() {
        double bill = flagg == 1 ? discountedPrice() : totalPrice;
        JFrame frame = new JFrame("Check");
        frame.setSize(560, 400);
        Icon wallpater = null;
        JOptionPane.showMessageDialog(frame,
                "Bill: " + bill + "€ Check will be received on delivery",
                "Success",
                JOptionPane.PLAIN_MESSAGE,
                wallpater);
    }

    private void handleVoucher() {
        JFrame frame = new JFrame("Voucher");
        frame.setSize(560, 400);
        String voucherCode = JOptionPane.showInputDialog(frame, 
                "Enter 20% Discount Voucher Code?", 
                "Voucher",
                JOptionPane.QUESTION_MESSAGE);
        
        if (voucherCode != null && !voucherCode.isEmpty()) {
            flagg = 1;
            Icon wallpater = null;
            JOptionPane.showMessageDialog(frame, 
                    "Voucher Applied", 
                    "Success", 
                    JOptionPane.PLAIN_MESSAGE,
                    wallpater);
        }
    }
}
