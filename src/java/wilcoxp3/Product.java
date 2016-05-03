package wilcoxp3;

/*
 * Paul Wilcox 
 * Module 3 Project 
 * This application allows the user to manage an
 * inventory of products. The user may add view a product's information, add a
 * new product to the inventory, update information for an existing product, or
 * delete a product from the inventory.
 */
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Product class represents a product in the inventory, and contains fields
 * for the UPC code, short details, long details, the price, and the quantity in
 * stock. It also overrides compareTo to compare products by their UPCs.
 *
 * @author wilcoxp3
 */
public class Product implements Comparable<Product>, Serializable {

    private String upc;
    private String shortDetails;
    private String longDetails;
    private BigDecimal price;
    private Integer stock;

    /**
     * @return the UPC code.
     */
    public String getUpc() {
        return upc;
    }

    /**
     * @param upc the UPC code to set
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }

    /**
     * @return the short details
     */
    public String getShortDetails() {
        return shortDetails;
    }

    /**
     * @param shortDetails the short details to set
     */
    public void setShortDetails(String shortDetails) {
        this.shortDetails = shortDetails;
    }

    /**
     * @return the long details
     */
    public String getLongDetails() {
        return longDetails;
    }

    /**
     * @param longDetails the long details to set
     */
    public void setLongDetails(String longDetails) {
        this.longDetails = longDetails;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the quantity in stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock the quantity in stock to set
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     *
     * This method returns the int comparison of two products based on the UPC
     * code of each product.
     *
     * @param p
     * @return the comparison of the two product's upcs
     */
    @Override
    public int compareTo(Product p) {
        return this.getUpc().compareToIgnoreCase(p.getUpc());
    }

    /**
     * This method returns the value of each field in a string formatted for
     * viewing in a web browser.
     *
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPC: ");
        builder.append(this.getUpc());
        builder.append("<br>Short Details: ");
        builder.append(this.getShortDetails());
        builder.append("<br>Long Details: ");
        builder.append(this.getLongDetails());
        builder.append("<br>Price: ");
        builder.append(this.getPrice());
        builder.append("<br>Stock: ");
        builder.append(this.getStock());
        builder.append("<br><br>");
        return builder.toString();
    }
}
