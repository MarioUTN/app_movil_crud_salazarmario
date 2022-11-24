package com.example.appmobile_crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmobile_crud.entities.Customer;
import com.example.appmobile_crud.entities.Invoice;
import com.example.appmobile_crud.entities.InvoiceDetail;
import com.example.appmobile_crud.entities.Product;
import com.example.appmobile_crud.listadapter.ListAdapter;
import com.example.appmobile_crud.managers.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityInvoices extends AppCompatActivity {

    String card_id, first_name, last_name, email, phone, addres, id_product;

    TextView cardId, FirstName, LastName, correo, telefono, direccion, Quantity, subtotal, Iva, Total;

    TableLayout tableProductsDetails;

    Button back, seeInvoices;

    int quantity;

    Manager manager;

    Customer customer;

    Product[] products;
    Spinner spinner;

    List<InvoiceDetail> invoiceDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_invoices);


        spinner = (Spinner)findViewById(R.id.spinner);

        cardId = (TextView) findViewById(R.id.textCI);
        FirstName = (TextView) findViewById(R.id.textName);
        LastName = (TextView) findViewById(R.id.textLastName);
        correo = (TextView) findViewById(R.id.textEmail);
        telefono = (TextView) findViewById(R.id.textPhone);
        direccion = (TextView) findViewById(R.id.textAddress);

        Quantity = (TextView)findViewById(R.id.textQuantityInvoce);
        tableProductsDetails = (TableLayout) findViewById(R.id.tblProductsDetailsInvoices);
        subtotal = (TextView)findViewById(R.id.textSubTotal);
        Iva = (TextView)findViewById(R.id.textIva);
        Total = (TextView)findViewById(R.id.textTotal);

        back = (Button)findViewById(R.id.btnBackInvoice);
        seeInvoices = (Button)findViewById(R.id.btnViewInvoices);

        customer = new Customer();

        invoiceDetails = new ArrayList<>();
        manager = new Manager(this, "appmobile.db", 1);
        products = manager.SelectAllProducts();
        LlenarSpinner(products);
/*
        manager.deleteDetail();
        manager.deleteInv();
        manager.deleteCust();
 */


    }



    public void Back(View view){
        Intent p = new Intent(MainActivityInvoices.this,MainActivity.class);
        startActivity(p);
    }

    public void SeeInvoices(View view){
        Intent p = new Intent(MainActivityInvoices.this,MainActivityViewInvoices.class);
        startActivity(p);
    }

    public boolean validarCamposInvoices(){
        boolean resp = true;
        String card_id = cardId.getText().toString();
        String first_name = FirstName.getText().toString();
        String last_name = LastName.getText().toString();
        String email = correo.getText().toString();
        String phone = telefono.getText().toString();
        String address = direccion.getText().toString();
        if(card_id.isEmpty()){
            cardId.setError("Card ID is required");
            resp=false;
        }
        if(first_name.isEmpty()){
            FirstName.setError("First Name is required");
            resp=false;
        }
        if(last_name.isEmpty()){
            LastName.setError("Last Name is required");
            resp=false;
        }
        if(email.isEmpty()){
            correo.setError("Email is required");
            resp=false;
        }
        if(phone.isEmpty()){
            telefono.setError("Phone is required");
            resp=false;
        }
        if(address.isEmpty()){
            direccion.setError("Address is required");
            resp=false;
        }
        return resp;
    }

    public boolean validarCamposSearch(){
        boolean resp = true;
        String card_id = cardId.getText().toString();
        if(card_id.isEmpty()){
            cardId.setError("Card ID is required");
            resp=false;
        }
        return resp;
    }


    public void SearchCustomer_OnClick(View view){
        if(validarCamposSearch()){
            card_id = cardId.getText().toString();
            Customer customer = manager.SelectCustomerBy_ID(card_id);
            if (customer!=null){
                cardId.setText(customer.getCard_id());
                FirstName.setText(customer.getFirst_name());
                LastName.setText(customer.getLast_name());
                correo.setText(customer.getEmail());
                telefono.setText(customer.getPhone());
                direccion.setText(customer.getAddress());
                Toast.makeText(this, "Customer found successfully!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Customer not found!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void addInvoice_OnClick(View view){
        if(validarCamposInvoices()){
            card_id = cardId.getText().toString();
            first_name = FirstName.getText().toString();
            last_name = LastName.getText().toString();
            email = correo.getText().toString();
            phone = telefono.getText().toString();
            addres = direccion.getText().toString();

            if(card_id.length()>0 && first_name.length()>0 && last_name.length()>0
                    &&email.length()>0&&phone.length()>0&&addres.length()>0) {
                Customer customer = manager.SelectCustomerBy_ID(card_id);

                if(invoiceDetails.size()==0){
                    Toast.makeText(this, "Invoice Details is Empty!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (customer == null) {
                        manager.insertCustomer(first_name, last_name, email, phone, card_id, addres);
                        customer = manager.SelectCustomerBy_ID(card_id);
                        Invoice invoice = manager.facturar(invoiceDetails, customer.getId_customer());
                        Toast.makeText(this, "Invoice created successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        customer = manager.SelectCustomerBy_ID(card_id);
                        Invoice invoice = manager.facturar(invoiceDetails, customer.getId_customer());
                        Toast.makeText(this, "Invoice created successfully!", Toast.LENGTH_SHORT).show();

                    }
                }

            }

            else{
                Toast.makeText(this, "Data Empty!!", Toast.LENGTH_SHORT).show();
            }
        }
        invoiceDetails=new ArrayList<>();
    }

    public boolean validarQuantity(){
        boolean resp = true;
        String quantity = Quantity.getText().toString();
        if(quantity.isEmpty()){
            Quantity.setError("Quantity is required and > 0");
            resp=false;
        }
        return resp;
    }

    public void addProductDetail_OnClick(View view){
        if(validarQuantity()){
            //products = manager.SelectAllProducts();
            int index = spinner.getSelectedItemPosition();
            List<Product> list = Arrays.asList(manager.SelectAllProducts());
            Product product = list.get(index);
            if(Quantity.getText().length()==0 || Quantity.getText().equals("0")){
                Toast.makeText(this, "Insert a quantity! ", Toast.LENGTH_SHORT).show();
            }
            else
            {
                quantity = Integer.parseInt(Quantity.getText().toString());
                if(quantity==0){
                    Toast.makeText(this, "Insert a quantity more that 0! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!manager.searchProduct(invoiceDetails, product.getId_product())) {
                        manager.addProductDetail(product, invoiceDetails, quantity);
                        tableProductsDetails.removeAllViews();
                        LlenarTabla(invoiceDetails);
                        ValoresPagar(invoiceDetails);
                        Toast.makeText(this, "Product inserted! " + product.getProduct_name(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Product was inserted!" + product.getProduct_name(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }



    public void LlenarSpinner(Product[] products){
        products = manager.SelectAllProducts();
        String[] opciones=new String[products.length];
        for (int i = 0; i<products.length; i++){
            opciones[i] = products[i].getProduct_name();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, opciones);
        spinner.setAdapter(adapter);
    }

    public void ValoresPagar(List<InvoiceDetail> products){
        double sub_total=0, iva = 0, total =0;
        for (InvoiceDetail invoiceDetail:products
             ) {
            sub_total = sub_total+invoiceDetail.getSub_total();
            iva = iva + invoiceDetail.getIva();
            total = total + invoiceDetail.getTotal();
            subtotal.setText(""+String.format("%.2f",sub_total));
            Iva.setText(""+String.format("%.2f",iva));
            Total.setText(""+String.format("%.2f",total));
        }
    }

    public void LlenarTabla(List<InvoiceDetail> products){
        for (InvoiceDetail product: products) {
            TableRow reg = (TableRow) LayoutInflater.from(this).inflate(R.layout.activity_main_show_products, null,false);
            TextView tvCode = reg.findViewById(R.id.textCode);
            TextView tvName = reg.findViewById(R.id.textNameProduct);
            TextView tvQuantity = reg.findViewById(R.id.textQuantityProduct);
            TextView tvPrice = reg.findViewById(R.id.textPriceProduct);
            tvCode.setText(product.getProduct().getId_product());
            tvName.setText(product.getProduct().getProduct_name());
            tvQuantity.setText(""+product.getQuantity());
            tvPrice.setText(""+String.format("%.2f",product.getTotal()));
            tableProductsDetails.addView(reg);

        }
    }
}