import javax.swing.Icon;

public class Product {

    public static final Icon productImage = null;
    private String name;
    private double price;
    private String specialRequest;
    
    Product() {
        name = "";
        price = 0;
        specialRequest = "";
    }
    
    Product(String n, double pr, String sp) {
        name = n;
        price = pr;
        specialRequest = sp;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }
	public void setSpecialRequest(String request) {
		this.specialRequest = request;
	}
    

}
