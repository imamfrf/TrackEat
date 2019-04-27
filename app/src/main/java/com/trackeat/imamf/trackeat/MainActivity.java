package com.trackeat.imamf.trackeat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView btmNav;
    private CameraHelper camHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btm nav
        btmNav = findViewById(R.id.navigation);
        btmNav.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFrg()).commit();






    }

    public boolean loadFragment(android.support.v4.app.Fragment fragment) {
       // if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        //}
       // return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        android.support.v4.app.Fragment fragment = null;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                loadFragment(fragment);
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                loadFragment(fragment);
                break;
        }

        return true;
    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;
//        //Toast.makeText(MainActivity.this, item.getItemId(), Toast.LENGTH_LONG).show();
//        switch (item.getItemId()){
//            case R.id.navigation_home:
//                fragment = new HomeFrg();
//                loadFragment(fragment);
//               // ft.commit();
//                Log.d("homm", "OK");
//                break;
//        }
//        return true;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


}

//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        android.support.v4.app.Fragment fragment = null;
//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        switch (item.getItemId()){
//            case R.id.navigation_home:
//                fragment = new HomeFragment();
//                loadFragment(fragment);
//                ft.commit();
//                break;
//        }
//        return true;
//    }
//}
