package com.example.alejo.practica2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ClassActivity extends AppCompatActivity {

    FragmentManager fm;
    FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();


        SuperFragment frangment = new SuperFragment();
        ft.add(R.id.frame, frangment).commit();



    }

    public void cambiar(View view) {
        ft = fm.beginTransaction();
        PerfilFragment fragment = new PerfilFragment();
        ft.replace(R.id.frame,fragment).commit();
    }
}
