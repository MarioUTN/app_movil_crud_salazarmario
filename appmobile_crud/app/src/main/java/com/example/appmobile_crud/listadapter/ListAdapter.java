package com.example.appmobile_crud.listadapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile_crud.R;
import com.example.appmobile_crud.entities.Invoice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Invoice> invoiceList;
    private List<Invoice> invoicesOriginal;
    private LayoutInflater layoutInflater;
    private Context context;
    final ListAdapter.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void OnItemClick(Invoice invoice);
    }

    public ListAdapter(List<Invoice> itemInvoices, Context context, ListAdapter.OnItemClickListener listener){
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.invoiceList = itemInvoices;
        this.invoicesOriginal = new ArrayList<>();
        this.invoicesOriginal.addAll(invoiceList);
        this.listener = listener;
    }

    public  void filter(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            invoiceList.clear();
            invoiceList.addAll(invoicesOriginal);
        }
        else{
            List<Invoice> collection = invoiceList.stream().filter(i -> (i.getCustomer().getFirst_name() + i.getCustomer().getLast_name()).
                    toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            invoiceList.clear();
            invoiceList.addAll(collection);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){return invoiceList.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.list_invoice, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(invoiceList.get(position));
    }

    public void setItems(List<Invoice> items){
        invoiceList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, date, number, subtotal, iva, total;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.textNames);
            date = itemView.findViewById(R.id.textDate);
            number = itemView.findViewById(R.id.textNumber);
            subtotal = itemView.findViewById(R.id.textSubTotalInvoice);
            iva = itemView.findViewById(R.id.textIvaInvoice);
            total = itemView.findViewById(R.id.textTotalInvoice);
        }

        void bindData(final Invoice item){
            SimpleDateFormat f1 = new SimpleDateFormat("dd-MM-yyyy");
            iconImage.setColorFilter(Color.parseColor("#03a9f4"), PorterDuff.Mode.SRC_IN);
            name.setText(item.getCustomer().getFirst_name()+" "+item.getCustomer().getLast_name());
            date.setText(""+f1.format(item.getEmission_date()));
            number.setText("000-00"+item.getInvoice_number());
            subtotal.setText(""+String.format("%.2f",item.getSub_total()));
            iva.setText(""+String.format("%.2f",item.getIva()));
            total.setText(""+String.format("%.2f",item.getTotal()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(item);
                }
            });
        }
    }
}
