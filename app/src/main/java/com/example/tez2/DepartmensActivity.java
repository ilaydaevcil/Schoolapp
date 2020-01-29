package com.example.tez2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DepartmensActivity extends AppCompatActivity {

    private FirebaseFirestore  firebaseFirestore;
     private RecyclerView bolumlerList;
     //bolumlerList means DepartmensList
     private FirestoreRecyclerAdapter adapter;
     String sessionBolumadi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolumler);

        firebaseFirestore = FirebaseFirestore.getInstance();
        bolumlerList = findViewById(R.id.bolumlerRecyleView);

        Query query= firebaseFirestore.collection("Bolumler");
        //Bolumler : departmens


        FirestoreRecyclerOptions<DepartmensModel> options=new FirestoreRecyclerOptions.Builder<DepartmensModel>()
                .setQuery(query, DepartmensModel.class)
                .build();

            adapter= new FirestoreRecyclerAdapter<DepartmensModel, BolumlerViewHolder>(options) {
            @NonNull
            @Override
            public BolumlerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.oneitembolum,parent,false);
                return new BolumlerViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BolumlerViewHolder holder, int position, @NonNull DepartmensModel model) {
            holder.bolumAdi.setText(model.getBolumAdi());

            }
        };
            bolumlerList.setHasFixedSize(true);
            bolumlerList.setLayoutManager(new LinearLayoutManager(this));
            bolumlerList.setAdapter(adapter);





    }



    private class BolumlerViewHolder extends RecyclerView.ViewHolder{

        private TextView bolumAdi;
        public BolumlerViewHolder(@NonNull View itemView) {
            super(itemView);
            //i just took DepartmanName.

            bolumAdi=itemView.findViewById(R.id.oneitembolumadi);

        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
