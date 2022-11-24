package com.example.appmobile_crud.entities;

import java.io.Serializable;

public class InvoiceDetail implements Serializable {
    private int id_detail;
    private Invoice invoice;
    private Product product;
    private int quantity;
    private double sub_total;
    private double iva;
    private double total;

    public InvoiceDetail(int id_detail, Invoice invoice, Product product, int quantity, double sub_total, double iva, double total) {
        this.id_detail = id_detail;
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        this.sub_total = sub_total;
        this.iva = iva;
        this.total = total;
    }

    public InvoiceDetail() {
    }

    @Override
    public String toString() {
        return "InvoiceDetail{" +
                "id_detail=" + id_detail +
                ", invoice=" + invoice.toString() +
                ", product='" + product.toString() + '\'' +
                ", quantity=" + quantity +
                ", sub_total=" + sub_total +
                ", iva=" + iva +
                ", total=" + total +
                '}';
    }

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
