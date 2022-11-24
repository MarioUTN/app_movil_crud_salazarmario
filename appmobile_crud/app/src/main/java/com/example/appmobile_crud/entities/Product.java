package com.example.appmobile_crud.entities;

import java.io.Serializable;

public class Product implements Serializable {
    private String id_product;
    private  String product_name;
    private  String description;
    private int quantity;
    private double price;
    private double iva;

    public Product(String id_product, String product_name, String description, int quantity, double price, double iva) {
        this.id_product = id_product;
        this.product_name = product_name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.iva = iva;
    }

    public  String getProduct(){
        return "\nid: "+id_product+"\nname: "+product_name+"\n" +
                "Description: "+description+"\nQuantity: "+quantity+"\n" +
                "Price: "+price+"\nIva: "+iva;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
}
