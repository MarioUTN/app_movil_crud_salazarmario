package com.example.appmobile_crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLite extends SQLiteOpenHelper {


    public AdminSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("" +
                "CREATE TABLE customer(" +
                "id_customer INTEGER NOT NULL," +
                "first_name text(100) NOT NULL," +
                "last_name text(100) NOT NULL," +
                "email text(50) NOT NULL," +
                "phone text(15) NOT NULL," +
                "card_id text(13) UNIQUE NOT NULL," +
                "address text(100) NOT NULL," +
                "CONSTRAINT pk_idcustomer PRIMARY KEY(id_customer)" +
                ");" +
                "");
        db.execSQL("" +
                "CREATE TABLE product(" +
                "id_product text(15) NOT NULL," +
                "product_name text(100) NOT NULL," +
                "description text(100) NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "price NUMERIC(12,2) NOT NULL," +
                "iva NUMERIC(5,2) NOT NULL," +
                "CONSTRAINT pk_product PRIMARY KEY(id_product)" +
                ");" +
                "");

        db.execSQL("" +
                "CREATE TABLE invoice(" +
                "id_invoice INTEGER NOT NULL," +
                "invoice_number text(20) UNIQUE NOT NULL," +
                "customer INTEGER NOT NULL," +
                "emission_date text NOT NULL," +
                "sub_total NUMERIC(12,2) NOT NULL," +
                "iva NUMERIC(12,2) NOT NULL," +
                "total numeric(12,2) NOT NULL," +
                "CONSTRAINT pk_invoice PRIMARY KEY(id_invoice)," +
                "CONSTRAINT fk_cust_inv FOREIGN KEY(customer) REFERENCES customer(id_customer)" +
                ");" +
                "");

        db.execSQL("" +
                "CREATE TABLE invoice_detail(" +
                "id_detail INTEGER NOT NULL," +
                "invoice INTEGER NOT NULL," +
                "product text(15) NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "sub_total NUMERIC(12,2) NOT NULL," +
                "iva NUMERIC(12,2) NOT NULL," +
                "total numeric(12,2) NOT NULL," +
                "CONSTRAINT pk_detail PRIMARY KEY(id_detail)," +
                "CONSTRAINT fk_prod_det FOREIGN KEY(product) REFERENCES product(id_product)," +
                "CONSTRAINT fk_inv_det FOREIGN KEY(invoice) REFERENCES invoice(id_invoice)" +
                ");" +
                "");

        db.execSQL("" +
                "create view view_invoices " +
                " as" +
                " select c.id_customer, c.first_name, c.last_name, c.email, c.phone, c.card_id, c.address," +
                "p.id_product, p.product_name, p.description, p.price," +
                "f.id_invoice, f.invoice_number, f.emission_date, f.sub_total, f.iva, f.total," +
                "id.id_detail, id.invoice, id.product, id.quantity, id.sub_total, id.iva, id.total" +
                " from customer as c, product as p, invoice as f, invoice_detail as id " +
                " where c.id_customer = f.customer and f.id_invoice = id.invoice and p.id_product = id.product;" +
                "");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
