package com.example.appmobile_crud.entities;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {
    private int id_invoice;
    private String invoice_number;
    private Customer customer;
    private Date emission_date;
    private double sub_total;
    private double iva;
    private double total;

    public Invoice(int id_invoice, String invoice_number, Customer customer, Date emission_date, double sub_total, double iva, double total) {
        this.id_invoice = id_invoice;
        this.invoice_number = invoice_number;
        this.customer = customer;
        this.emission_date = emission_date;
        this.sub_total = sub_total;
        this.iva = iva;
        this.total = total;
    }

    public Invoice(){}


    public String getInvoice() {
        return "Invoice{" +
                "id_invoice=" + id_invoice +
                ", invoice_number='" + invoice_number + '\'' +
                ", customer=" + customer.getCustomer() +
                ", emission_date=" + emission_date +
                ", sub_total=" + sub_total +
                ", iva=" + iva +
                ", total=" + total +
                '}';
    }

    public int getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(int id_invoice) {
        this.id_invoice = id_invoice;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getEmission_date() {
        return emission_date;
    }

    public void setEmission_date(Date emission_date) {
        this.emission_date = emission_date;
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
