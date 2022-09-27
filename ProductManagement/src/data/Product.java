/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author VU HONG ANH
 */
public class Product implements Comparable<Product> {
    public static final char SEPERATOR = ',';
    public static final String ID_FORMAT = "P\\d{4}";
    private String ID;
    private String name;
    private double price;
    private int quantity;
    private String status;

    public Product(String ID, String name, double price, int quantity, String status) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public Product(String s) {
        String[] parts = s.split("" + this.SEPERATOR);
        ID = parts[0].trim().toUpperCase();
        name = parts[1].trim().toUpperCase();
        price = Double.parseDouble(parts[2].trim());
        quantity = Integer.parseInt(parts[3].trim());
        status = parts[4].trim().toUpperCase();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Product o) {
        return this.getID().compareToIgnoreCase(o.getID());
    }
}
