package com.example.tez2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView registerKullaniciAdi,registerEmail,registerSifre,registerTelefon;
    Button registerButton;
    TextView registerZatenHesabinizVarmi;
    ProgressBar registerProgressBar;
    FirebaseAuth fAuth;
    Boolean edu;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerKullaniciAdi=findViewById(R.id.rgstrKullaniciAdi);
        registerEmail=findViewById(R.id.rgstrEmail);
        registerSifre=findViewById(R.id.rgstrSifre);
        registerTelefon=findViewById(R.id.rgstrtelefon);
        registerButton=findViewById(R.id.rgsbutton);
        registerZatenHesabinizVarmi=findViewById(R.id.rgstrZatenHesabinizVarMi);
        registerProgressBar=findViewById(R.id.rgstrprogressBar);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final String email=registerEmail.getText().toString().trim();
                 String passworld= registerSifre.getText().toString().trim();
                 final String kullaniciAdi=registerKullaniciAdi.getText().toString();
                final String telefon=registerTelefon.getText().toString();

                edu=email.endsWith("@ogr.cbu.edu.tr");
/*
                if(edu==false){
                    registerEmail.setError("@ogr.cbu.edu.tr adresinizle giris olmalı");
                    return;
                }

 */

                if (TextUtils.isEmpty(email)){
                    registerEmail.setError("Email girmek zorunludur.");
                    return;
                }
                if (TextUtils.isEmpty(passworld)){
                    registerSifre.setError("Passworld girmek zorunludur.");
                    return;
                }
                if (passworld.length()<6){
                    registerSifre.setError("pasworld must be >6.");
                    return;
                }


                registerProgressBar.setVisibility(View.VISIBLE);
                //register 4 user database

                fAuth.createUserWithEmailAndPassword(email,passworld).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            registerProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this,"user created",Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("kullanicilar").document(userID);
                            Map<String,Object> kullanici=new HashMap<>();
                            kullanici.put("kullaniciAdi",kullaniciAdi);
                            kullanici.put("email",email);
                            kullanici.put("telefon",telefon);
                            documentReference.set(kullanici).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess:  kullanici profili yaratıldı "+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+e.toString());

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this,"yanliş bişeyler oldu"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            registerProgressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });
            }
        });

        registerZatenHesabinizVarmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}
