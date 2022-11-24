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

import com.example.appmobile_crud.entities.InvoiceDetail;
import com.example.appmobile_crud.entities.Product;
import com.example.appmobile_crud.managers.Manager;

import java.util.ArrayList;
import java.util.List;

public class MainActivityProducts extends AppCompatActivity {


    TableLayout tableProducts;
    Manager manager;

    Button addProduct;
    TextView idProduct, productName, productDescription, productQuantity, productIva, productPrice;
    String id_product, product_name, description;
    TextView searchProduct;

    Button back;

    int quantity;
    double iva, price;

    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);

        productList = new ArrayList<>();

        manager=new Manager(this, "appmobile.db", 1);
        tableProducts = (TableLayout) findViewById(R.id.tblProductsDetailsProducts);

        addProduct =  (Button) findViewById(R.id.btnAddProduct);
        idProduct = (TextView)findViewById(R.id.textIdProduct);
        productName = (TextView)findViewById(R.id.textProductName);
        productDescription = (TextView)findViewById(R.id.textProductDescription);
        productQuantity = (TextView)findViewById(R.id.textProductQuantity);
        productIva = (TextView)findViewById(R.id.textProductIva);
        productPrice = (TextView)findViewById(R.id.textProductPrice);
        searchProduct = (TextView) findViewById(R.id.textProductIdSearch);
        back = (Button) findViewById(R.id.btnBackProducts);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivityProducts.this,MainActivity.class);
                startActivity(p);
            }
        });
    }


    public boolean validarCamposProducts(){
        boolean resp = true;
        String id_product = idProduct.getText().toString();
        String product_name = productName.getText().toString();
        String description = productDescription.getText().toString();
        String quantity = productQuantity.getText().toString();
        String iva = productIva.getText().toString();
        String price = productPrice.getText().toString();
        if(id_product.isEmpty()){
            idProduct.setError("Product ID is required");
            resp=false;
        }
        if(product_name.isEmpty()){
            productName.setError("Product Name is required");
            resp=false;
        }
        if(description.isEmpty()){
            productDescription.setError("Description is required");
            resp=false;
        }
        if(quantity.isEmpty()){
            productQuantity.setError("Quantity is required");
            resp=false;
        }
        if(iva.isEmpty()){
            productIva.setError("Product Iva is required");
            resp=false;
        }
        if(price.isEmpty()){
            productPrice.setError("Product Price is required");
            resp=false;
        }
        return resp;
    }

    public boolean validarCamposSearch(){
        boolean resp = true;
        id_product = searchProduct.getText().toString();
        if(id_product.isEmpty()){
            searchProduct.setError("Card ID is required");
            resp=false;
        }
        return resp;
    }


    public void setAddProduct_OnClick(View view){
        if(validarCamposProducts()){
            id_product = idProduct.getText().toString();
            Product product = manager.SelectProductBy_ID(id_product);
            if(product==null){
                product_name = productName.getText().toString();
                description = productDescription.getText().toString();
                quantity = Integer.parseInt(productQuantity.getText().toString());
                iva = Double.parseDouble(productIva.getText().toString());
                price = Double.parseDouble(productPrice.getText().toString());
                tableProducts.removeAllViews();
                product = new Product(id_product, product_name, description, quantity, price, iva);
                manager.insertProduct(id_product,product_name,description, quantity, price, iva);
                manager.addNewProduct(product,productList);
                LlenarTabla(productList);
                Toast.makeText(this, "Product inserted!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                idProduct.setText("");
                productName.setText("");
                productDescription.setText("");
                productQuantity.setText("");
                productPrice.setText("0");
                productQuantity.setText("0");
                productIva.setText("0");
                Toast.makeText(this, "Product exist on Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void SearchProduct(View view){
        if(validarCamposSearch()){
            id_product = searchProduct.getText().toString();
            Product product = manager.SelectProductBy_ID(id_product);
            if(product!=null){
                idProduct.setText(product.getId_product());
                productName.setText(product.getProduct_name());
                productDescription.setText(product.getDescription());
                productQuantity.setText(""+product.getQuantity());
                productPrice.setText(""+product.getPrice());
                productIva.setText(""+product.getIva());
                tableProducts.removeAllViews();
                LlenarTabla();
                Toast.makeText(this, "Product exist on Database!", Toast.LENGTH_SHORT).show();
            }
            else{
                idProduct.setText("");
                productName.setText("");
                productDescription.setText("");
                productIva.setText("0");
                productPrice.setText("0");
                productQuantity.setText("0");
                Toast.makeText(this, "Product not found on Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void DeleteProduct(View view){
        if(validarCamposSearch()){
            id_product = idProduct.getText().toString();
            Product product = manager.SelectProductBy_ID(id_product);
            if(product==null){
                Toast.makeText(this, "Product not found on Database!", Toast.LENGTH_SHORT).show();
            }
            else{
                List<InvoiceDetail> invoiceDetailList = manager.SelectAllInvoiceDetailsList();
               if(!manager.searchProduct(invoiceDetailList, product.getId_product())){
                   manager.DeleteProduct(id_product);
                   idProduct.setText("");
                   productName.setText("");
                   productDescription.setText("");
                   productQuantity.setText("0");
                   productPrice.setText("0");
                   productIva.setText("0");
                   tableProducts.removeAllViews();
                   LlenarTabla();
                   Toast.makeText(this, "Product deleted successfully!", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(this, "Product can not deleted because it have a invoice!", Toast.LENGTH_SHORT).show();
               }
            }
        }
    }

    public void LlenarTabla(){
        Product[] products = manager.SelectAllProducts();
        for (Product product: products) {
            TableRow reg = (TableRow) LayoutInflater.from(this).inflate(R.layout.activity_main_show_products, null,false);
                TextView tvCode = reg.findViewById(R.id.textCode);
                TextView tvName = reg.findViewById(R.id.textNameProduct);
                TextView tvQuantity = reg.findViewById(R.id.textQuantityProduct);
                TextView tvPrice = reg.findViewById(R.id.textPriceProduct);
                tvCode.setText(product.getId_product());
                tvName.setText(product.getProduct_name());
                tvQuantity.setText(""+product.getQuantity());
                tvPrice.setText(""+product.getPrice());
                tableProducts.addView(reg);

        }
    }

    public void ShowAllProducts(View view){
        tableProducts.removeAllViews();
        LlenarTabla();
    }

    public void UpdateProduct_OnClick(View view){
        if(validarCamposProducts()){
            id_product = idProduct.getText().toString();
            Product product = manager.SelectProductBy_ID(id_product);
            if(product!=null){
                product_name = productName.getText().toString();
                description = productDescription.getText().toString();
                quantity = Integer.parseInt(productQuantity.getText().toString());
                iva = Double.parseDouble(productIva.getText().toString());
                price = Double.parseDouble(productPrice.getText().toString());
                product = manager.UpdateProduct(id_product, product_name, description, quantity, price, iva);
                productList = new ArrayList<>();
                productList.add(product);
                tableProducts.removeAllViews();
                LlenarTabla(productList);
                Toast.makeText(this, "Product updated!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                idProduct.setText("");
                productName.setText("");
                productDescription.setText("");
                productQuantity.setText("");
                productPrice.setText("0");
                productQuantity.setText("0");
                productIva.setText("0");
                Toast.makeText(this, "Updated product failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void LlenarTabla(List<Product> products){
        for (Product product: products) {
            TableRow reg = (TableRow) LayoutInflater.from(this).inflate(R.layout.activity_main_show_products, null,false);
            TextView tvCode = reg.findViewById(R.id.textCode);
            TextView tvName = reg.findViewById(R.id.textNameProduct);
            TextView tvQuantity = reg.findViewById(R.id.textQuantityProduct);
            TextView tvPrice = reg.findViewById(R.id.textPriceProduct);
            tvCode.setText(product.getId_product());
            tvName.setText(product.getProduct_name());
            tvQuantity.setText(""+product.getQuantity());
            tvPrice.setText(""+product.getPrice());
            tableProducts.addView(reg);
        }
    }
}