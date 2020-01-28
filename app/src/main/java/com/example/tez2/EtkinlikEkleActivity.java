package com.example.tez2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Locale;

public class EtkinlikEkleActivity extends AppCompatActivity {


    private TextView editTextEtkinlikAdi;
    private TextView editText;

    private TextView dataView;
    int year,month,day;
    private Calendar calendar;
    private TextView sontarih;
    private Calendar calender2;
    private Button button;



    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference etkinlikRef=db.collection("Etkinlik");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik_ekle);

        editTextEtkinlikAdi=findViewById(R.id.etknlikadi);
        editText=findViewById(R.id.etkinlikaciklama);
        dataView=findViewById(R.id.textDate);
        calendar=Calendar.getInstance();
        calender2=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        sontarih=findViewById(R.id.txtSon);

        dataView.setVisibility(View.INVISIBLE);
        sontarih.setVisibility(View.INVISIBLE);

        button=findViewById(R.id.etkinlikEkle);



    }

    @SuppressWarnings("deprecation")
    public void sonTarih(View v){
        showDialog(888);

        Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View v){
        showDialog(999);

        Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id){

        if (id==999){
            return new DatePickerDialog(this,myDateListener,year,month,day);

        }
        if (id==888){
            return new DatePickerDialog(this,sontarihListener,year,month,day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener sontarihListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            showTarih(i,i1+1,i2);
        }
    };
    private DatePickerDialog.OnDateSetListener myDateListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            showDate(i,i1+1,i2);

        }
    };

    public  void showDate(int year,int month,int day){
        dataView.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    public void showTarih(int year,int month,int day){
        sontarih.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
  /*  protected void onStart() {
        super.onStart();

        etkinlikRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e!= null){
                    return;
                }

                String data="";
                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    EtkinlikModel note= documentSnapshot.toObject(EtkinlikModel.class);

                    String title= note.getEtkinlikAdi();
                    String description=note.getEtkinlikAciklma();


                    data+="\nTitle: "+title+ "\nDescription: "+description+"\n\n";


                }
            }
        });

    }*/
    public void addNotes(View v){

         String title =editTextEtkinlikAdi.getText().toString().trim();
        String description =editText.getText().toString().trim();

        String basTarih=dataView.getText().toString();
        String sonTarih=sontarih.getText().toString();

        if (TextUtils.isEmpty(title)){
            editTextEtkinlikAdi.setError("Etkinlik Adı girmek zorunludur!");
        }
        if (TextUtils.isEmpty(description)){
            editText.setError("Açıklama girmek zorunludur!");
        }
        if (TextUtils.isEmpty(basTarih)){
            editText.setError("Lütfen başlangıç tarihi seçiniz!");
        }
        if (TextUtils.isEmpty(sonTarih)){
            editText.setError("Lütfen sonlanma tarihi seçiniz!");
        }


        EtkinlikModel etkinlikModel=new EtkinlikModel(title,description,basTarih,sonTarih);
        etkinlikRef.add(etkinlikModel);
        startActivity(new Intent(getApplicationContext(),DuyurularActivity.class));


       /* AlertDialog alertDialog = new AlertDialog.Builder(EtkinlikEkleActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();*/

    }

}
