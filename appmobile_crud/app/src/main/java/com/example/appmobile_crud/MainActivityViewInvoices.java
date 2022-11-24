package com.example.appmobile_crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.appmobile_crud.entities.Invoice;
import com.example.appmobile_crud.listadapter.ListAdapter;
import com.example.appmobile_crud.managers.Manager;

import java.util.List;

public class MainActivityViewInvoices extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Manager manager;
    Button back;
    SearchView searchView;
    List<Invoice> invoiceList;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_invoices);
        manager = new Manager(this, "appmobile.db", 1);

        invoiceList = manager.SelectAllInvoicesList();
        listAdapter  = new ListAdapter(invoiceList, this, new ListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Invoice invoice) {
                moveToDescription(invoice);
            }
        });
        back=(Button)findViewById(R.id.btnBackInvoices);
        searchView = (SearchView)findViewById(R.id.txtSearch);

        ShowInvoices();

        searchView.setOnQueryTextListener(this);
    }

    public void moveToDescription(Invoice invoice){
        Intent intent = new Intent(MainActivityViewInvoices.this, DescriptionActivity.class);
        intent.putExtra("Invoice", invoice);
        startActivity(intent);
    }

    public void Back(View view){
        Intent p = new Intent(MainActivityViewInvoices.this,MainActivityInvoices.class);
        startActivity(p);
    }

    public void ShowInvoices(){
        RecyclerView recyclerView = findViewById(R.id.listInvoices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filter(newText);
        return false;
    }
}