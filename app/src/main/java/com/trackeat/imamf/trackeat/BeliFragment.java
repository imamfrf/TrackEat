package com.trackeat.imamf.trackeat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trackeat.imamf.trackeat.model.List_Makanan;

import java.util.ArrayList;
import java.util.List;

public class BeliFragment extends android.support.v4.app.Fragment {

    public List<List_Makanan> listItems = new ArrayList<List_Makanan>();
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_beli, null);

        recyclerView = (RecyclerView) inflate.findViewById(R.id.recV_makanan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        listItems.add(new List_Makanan("Nasi Goreng", "Catering Malang", "300 kal", "Rp. 15000"));
        listItems.add(new List_Makanan("Nasi Goreng", "Catering Malang", "300 kal", "Rp. 15000"));
        listItems.add(new List_Makanan("Nasi Goreng", "Catering Malang", "300 kal", "Rp. 15000"));
        listItems.add(new List_Makanan("Nasi Goreng", "Catering Malang", "300 kal", "Rp. 15000"));

        adapter = new makananAdapter(listItems, getActivity(), new makananAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
//                ClipboardManager clipboard = (ClipboardManager) inflate.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("kode_promo", "tes");
//                clipboard.setPrimaryClip(clip);
//                Toast.makeText(inflate.getContext(),"Kode Promo Berhasil Disalin",Toast.LENGTH_SHORT).show();

            }
        });

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return inflate;
    }
}
