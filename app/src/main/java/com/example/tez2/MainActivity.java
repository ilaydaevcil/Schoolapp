package com.example.tez2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {



   TextView mainusers,mainphone,mainemail;
   FirebaseFirestore fStore;
   FirebaseAuth fAuth;
   String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainusers=findViewById(R.id.mainkullaniciAdi);
        mainphone=findViewById(R.id.maintelefon);
        mainemail=findViewById(R.id.mainemail);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userId=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference= fStore.collection("kullanicilar").document(userId);
        //kullanicilar : in database users table
        //in this table : email, usersname and phone
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mainusers.setText(documentSnapshot.getString("kullaniciAdi"));
                //kullaniciAdi: userName
                mainemail.setText(documentSnapshot.getString("email"));

                mainphone.setText(documentSnapshot.getString("telefon"));

                //telefon: phone .

            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); // çıkış işlemi
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void LessonsNotes(View view) {
        //dersNotları : LessonsNotes
        startActivity(new Intent(getApplicationContext(),DepartmensActivity.class));
    }

    public void Announcement(View view) {
        //duyurular: announcements
        startActivity(new Intent(getApplicationContext(),AnnouncementActivity.class));

    }

    public void PlacesToVisit(View view) {
        //its not important now. its easy to do this part
        startActivity(new Intent(getApplicationContext(),GezilecekYerActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //so that he does not pass directly by pressing the back button on the phone.
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            try {
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Programdan çıkılsın mı?").setCancelable(false).setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }).setPositiveButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Programdan çıkmaktan vazgeçtiniz!", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.create().show();
                return super.onKeyDown(keyCode,event);
            }
            catch (IllegalStateException e){
                e.printStackTrace();
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
