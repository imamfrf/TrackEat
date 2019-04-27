package com.trackeat.imamf.trackeat;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetailActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private String nama;
    private float cals, fat, carbs, pro;
    private TextView tv_cals, tv_fat, tv_carbs, tv_pro;
    private ImageView img_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        nama = getIntent().getStringExtra("productName");

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(nama);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.main_collapsing);

//        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.green_trans));
//        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.green_trans));

        tv_cals = findViewById(R.id.tv_cals_detail);
        tv_fat = findViewById(R.id.tv_fat_detail);
        tv_carbs = findViewById(R.id.tv_carb_detail);
        tv_pro = findViewById(R.id.tv_pro_detail);

        img_food = findViewById(R.id.img_food_header);
        //firebase
        db = FirebaseDatabase.getInstance();

        db.getReference("makanan").child(nama).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cals = Float.valueOf(dataSnapshot.child("Calories").getValue().toString());
                fat = Float.valueOf(dataSnapshot.child("Fat").getValue().toString());
                carbs = Float.valueOf(dataSnapshot.child("Carbs").getValue().toString());
                pro = Float.valueOf(dataSnapshot.child("Protein").getValue().toString());

                tv_cals.setText(String.valueOf(cals));
                tv_fat.setText(String.valueOf(fat));
                tv_carbs.setText(String.valueOf(carbs));
                tv_pro.setText(String.valueOf(pro));

                Glide.with(FoodDetailActivity.this)
                        .load(dataSnapshot.child("imgSrc").getValue().toString()).into(img_food);

                Log.d("nasgorr", dataSnapshot.child("imgSrc").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
