package com.example.alejo.practica2;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.location.LocationServices;


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
    private FusedLocationProviderClient mFusedLocationClient;
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



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);




        prefs = getSharedPreferences(Tags.TAG_PREFERENCES,MODE_PRIVATE);
        editor = prefs.edit();
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

                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot children:dataSnapshot.getChildren()) {
                        if (children.child("email").getValue().equals(correoR)) {
                            user = new UserClass();
                            user.setKey(children.getKey());
                            editor.putString(Tags.TAG_KEY,children.getKey());
                            editor.apply();
                            flag = 1;
                            break;
                        }
                    }
                }

                if(flag ==0){
                    DatabaseReference myRef = database.getReference("Users").push();
                    user= new UserClass(correoR,"0" ,"0",nombreR,"123");
                    myRef.setValue(user);
                    user.setKey(myRef.getKey());
                    editor.putString(Tags.TAG_KEY,myRef.getKey());
                    editor.apply();
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("Esta ","mierda no funciona");
            Toast.makeText(getApplicationContext(),"Error: No tiene permisos suficientes",Toast.LENGTH_SHORT).show();
            int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=1;
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                return;
            }

        }


        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15));
                            DatabaseReference currentUser = myRef.child(user.getKey());
                            currentUser.child("lat").setValue(location.getLatitude());
                            currentUser.child("longi").setValue(location.getLongitude());

                        }else{
                            Toast.makeText(getApplicationContext(),"No se pudo actualizar la ubicación",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        LatLng parquedeseos = new LatLng(6.2641494,-75.5672275);
        mMap.addMarker(new MarkerOptions()
                .position(parquedeseos).title("Parque de los deseos")
                .snippet("donde tus deseoos se los roba un gamin")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
        );

        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

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


}
