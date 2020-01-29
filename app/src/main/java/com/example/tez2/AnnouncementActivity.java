package com.example.tez2;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AnnouncementActivity extends AppCompatActivity {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference etkinlikRef=db.collection("Etkinlik");
    //etkinlik means Announcement. this is the part i wanna inport notification

    private TextView textViewData;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyurular);
        textViewData=findViewById(R.id.text_view_data);
        btnAdd=findViewById(R.id.etkinlikEkle);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EtkinlikEkleActivity.class));
                //etkinlikEkleActivity : AnnouncementAddActivity
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        etkinlikRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e!=null){
                    return;
                }
                String data="";

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    EtkinlikModel etkinlikModel=documentSnapshot.toObject(EtkinlikModel.class);
                    String ad=etkinlikModel.getEtkinlikAdi();
                    String aciklama=etkinlikModel.getEtkinlikAciklma();
                    String basTarih=etkinlikModel.getBaslangicTarihi();
                    String sonTarih=etkinlikModel.getSonlanmaTarihi();

                    data+= "Etkinlik ADI: "+ad+"\nAçıklama: "+aciklama+"\n"+
                            "Etkinlik Tarihi:"+basTarih+"\n"+"sonlanma:"+sonTarih+"\n\n";
                }
            }
        });


        etkinlikRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data="";
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            EtkinlikModel etkinlikModel=documentSnapshot.toObject(EtkinlikModel.class);
                            String ad=etkinlikModel.getEtkinlikAdi();
                            String aciklama=etkinlikModel.getEtkinlikAciklma();
                            String sonTarih=etkinlikModel.getSonlanmaTarihi();
                            String basTarih=etkinlikModel.getSonlanmaTarihi();

                            data+= "Etkinlik ADI: "+ad+"\nAçıklama: "+aciklama+"\n"+
                                    "Etkinlik Tarihi:"+basTarih+"\n"+"sonlanma:"+sonTarih+"\n\n";
                        }

                        textViewData.setText(data);

                    }
                });
    }


}
