package com.example.alejo.practica2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.alejo.practica2.Classes.UserClass;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment {



    String correoR="",contraseñaR="", foto="",nombre="";
    EditText eTelefono;
    Button editTelefono;
    TextView texto,Borrar;
    ImageView ifoto;
    GoogleApiClient mGoogleApiClient;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int id=0;
    UserClass user;
    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.perfil_fragment, container, false);

        texto = (TextView) view.findViewById(R.id.nombre);
        ifoto = (ImageView) view.findViewById(R.id.foto);

        eTelefono = (EditText) view.findViewById(R.id.telefono);
        editTelefono = (Button) view.findViewById(R.id.EditarTelefono);

        Borrar = (TextView) view.findViewById(R.id.Borrar);




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");






        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        Context context;

        // cambie this por getActivity() para que dejara de mandar error por estar trabajando en un framento
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage( getActivity() /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //Toast.makeText(getApplicationContext(),"Error en login",Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        prefs = getActivity().getSharedPreferences(Tags.TAG_PREFERENCES, Context.MODE_PRIVATE);

        correoR = prefs.getString(Tags.TAG_EMAIL,"");
        contraseñaR = prefs.getString(Tags.TAG_PASSWORD,"");
        nombre = prefs.getString(Tags.TAG_NAME,"");
        foto = prefs.getString(Tags.TAG_URLIMG,"");

        texto.setText("Correo: " + correoR + "\nNombre: " + nombre);


        myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                while(id!=100) {
                    if (dataSnapshot.child("User" + id).exists()) {
                        user = dataSnapshot.child("User" + id).getValue(UserClass.class);
                        if(correoR.equals(user.getEmail())){
                            eTelefono.setText(user.getPhone());
                            break;
                        }
                    }

                    id=id+1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        editTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                myRef.child(prefs.getString(Tags.TAG_KEY,"")).child("phone").setValue(eTelefono.getText().toString());

                /*Map<String,Object> newData = new HashMap<>();
                newData.put("phone",eTelefono.getText().toString());
                myRef.updateChildren(newData);*/
            }
        });



        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users").child("User"+id);
                myRef = database.getReference("Users").child("User"+id);
                myRef.removeValue();
                LoginManager.getInstance().logOut(); //Cierra sesión en facebook
                editor = prefs.edit();
                editor.putString(Tags.TAG_NAME,"");
                editor.putString(Tags.TAG_EMAIL,"");
                editor.putString(Tags.TAG_PASSWORD,"");
                editor.putInt(Tags.LOGIN_OPTION,0);
                editor.putString(Tags.TAG_URLIMG,"");
                editor.commit();
                intent = new Intent(view.getContext(),LoguinActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });


        iniciar();





        return  view;
    }

    protected Intent intent;

    protected Runnable delay = new Runnable() {
        @Override
        public void run() {
            startActivity(intent);
            getActivity().finish();
        }
    };


    public void iniciar(){
        //Log.d("correo del perfil",correoR);
        //Log.d("contraseña del perfil",contraseñaR);
        //texto.setText(correoR);



        if(foto != null) Glide.with(getActivity().getApplicationContext()).load(foto)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ifoto);
    }
}
