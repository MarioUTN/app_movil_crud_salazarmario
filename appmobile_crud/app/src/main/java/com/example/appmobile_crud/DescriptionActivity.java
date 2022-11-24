package com.example.appmobile_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.appmobile_crud.entities.Invoice;
import com.example.appmobile_crud.entities.InvoiceDetail;
import com.example.appmobile_crud.managers.Manager;

import java.text.SimpleDateFormat;
import java.util.List;

public class DescriptionActivity extends AppCompatActivity {

    TableLayout tableDetails;
    TextView cardId, FirstName, LastName, Email, Address, Phone, subTotal, Iva, Total, Date, Number;
    Manager manager;
    List<InvoiceDetail> invoiceDetailList;
    ImageView iconImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripion);

        Invoice invoice = (Invoice) getIntent().getSerializableExtra("Invoice");

        cardId = (TextView) findViewById(R.id.textCardIdDescription);
        FirstName = (TextView) findViewById(R.id.textFirstNameDescription);
        LastName = (TextView) findViewById(R.id.textLastNameDescription);
        Email = (TextView) findViewById(R.id.textEmailDescription);
        Address = (TextView) findViewById(R.id.textAddressDescription);
        Phone = (TextView) findViewById(R.id.textPhoneDescription);
        subTotal = (TextView) findViewById(R.id.textSubTotalInvoiceDescription);
        Iva = (TextView) findViewById(R.id.textIvaInvoiceDescription);
        Total = (TextView) findViewById(R.id.textTotalInvoiceDescription);

        Date = (TextView) findViewById(R.id.textDateDescription);
        Number = (TextView) findViewById(R.id.textNumberDescription);

        iconImage = (ImageView) findViewById(R.id.iconImageViewDescription);
        iconImage.setColorFilter(Color.parseColor("#03a9f4"), PorterDuff.Mode.SRC_IN);

        tableDetails = (TableLayout) findViewById(R.id.tableCustomersDetailsDescription);

        cardId.setText(invoice.getCustomer().getCard_id());
        FirstName.setText(invoice.getCustomer().getFirst_name());
        LastName.setText(invoice.getCustomer().getLast_name());
        Email.setText(invoice.getCustomer().getEmail());
        Address.setText(invoice.getCustomer().getAddress());
        Phone.setText(invoice.getCustomer().getPhone());
        subTotal.setText(""+String.format("%.2f", invoice.getSub_total()));
        Iva.setText(""+String.format("%.2f", invoice.getIva()));
        Total.setText(""+String.format("%.2f", invoice.getTotal()));

        SimpleDateFormat f1 = new SimpleDateFormat("dd-MM-yyyy");
        Date.setText(""+f1.format(invoice.getEmission_date()));
        Number.setText("000-00"+invoice.getInvoice_number());
        manager = new Manager(this, "appmobile.db", 1);
        invoiceDetailList = manager.SelectAllInvoiceDetailsListByInvoiceId(invoice.getId_invoice());
        LlenarTabla(invoiceDetailList);

    }

    public void LlenarTabla(List<InvoiceDetail> products){
        for (InvoiceDetail product: products) {
            TableRow reg = (TableRow) LayoutInflater.from(this).inflate(R.layout.activity_details_invoice, null,false);
            TextView tvCode = reg.findViewById(R.id.textIdProductDescription);
            TextView tvName = reg.findViewById(R.id.textNameProductDescription);
            TextView tvQuantity = reg.findViewById(R.id.textQuantityProductDescription);
            TextView tvPrice = reg.findViewById(R.id.textPriceProductDescription);
            tvCode.setText(product.getProduct().getId_product());
            tvName.setText(product.getProduct().getProduct_name());
            tvQuantity.setText(""+product.getQuantity());
            tvPrice.setText(""+String.format("%.2f",product.getTotal()));
            tableDetails.addView(reg);

        }
    }
}