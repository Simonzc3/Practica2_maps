package com.example.alejo.practica2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class MainActivity extends DrawerActivity {
    String correo="",contraseña="";
    String correoR, contraseñaR,nombreR;
    String fotoR="";
    int main=2222;

    int oplog;
    GoogleApiClient mGoogleApiClient;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        prefs = getSharedPreferences(Tags.TAG_PREFERENCES,MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Error en login",Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();

        switch (id){
            case R.id.mMiPerfil:
                intent = new Intent(MainActivity.this,PerfilActivity.class);
                startActivity(intent);
                break;
            case R.id.mCerrar:

                prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);
                editor = prefs.edit();

                editor.putInt(Tags.LOGIN_OPTION,oplog);
                editor.commit();

                ///////////////////////////////cerrar sesión de google
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(

                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                            }
                        });
                ////////////////////////////////////////////////
                LoginManager.getInstance().logOut(); //Cierra sesión en facebook
                intent = new Intent(MainActivity.this,LoguinActivity.class);
                prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);
                editor = prefs.edit();
                oplog =0;
                editor.putString(Tags.TAG_PASSWORD,"");
                editor.putString(Tags.TAG_EMAIL,"");
                editor.putString(Tags.TAG_NAME,"");
                editor.putString(Tags.TAG_URLIMG,"");
                editor.putInt(Tags.LOGIN_OPTION,oplog);
                editor.commit();
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
