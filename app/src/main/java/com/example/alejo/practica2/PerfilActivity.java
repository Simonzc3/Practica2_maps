package com.example.alejo.practica2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class PerfilActivity extends DrawerActivity {
    String correoR="",contraseñaR="", foto="",nombre="";
    TextView texto;
    ImageView ifoto;
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null)
                return;

            PerfilFragment fragment = new PerfilFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }




    public void Salir(View view) {
        Intent intent = new Intent(PerfilActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void iniciar(){
        //Log.d("correo del perfil",correoR);
        //Log.d("contraseña del perfil",contraseñaR);
        //texto.setText(correoR);
       if(foto != null) Glide.with(getApplicationContext()).load(foto)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ifoto);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }
}
