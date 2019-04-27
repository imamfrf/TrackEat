package com.trackeat.imamf.trackeat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trackeat.imamf.trackeat.model.List_Makanan;

import java.util.List;

public class makananAdapter extends RecyclerView.Adapter<makananAdapter.ViewHolder> {
    private List<List_Makanan> listItems;
    private Context context;
    private OnItemClicked mListener;

    public makananAdapter(List<List_Makanan> listItems, Context context, OnItemClicked listener) {
        this.listItems = listItems;
        this.context = context;
        this.mListener = listener;

    }

    @Override
    public makananAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_makanan, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final makananAdapter.ViewHolder holder, final int position) {
        //binding value dari list item ke holder
        List_Makanan listItem = listItems.get(position);
        holder.namaMakanan.setText(listItem.getNamaMakanan());
        holder.namaCatering.setText(listItem.getNamaCatering());
        holder.kalori.setText(listItem.getKaloriMakanan());
        holder.harga.setText(listItem.getHarga());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView namaMakanan, namaCatering, kalori, harga;

        public ViewHolder(View itemView) {
            super(itemView);

            namaMakanan = itemView.findViewById(R.id.tv_namaMakanan);
            namaCatering = itemView.findViewById(R.id.tv_namaCatering);
            kalori = itemView.findViewById(R.id.tv_kalori);
            harga = itemView.findViewById(R.id.tv_harga);


        }
    }
    public interface OnItemClicked {
        void onItemClick(int position);
    }
}
