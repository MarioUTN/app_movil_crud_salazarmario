package com.example.appmobile_crud;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.appmobile_crud.entities.Customer;
import com.example.appmobile_crud.entities.Invoice;
import com.example.appmobile_crud.entities.InvoiceDetail;
import com.example.appmobile_crud.entities.Product;
import com.example.appmobile_crud.managers.Manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button products;
    Button customers;
    Button invoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        products = (Button) findViewById(R.id.bntProducts);
        customers = (Button) findViewById(R.id.btnCustomers);
        invoices = (Button) findViewById(R.id.btnInvoices);



        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivity.this,MainActivityProducts.class);
                startActivity(p);
            }
        });

        invoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivity.this,MainActivityInvoices.class);
                startActivity(p);
            }
        });

        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivity.this,MainActivityCustomers.class);
                startActivity(p);
            }
        });

    }

}