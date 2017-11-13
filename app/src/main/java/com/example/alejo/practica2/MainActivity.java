package com.example.alejo.practica2;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alejo.practica2.Classes.UserClass;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends DrawerActivity implements OnMapReadyCallback {


    MapView mapView;
    GoogleMap mMap;



    String correo="",contraseña="";
    String correoR, contraseñaR,nombreR;
    String fotoR="";
    int main=2222;
    int id=0;
    int flag=0;
    int oplog;
    UserClass user;
    GoogleApiClient mGoogleApiClient;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //INICIALIZACIÓN DEL MAPA
        mapView = (MapView) findViewById(R.id.map);


        try{
            mapView.onCreate(savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
        }
        mapView.getMapAsync(this);
        //mapView.onCreate(savedInstanceState);








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



        prefs = getSharedPreferences(Tags.TAG_PREFERENCES, Context.MODE_PRIVATE);

        correoR = prefs.getString(Tags.TAG_EMAIL,"");
        nombreR = prefs.getString(Tags.TAG_NAME,"");


        Toast.makeText(getApplicationContext(),correoR,Toast.LENGTH_SHORT).show();

        String email="emai";



        myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            while(id!=100) {
                                                if (dataSnapshot.child("User" + id).exists()) {
                                                    user = dataSnapshot.child("User" + id).getValue(UserClass.class);
                                                    String name2 = user.getEmail();
                                                    if(correoR.equals(name2)){
                                                        flag=flag+1;
                                                    }
                                                }else{
                                                    break;
                                                }
                                                id=id+1;
                                            }

                                            if(flag ==0){
                                                DatabaseReference myRef = database.getReference("Users").child("User"+id);
                                                user= new UserClass(correoR,String.valueOf(id),"0" ,"0",nombreR,"123");
                                                myRef.setValue(user);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }


                                    });






    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);

        LatLng parquedeseos = new LatLng(6.2641494,-75.5672275);
        mMap.addMarker(new MarkerOptions()
                .position(parquedeseos).title("Parque de los deseos")
                .snippet("donde tus deseoos se los roba un gamin")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
        );


        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parquedeseos,20));

    }
    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    //INTENTO DE MAPA EN LA ACTIVIDAD PRINCIPAL DE LA APLICACIÓN

/*
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
    }*/
}
