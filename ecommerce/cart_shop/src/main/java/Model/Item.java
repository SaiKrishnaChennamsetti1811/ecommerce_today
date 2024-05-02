package Model;

public class Item {
    private String proid;
    private int quantity;

    // Constructor
    public Item(String proid, int quantity) {
        this.proid = proid;
        this.quantity = quantity;
    }

    // Getter for proid
    public String getProid() {
        return proid;
    }

    // Setter for proid (optional, if needed)
    public void setProid(String proid) {
        this.proid = proid;
    }

    // Getter for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for quantity (optional, if needed)
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "proid='" + proid + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
