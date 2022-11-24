package com.example.appmobile_crud.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appmobile_crud.AdminSQLite;
import com.example.appmobile_crud.entities.Customer;
import com.example.appmobile_crud.entities.Invoice;
import com.example.appmobile_crud.entities.InvoiceDetail;
import com.example.appmobile_crud.entities.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager {
    private AdminSQLite adminSQLLite;
    private SQLiteDatabase sqLiteDatabase;

    public Manager(Context context, String nameDB, int version){
        adminSQLLite = new AdminSQLite(context, nameDB,null, version);
    }


    public void in() {
        this.sqLiteDatabase = adminSQLLite.getWritableDatabase();
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('NUS', 'everyone Hand Sanitizer', 'alcohol', 19, 30.93, 12); " +
                "", null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('SCHN', 'Molds - Mold Mix 10', 'Molds - Mold Mix 10', 10, 61.34, 0);",null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('DPG', 'Didanosine', 'Didanosine', 24, 71.21, 12);",null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('CLNS^G', 'athletes foot', 'Terbinafine Hydrochloride', 16, 45.75, 0);\n",null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('CFCO', 'BEAUTE INITIALE', 'Energizing Multi-Protection Healthy Glow Fluid', 21, 35.07, 0);\n",null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('ELOS', 'CLE DE PEAU BEAUTE PW FOUNDATION s', 'Octinoxate and Titanium dioxide', 16, 74.19, 0);\n",null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('AMWD', 'Citalopram', 'citalopram', 25, 68.25, 12);\n",null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('BANC', 'Fem Relief', 'Acetaminophen Pamabrom', 9, 78.85, 12);\n",null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('GEC', 'ACETAMINOPHEN AND CODEINe', 'ACETAMINOPHEN AND CODEINE PHOSPHATE', 25, 96.34, 12);\n",null);
        sqLiteDatabase.rawQuery("insert into product (id_product, product_name, description, quantity, price, iva) values ('SWIN', 'OXYGEN', 'OXYGEN', 19, 9.9, 0);\n",null);
        Log.i("info","Aqui toy");
        sqLiteDatabase.close();
    }

    public  void deleteCust(){
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        sqLiteDatabase.delete("customer", "id_customer<100", null);
        sqLiteDatabase.close();
    }

    public  void deleteProd(){
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        sqLiteDatabase.delete("product", "id_product<100", null);
        sqLiteDatabase.close();
    }

    public  void deleteInv(){
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        sqLiteDatabase.delete("invoice", "id_invoice<100", null);
        sqLiteDatabase.close();
    }

    public  void deleteDetail(){
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        sqLiteDatabase.delete("invoice_detail", "id_detail<100", null);
        sqLiteDatabase.close();
    }

    public boolean searchProductOnInvoice(List<InvoiceDetail> invoiceDetailList, String id_product){
        boolean resp = false;
        for (InvoiceDetail detail:invoiceDetailList
             ) {
            if(id_product.equals(detail.getProduct().getId_product())){
                resp = true;
                break;
            }
        }
        return resp;
    }

    public boolean searCustomerOnInvoice(List<Invoice> invoiceList, int id_customer){
        boolean resp = false;
        for (Invoice invoice:invoiceList
             ) {
            if(id_customer == invoice.getCustomer().getId_customer()){
                resp=true;
                break;
            }
        }
        return resp;
    }

    /**
     * Find All Customers
     * @return Array of customers
     */
    public Customer[] SelectAllCustomer()
    {
        Customer[] customers;
        Customer customer;

        int i = 0;
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from customer order by id_customer", null);
        if (cursor.getCount() <= 0){
            return null;
        }
        else{
            customers = new Customer[cursor.getCount()];
            while (cursor.moveToNext()){
                customer = new Customer(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
                customers[i++] = customer;
            }
            return customers;
        }
    }

    public List<Customer> SelectAllCustomerList()
    {
        List<Customer> customers;
        Customer customer;

        int i = 0;
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from customer order by id_customer", null);
        if (cursor.getCount() <= 0){
            return null;
        }
        else{
            customers = new ArrayList<>();
            while (cursor.moveToNext()){
                customer = new Customer(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
                customers.add(customer);
            }
            return customers;
        }
    }

    public List<InvoiceDetail> SelectAllInvoiceDetailsListByInvoiceId(int id_invoice)
    {
        List<InvoiceDetail> invoiceDetails;
        InvoiceDetail invoiceDetail;

        int i = 0;
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from invoice_detail where invoice = "+id_invoice+" order by id_detail", null);
        if (cursor.getCount() <= 0){
            return null;
        }
        else{
            invoiceDetails = new ArrayList<>();
            while (cursor.moveToNext()){
                invoiceDetail = new InvoiceDetail(cursor.getInt(0),
                        SelectInvoiceBy_ID(cursor.getInt(1)),
                        SelectProductBy_ID(cursor.getString(2)),
                        cursor.getInt(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6));
                invoiceDetails.add(invoiceDetail);
            }
            return invoiceDetails;
        }
    }

    public List<InvoiceDetail> SelectAllInvoiceDetailsList()
    {
        List<InvoiceDetail> invoiceDetails;
        InvoiceDetail invoiceDetail;

        int i = 0;
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from invoice_detail order by id_detail", null);
        if (cursor.getCount() <= 0){
            return null;
        }
        else{
            invoiceDetails = new ArrayList<>();
            while (cursor.moveToNext()){
                invoiceDetail = new InvoiceDetail(cursor.getInt(0),
                        SelectInvoiceBy_ID(cursor.getInt(1)),
                        SelectProductBy_ID(cursor.getString(2)),
                        cursor.getInt(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6));
                invoiceDetails.add(invoiceDetail);
            }
            return invoiceDetails;
        }
    }

    public Product[] SelectAllProducts()
    {
        Product[] products;
        Product product;

        int i = 0;
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from product order by id_product", null);
        if (cursor.getCount() <= 0){
            return null;
        }
        else{
            products = new Product[cursor.getCount()];
            while (cursor.moveToNext()){
                product = new Product(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5));
                products[i++] = product;
            }
            return products;
        }
    }


    public Invoice[] SelectAllInvoices()
    {
        Invoice[] invoices;
        Invoice invoice;

        int i = 0;
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from invoice order by id_invoice", null);
        if (cursor.getCount() <= 0){
            return null;
        }
        else{
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            invoices = new Invoice[cursor.getCount()];
            while (cursor.moveToNext()){
                invoice = new Invoice(cursor.getInt(0),
                        cursor.getString(1),
                        SelectCustomerByID(cursor.getInt(2)),
                        new Date(cursor.getString(3)),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6));
                invoices[i++] = invoice;
            }
            return invoices;
        }
    }

    public List<Invoice> SelectAllInvoicesList()
    {
        List<Invoice> invoices;
        Invoice invoice;

        int i = 0;
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from invoice order by id_invoice", null);
        if (cursor.getCount() <= 0){
            return null;
        }
        else{
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MM-yyyy");
            invoices = new ArrayList<>();
            while (cursor.moveToNext()){
                invoice = new Invoice(cursor.getInt(0),
                        cursor.getString(1),
                        SelectCustomerByID(cursor.getInt(2)),
                        new Date(cursor.getString(3)),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6));
                invoices.add(invoice);
            }
            return invoices;
        }
    }


    public InvoiceDetail[] SelectAllInvoicesDetailsByInvoiceId(int id_invoice)
    {
        InvoiceDetail[] invoiceDetails;
        InvoiceDetail invoiceDetail;

        int i = 0;
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from invoice_detail where invoice = '"+id_invoice+"' order by id_detail", null);
        if (cursor.getCount() <= 0){
            return null;
        }
        else{

            invoiceDetails = new InvoiceDetail[cursor.getCount()];
            while (cursor.moveToNext()){
                invoiceDetail = new InvoiceDetail(cursor.getInt(0),
                        SelectInvoiceBy_ID(cursor.getInt(1)),
                        SelectProductBy_ID(cursor.getString(2)),
                        cursor.getInt(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6));
                invoiceDetails[i++] = invoiceDetail;
            }
            return invoiceDetails;
        }
    }



    public Customer insertCustomer(String first_name, String last_name, String email, String phone, String card_id, String address){
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        ContentValues register = new ContentValues();
        register.put("first_name", first_name);
        register.put("last_name", last_name);
        register.put("email", email);
        register.put("phone",phone);
        register.put("card_id", card_id);
        register.put("address", address);
        sqLiteDatabase.insert("customer",null, register);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT last_insert_rowid() FROM customer", null);
        cursor.moveToFirst();
        int id_customer  = cursor.getInt(0);

        Customer customer = new Customer(id_customer, first_name, last_name, email, phone, card_id, address);
        sqLiteDatabase.close();
        return  customer;
    }

    public Product insertProduct(String id_product, String product_name, String description, int quantity, double price, double iva){
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        ContentValues register = new ContentValues();
        register.put("id_product", id_product);
        register.put("product_name", product_name);
        register.put("description", description);
        register.put("quantity",quantity);
        register.put("price", price);
        register.put("iva", iva);
        sqLiteDatabase.insert("product",null, register);
        Product product = new Product(id_product, product_name, description, quantity, price, iva);
        sqLiteDatabase.close();
        return  product;
    }

    public Invoice insertInvoice(String invoice_number, int customer, Date emission_date, double sub_total, double iva, double total){
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        ContentValues register = new ContentValues();
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        register.put("invoice_number", invoice_number);
        register.put("customer", customer);
        register.put("emission_date",formatoDeFecha.format(emission_date));
        register.put("sub_total", sub_total);
        register.put("iva", iva);
        register.put("total", total);
        sqLiteDatabase.insert("invoice",null, register);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT last_insert_rowid() FROM invoice", null);
        cursor.moveToFirst();
        int id_invoice  = cursor.getInt(0);
        Customer customer1 = SelectCustomerByID(customer);
        Log.i("info","Mario --- "+customer1.getCustomer());
        Invoice invoice = new Invoice(id_invoice, invoice_number, customer1, emission_date, sub_total, iva, total);
        Log.i("info","Mario --- "+invoice.getInvoice());
        sqLiteDatabase.close();
        return  invoice;
    }

    public InvoiceDetail insertInvoiceDetail(Invoice invoice, Product product, int quantity, double sub_total, double iva, double total){
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        ContentValues register = new ContentValues();
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        register.put("invoice", invoice.getId_invoice());
        register.put("product", product.getId_product());
        register.put("quantity",quantity);
        register.put("sub_total", sub_total);
        register.put("iva", iva);
        register.put("total", total);
        sqLiteDatabase.insert("invoice_detail",null, register);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT last_insert_rowid() FROM customer", null);
        cursor.moveToFirst();
        int id_detail  = cursor.getInt(0);
        InvoiceDetail invoiceDetail = new InvoiceDetail(id_detail,invoice,product,quantity, sub_total, iva, total);
        sqLiteDatabase.close();
        return  invoiceDetail;
    }


    public void addNewProduct(Product product, List<Product> productList){
        productList.add(product);
    }

    public void addNewCustomer(Customer customer, List<Customer> customerList){
        if(customerList==null) {
            customerList = new ArrayList<>();
        }
        customerList.add(customer);
    }

    public List<InvoiceDetail> addProductDetail(Product product, List<InvoiceDetail> list, int quantity){
        if (list==null){
            list = new ArrayList<>();
        }
        else{
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setProduct(product);
            invoiceDetail.setQuantity(quantity);
            invoiceDetail.setSub_total((quantity*product.getPrice())/1.12);
            invoiceDetail.setIva((quantity* product.getPrice())-(quantity*product.getPrice())/1.12);
            invoiceDetail.setTotal(quantity* product.getPrice());
            list.add(invoiceDetail);
        }
        return list;
    }

    public Invoice CalculateValues(List<InvoiceDetail> list){
        Invoice invoice = new Invoice();
        double subtotal =0, iva = 0, total = 0;
        for (InvoiceDetail invoiceDetail :
                list) {
            subtotal =subtotal+invoiceDetail.getSub_total();
            iva = iva + invoiceDetail.getIva();
            total = total+ invoiceDetail.getTotal();
            invoice.setSub_total(subtotal);
            invoice.setIva(iva);
            invoice.setTotal(total);
        }
        return invoice;
    }

    public int CountInvoices(){
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from invoice", null);
        return cursor.getCount();
    }

    public boolean searchProduct(List<InvoiceDetail> invoiceDetailList, String codeProduct){
        boolean resp = false;
        for (InvoiceDetail detail:invoiceDetailList
             ) {
            if(codeProduct.equals(detail.getProduct().getId_product())){
                resp=true;
                break;
            }
        }
        return resp;
    }

    public Invoice facturar(List<InvoiceDetail> list, int customer){
        Invoice inv = CalculateValues(list);
        Date date = new Date();
        Invoice invoice = insertInvoice(""+(CountInvoices()+1),customer,new Date(), inv.getSub_total(),inv.getIva(),inv.getTotal());
        for (InvoiceDetail invoiceDetail :
                list) {
            insertInvoiceDetail(invoice,invoiceDetail.getProduct(),invoiceDetail.getQuantity(),invoiceDetail.getSub_total(),invoiceDetail.getIva(),invoiceDetail.getTotal());
        }
        return invoice;
    }


    public List<InvoiceDetail> removeProduct(List<InvoiceDetail> list, int index){
        list.remove(index);
        return list;
    }


    public Customer UpdateCustomer(String first_name, String last_name, String email, String phone, String card_id, String address)
    {
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        ContentValues register = new ContentValues();
        register.put("first_name", first_name);
        register.put("last_name", last_name);
        register.put("email", email);
        register.put("phone",phone);
        register.put("card_id", card_id);
        register.put("address", address);
        sqLiteDatabase.update("customer", register, "card_id = '"+card_id+"'", null);
        Customer customer = SelectCustomerBy_ID(card_id);
        sqLiteDatabase.close();
        return customer;
    }

    public Product UpdateProduct(String id_product, String product_name, String description, int quantity, double price, double iva)
    {
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        ContentValues register = new ContentValues();
        register.put("id_product", id_product);
        register.put("product_name", product_name);
        register.put("description", description);
        register.put("quantity", quantity);
        register.put("price",price);
        register.put("iva", iva);
        sqLiteDatabase.update("product", register, "id_product = '"+id_product+"'", null);
        Product product = new Product(id_product, product_name, description, quantity, price, iva);
        sqLiteDatabase.close();
        return product;
    }

    public void DeleteCustomer(String card_id)
    {
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        sqLiteDatabase.delete("customer", "card_id = "+card_id, null);
        sqLiteDatabase.close();

    }


    public void DeleteProduct(String id_product)
    {
        sqLiteDatabase = adminSQLLite.getWritableDatabase();
        sqLiteDatabase.delete("product", "id_product = '"+id_product+"'", null);
        sqLiteDatabase.close();

    }


    public Invoice SelectInvoiceBy_ID(int id_invoice){
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from invoice where id_invoice = "+id_invoice, null);
        if(cursor.getCount()<=0){
            return null;
        }
        else{
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            cursor.moveToFirst();
            Invoice invoice = new Invoice(cursor.getInt(0),
                    cursor.getString(1),
                    SelectCustomerBy_ID(cursor.getString(2)),
                    new Date(cursor.getString(3)),
                    cursor.getDouble(4),
                    cursor.getDouble(5),
                    cursor.getDouble(6));
            return invoice;
        }
    }


    public Product SelectProductBy_ID(String id_product){
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from product where id_product = '"+id_product+"'", null);
        if(cursor.getCount()<=0){
            return null;
        }
        else{
            cursor.moveToFirst();
            Product product = new Product(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getDouble(4),
                    cursor.getDouble(5));
            return product;
        }
    }


    public Customer SelectCustomerBy_ID(String card_id){
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from customer where card_id = '"+card_id+"'", null);
        if(cursor.getCount()<=0){
            return null;
        }
        else{
            cursor.moveToFirst();
            Customer customer = new Customer(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6));
            return customer;
        }
    }

    public Customer SelectCustomerByID(int card_id){
        sqLiteDatabase = adminSQLLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from customer where id_customer = '"+card_id+"'", null);
        if(cursor.getCount()<=0){
            return null;
        }
        else{
            cursor.moveToFirst();
            Customer customer = new Customer(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6));
            return customer;
        }
    }


}
