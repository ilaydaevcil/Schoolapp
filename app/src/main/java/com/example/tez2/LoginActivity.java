package com.example.tez2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

   private FirebaseAuth mAuth;
   private FirebaseUser firebaseUser;
    TextView loginEmail,loginSifre;
    Button loginButtonGiris;
    TextView loginHesabinizyokmu;
    ProgressBar loginProsesbar;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail=findViewById(R.id.lgnemail);
        loginSifre=findViewById(R.id.lgnSifre);
        loginProsesbar=findViewById(R.id.lgpnrogressBar);
        loginButtonGiris=findViewById(R.id.lgnbtnGiris);
        loginHesabinizyokmu=findViewById(R.id.lgnHesabınızYokmu);
        fAuth= FirebaseAuth.getInstance();


        //oturum açık mı değil mi

       if(firebaseUser !=null){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);

        }





        loginButtonGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=loginEmail.getText().toString().trim();
                String password= loginSifre.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    loginEmail.setError("Email girmek zorunludur.");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    loginSifre.setError("Password girmek zorunludur.");
                    return;
                }


                loginProsesbar.setVisibility(View.VISIBLE);
                //register 4 user database


                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            loginProsesbar.setVisibility(View.INVISIBLE);

                            Toast.makeText(LoginActivity.this,"Giriş Yapıldı",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else   {

                            Toast.makeText(LoginActivity.this,"Yanliş bişeyler oldu",Toast.LENGTH_SHORT).show();
                            loginProsesbar.setVisibility(View.INVISIBLE);

                        }


                    }
                });


            }
        });
       loginHesabinizyokmu.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
           }
       });


    }
}
