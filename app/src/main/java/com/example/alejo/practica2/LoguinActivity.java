package com.example.alejo.practica2;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;


import com.example.alejo.practica2.Classes.Tags;
import com.example.alejo.practica2.Classes.UserClass;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;


public class LoguinActivity extends AppCompatActivity {
    String contrasenaR,correoR,correo,contrasena,nombreR,contraseñai,correoi,contraseñarr,correorr,correomain,contraseñamain;
    EditText eCorreo,eContrasena;
    String personPhotoUrl, personName ="";
    Uri urlphoto;
    int main;
    int mainR = 0;
    int oplog;

    String facebook_id;
    String f_name;
    String m_name;
    String l_name;
    String full_name;
    String profile_image;
    String email_id;
    String gender;
    String id_facebook;
    String hola;
    Bitmap imagen;
    UserClass user;
    int flag=0;



    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    LoginButton loginButton;
    CallbackManager callbackManager;
    private int option; // 1:facebook,2:contraseña,3:google
    GoogleApiClient mGoogleApiClient;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");


    @Override
    /// podemso tulizar los metodos onresume(), onpuuse, onstop, ondestroy
    // y elonrestart

    //metodo del boton onBackpresset(), es el del boton atras del celular
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.alejo.practica2",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }



        ////////Logueo google
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


        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                signIn();

                                            }
        });
                ///////////////////////////////////////////////////////////////////////////////
              /*  Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoR = extras.getString("correomain");
            contraseñaR = extras.getString("contraseñamain");
            nombreR = extras.getString("nombremain");
        }*/


//        eCorreo = (EditText) findViewById(R.id.eCorreo);
//        eContrasena = (EditText) findViewById(R.id.eContraseña);


        loginButton = (LoginButton) findViewById(R.id.login_button);
        prefs = getSharedPreferences(Tags.TAG_PREFERENCES, Context.MODE_PRIVATE);

        /*loginButton.setReadPermissions(Arrays.asList(
                "email",
                "public_profile"
        ));*/


        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"Login Exitoso",Toast.LENGTH_SHORT).show();

                //progress.show();
                /*Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    facebook_id=profile.getId();
                    f_name=profile.getFirstName();
                    m_name=profile.getMiddleName();
                    l_name=profile.getLastName();
                    full_name=profile.getName();
                    profile_image=profile.getProfilePictureUri(400, 400).toString();
                    Toast.makeText(getApplicationContext(),full_name,Toast.LENGTH_SHORT).show();


                }*/


                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response) {

                        JSONObject json = response.getJSONObject();
                        try {
                            if(json != null){

                                if (object.getString("name") != null) {
                                    full_name = object.getString("name");
                                }
                                if (object.getString("email") != null) {
                                    correoR = object.getString("email");
                                }
                                if (object.getString("picture") != null) {
                                    JSONObject imagen = new JSONObject(object.getString("picture"));
                                    JSONObject imagen2 = new JSONObject(imagen.getString("data"));
                                    profile_image = imagen2.getString("url");
                                }
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString(Tags.TAG_NAME, full_name);
                                editor.putString(Tags.TAG_EMAIL, correoR);
                                editor.putString(Tags.TAG_URLIMG, profile_image);
                                //editor.putBoolean(getString(R.string.is_guest), false);
                                editor.putInt(Tags.LOGIN_OPTION, 1).apply();
                                editor.apply();

                                databasecheck();

                                //goMainActivity(); //está en el databasecheck
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,picture");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login cancelado",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Error en el login",Toast.LENGTH_SHORT).show();
            }
        });

    }
    /*public void registrarse(View view){
        Intent intent = new Intent(LoguinActivity.this,RegistroActivity.class);
        startActivityForResult(intent,1234);

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1234 && resultCode == RESULT_OK){ //registro
            Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();


        }else if (requestCode == 5678){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


        else{
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void goMainActivity(){
        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        editor = prefs.edit();
        //almacenar el valor de optlog

        Intent intent = new Intent(LoguinActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void databasecheck(){
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

                goMainActivity();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database error",Toast.LENGTH_SHORT).show();

            }


        });
    }

    // metodo de google

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 5678);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Gooogle", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(getApplicationContext(),acct.getDisplayName(),Toast.LENGTH_SHORT).show();

            personName = acct.getDisplayName();
            urlphoto = acct.getPhotoUrl();

            personPhotoUrl = urlphoto.toString();
            correoR = acct.getEmail();

            prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            editor = prefs.edit();
            editor.putString(Tags.TAG_NAME, personName);
            editor.putString(Tags.TAG_EMAIL, correoR);
            editor.putString(Tags.TAG_URLIMG, personPhotoUrl);
            //editor.putBoolean(getString(R.string.is_guest), false);
            editor.putInt(Tags.LOGIN_OPTION, 1).apply();
            editor.apply();


            databasecheck();


           // goMainActivity();
            // ir a la actividad Main Activity

            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
        } else {
            // Signed out, show unauthenticated UI.

        }
    }




}
