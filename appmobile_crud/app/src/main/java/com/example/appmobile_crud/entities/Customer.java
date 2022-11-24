package com.example.appmobile_crud.entities;

import java.io.Serializable;

public class Customer implements Serializable {
    private int id_customer;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String card_id;
    private String address;

    public Customer(){}
    public Customer(int id_customer, String first_name, String last_name, String email, String phone, String card_id, String address) {
        this.id_customer = id_customer;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.card_id = card_id;
        this.address = address;
    }

    public String getCustomer(){
        return "id: "+id_customer+"\nFirst Name: "+first_name+"\n" +
                "Last Name: "+last_name+"\nEmail: "+email+"\n" +
                "Phone: "+phone+"\nAddress: "+address+"\nCard ID: "+card_id;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
