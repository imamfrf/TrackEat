package com.trackeat.imamf.trackeat.BeliFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trackeat.imamf.trackeat.DetailMakananActivity;
import com.trackeat.imamf.trackeat.R;
import com.trackeat.imamf.trackeat.model.List_Makanan;

import java.util.ArrayList;

import static com.trackeat.imamf.trackeat.util.Constant.CHILD.CHILD_MAKANAN;
import static com.trackeat.imamf.trackeat.util.Constant.KEY.KEY_ID_MAKANAN;
import static com.trackeat.imamf.trackeat.util.Constant.KEY.KEY_NAMA_CATERING;
import static com.trackeat.imamf.trackeat.util.Constant.KEY.KEY_NAMA_MAKANAN;

public class BeliFragment extends Fragment implements makananListener {

    private ArrayList mMakanans;

    private makananAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mStatusTextView;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beli, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mProgressBar = view.findViewById(R.id.progressBar);
        mStatusTextView = view.findViewById(R.id.statusTextView);

        setupFirebase();
        mStatusTextView.setVisibility(View.GONE);

        mMakanans = new ArrayList<List_Makanan>();

        setupRecyclerView();

        fetchMakanans();
    }

    private void setupFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    private void fetchMakanans() {
        mMakanans.clear();
        mDatabaseReference.child(CHILD_MAKANAN).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProgressBar.setVisibility(View.GONE);
                if (!dataSnapshot.hasChildren()) {
                    mStatusTextView.setVisibility(View.VISIBLE);
                    return;
                }
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    List_Makanan itemMakanan = childDataSnapshot.getValue(List_Makanan.class);
                    mMakanans.add(itemMakanan);
                    mAdapter.notifyDataSetChanged();
                    updateUI();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        mProgressBar.setVisibility(View.GONE);
        if (mMakanans.size() == 0) {
            mStatusTextView.setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setVisibility(View.GONE);
        }
    }

    private LinearLayoutManager getReverseLinearLayoutManager() {
        LinearLayoutManager reverseLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        reverseLinearLayoutManager.setStackFromEnd(true);
        return reverseLinearLayoutManager;
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(getReverseLinearLayoutManager());
        mAdapter = new makananAdapter(mMakanans, this, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(String id, String nama, String catering, double lat, double lng) {
        navigateToDetailMakananActivity(id, nama, catering, lat, lng);
    }

    @Override
    public void onBuyClick(String id) {

    }

    public void navigateToDetailMakananActivity(String id, String nama, String catering, double lat, double lng) {
        Intent intent = new Intent(getContext(), DetailMakananActivity.class);
        intent.putExtra(KEY_ID_MAKANAN, id);
        intent.putExtra(KEY_NAMA_MAKANAN, nama);
        intent.putExtra(KEY_NAMA_CATERING, catering);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            getActivity().finish();
        }
    }
}
