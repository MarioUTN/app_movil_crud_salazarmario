package com.example.appmobile_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmobile_crud.entities.Customer;
import com.example.appmobile_crud.entities.Invoice;
import com.example.appmobile_crud.entities.Product;
import com.example.appmobile_crud.managers.Manager;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCustomers extends AppCompatActivity {

    Manager manager;
    TableLayout tableCustomers;
    Button save, add, back;
    TextView cardId, FirstName, LastName, Email, Address, Phone;
    String card_id, first_name, last_name, email, address, phone;

    TextView searchCardId;

    List<Customer> customerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customers);


        customerList = new ArrayList<>();

        manager=new Manager(this, "appmobile.db", 1);
        tableCustomers = (TableLayout) findViewById(R.id.tableCustomersDetails);

        cardId = (TextView) findViewById(R.id.textCardId);
        FirstName = (TextView) findViewById(R.id.textFirstName);
        LastName = (TextView) findViewById(R.id.textLastName);
        Email = (TextView) findViewById(R.id.textEmail);
        Address = (TextView) findViewById(R.id.textAddress);
        Phone = (TextView) findViewById(R.id.textPhone);
        searchCardId = (TextView) findViewById(R.id.textCarIdSearch);
        back = (Button)findViewById(R.id.btnBackCustomers);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivityCustomers.this,MainActivity.class);
                startActivity(p);
            }
        });
    }

    public boolean validarCamposCustomers(){
        boolean resp = true;
        first_name = FirstName.getText().toString();
        last_name = LastName.getText().toString();
        email = Email.getText().toString();
        address = Address.getText().toString();
        phone = Phone.getText().toString();
        card_id = cardId.getText().toString();
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
            Email.setError("Email is required");
            resp=false;
        }
        if(address.isEmpty()){
            Address.setError("Address is required");
            resp=false;
        }
        if(phone.isEmpty()){
            Phone.setError("Card ID is required");
            resp=false;
        }
        return resp;
    }

    public boolean validarCamposSearch(){
        boolean resp = true;
        card_id = searchCardId.getText().toString();
        if(card_id.isEmpty()){
            searchCardId.setError("Card ID is required");
            resp=false;
        }
        return resp;
    }

    public void AddCustomer_OnClick(View view){
        if(validarCamposCustomers()){
            card_id = cardId.getText().toString();
            Customer customer = manager.SelectCustomerBy_ID(card_id);
            if(customer==null){
                first_name = FirstName.getText().toString();
                last_name = LastName.getText().toString();
                email = Email.getText().toString();
                address = Address.getText().toString();
                phone = Phone.getText().toString();
                customer = manager.insertCustomer(first_name,last_name,email,phone,card_id,address);
                tableCustomers.removeAllViews();
                if(customerList==null){
                    customerList = new ArrayList<>();
                }
                manager.addNewCustomer(customer, customerList);
                LlenarTabla(customerList);
                Toast.makeText(this, "Customer added!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Customer exist!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void UpdateCustomer_OnClick(View view){
        if(validarCamposCustomers()){
            card_id = cardId.getText().toString();
            Customer customer = manager.SelectCustomerBy_ID(card_id);
            if(customer!=null){
                first_name = FirstName.getText().toString();
                last_name = LastName.getText().toString();
                email = Email.getText().toString();
                address = Address.getText().toString();
                phone = Phone.getText().toString();
                customer = manager.UpdateCustomer(first_name,last_name,email,phone,card_id,address);
                tableCustomers.removeAllViews();
                customerList=new ArrayList<>();
                manager.addNewCustomer(customer, customerList);
                LlenarTabla(customerList);
                Toast.makeText(this, "Customer updated successfully!", Toast.LENGTH_SHORT).show();
            }
            else{
                cardId.setText("");
                FirstName.setText("");
                LastName.setText("");
                Email.setText("");
                Address.setText("");
                Phone.setText("");
                Toast.makeText(this, "Update customer Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void SearchCustomer_OnClick(View view){
        if(validarCamposSearch()){
            card_id = searchCardId.getText().toString();
            Customer customer = manager.SelectCustomerBy_ID(card_id);
            LlenarTabla(customerList);
            if(customer!=null){
                cardId.setText(customer.getCard_id());
                FirstName.setText(customer.getFirst_name());
                LastName.setText(customer.getLast_name());
                Email.setText(customer.getEmail());
                Address.setText(customer.getAddress());
                Phone.setText(customer.getPhone());
                tableCustomers.removeAllViews();
                customerList = new ArrayList<>();
                customerList.add(customer);
                LlenarTabla(customerList);
                Toast.makeText(this, "Customer found!", Toast.LENGTH_SHORT).show();
            }
            else{
                cardId.setText("");
                FirstName.setText("");
                LastName.setText("");
                Email.setText("");
                Address.setText("");
                Phone.setText("");
                Toast.makeText(this, "Customer not found!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void ShowAllCustomers(View view){
        customerList = manager.SelectAllCustomerList();
        tableCustomers.removeAllViews();
        LlenarTabla(customerList);
    }


    public void DeleteCustomer_OnClick(View view){
        if(validarCamposSearch()){
            List<Invoice> invoiceList = manager.SelectAllInvoicesList();
            card_id = searchCardId.getText().toString();
            Customer customer = manager.SelectCustomerBy_ID(card_id);
            if(!manager.searCustomerOnInvoice(invoiceList, customer.getId_customer())){
                if(customer!=null){
                    manager.DeleteCustomer(card_id);
                    customerList = new ArrayList<>();
                    customerList = manager.SelectAllCustomerList();
                    LlenarTabla(customerList);
                    Toast.makeText(this, "Customer deleted!", Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(this, "Customer not found to delete!", Toast.LENGTH_SHORT).show();
                }
                cardId.setText("");
                FirstName.setText("");
                LastName.setText("");
                Email.setText("");
                Address.setText("");
                Phone.setText("");
            }
            else {
                Toast.makeText(this, "Customer can not be deleted because have a invoice!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void LlenarTabla(List<Customer> customerList){
        tableCustomers.removeAllViews();
        //customerList = new ArrayList<>();
        //customerList = manager.SelectAllCustomerList();
        if(customerList!=null){
            for (Customer customer: customerList) {
                TableRow reg = (TableRow) LayoutInflater.from(this).inflate(R.layout.activity_main_show_customers, null,false);
                TextView tvCode = reg.findViewById(R.id.textCardIdCustomer);
                TextView tvName = reg.findViewById(R.id.textNameCustomer);
                TextView tvEmail = reg.findViewById(R.id.textEmailCustomer);
                TextView tvPhone = reg.findViewById(R.id.textPhoneCustomer);
                tvCode.setText(""+customer.getId_customer());
                tvName.setText(customer.getFirst_name()+" "+customer.getLast_name());
                tvEmail.setText(customer.getEmail());
                tvPhone.setText(customer.getPhone());
                tableCustomers.addView(reg);

            }
        }
        else{
            Toast.makeText(this, "Data not found!", Toast.LENGTH_SHORT).show();
        }
    }
}