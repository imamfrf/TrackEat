package com.trackeat.imamf.trackeat;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FoodDetailActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private String nama;
    private float cals, fat, carbs, pro;
    private TextView tv_cals, tv_fat, tv_carbs, tv_pro, tv_cal_dtl, tv_cal_need_dtl;
    private ImageView img_food;
    private Button bt_makan;
    private FirebaseAuth auth;

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

        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary_trans));
//        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.green_trans));

        tv_cals = findViewById(R.id.tv_cals_detail);
        tv_fat = findViewById(R.id.tv_fat_detail);
        tv_carbs = findViewById(R.id.tv_carb_detail);
        tv_pro = findViewById(R.id.tv_pro_detail);
        tv_cal_dtl = findViewById(R.id.tv_cal_dtl);
        tv_cal_need_dtl = findViewById(R.id.tv_cal_need_dtl);

        bt_makan = findViewById(R.id.bt_makan);

        img_food = findViewById(R.id.img_food_header);
        //firebase
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

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

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        db.getReference("users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tv_cal_need_dtl.setText(String.valueOf(dataSnapshot.child("kebutuhanKalori").getValue()));
                tv_cal_dtl.setText(String.valueOf(dataSnapshot.child("daily").child(formattedDate).getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bt_makan.setOnClickListener(new View.OnClickListener() {
            float cal = 0.0f;
            @Override
            public void onClick(View v) {
                db.getReference("users").child(auth.getUid()).child("daily").child(formattedDate).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        cal = Float.valueOf(dataSnapshot.getValue().toString());
                        float cal2 = cal + Float.valueOf(tv_cals.getText().toString());
                        db.getReference("users").child(auth.getUid()).child("daily").child(formattedDate).setValue(cal2)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(findViewById(android.R.id.content),"Kalori berhasil ditambahkan",Snackbar.LENGTH_SHORT).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run(){

                                        finish();

                                    }
                                }, 1000);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
