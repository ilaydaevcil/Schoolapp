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

public class BolumlerActivity extends AppCompatActivity {

    private FirebaseFirestore  firebaseFirestore;
     private RecyclerView bolumlerList;
     private FirestoreRecyclerAdapter adapter;
     String sessionBolumadi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolumler);

        firebaseFirestore = FirebaseFirestore.getInstance();
        bolumlerList = findViewById(R.id.bolumlerRecyleView);

        Query query= firebaseFirestore.collection("Bolumler");


        FirestoreRecyclerOptions<BolumlerModel> options=new FirestoreRecyclerOptions.Builder<BolumlerModel>()
                .setQuery(query, BolumlerModel.class)
                .build();

            adapter= new FirestoreRecyclerAdapter<BolumlerModel, BolumlerViewHolder>(options) {
            @NonNull
            @Override
            public BolumlerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.oneitembolum,parent,false);
                return new BolumlerViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BolumlerViewHolder holder, int position, @NonNull BolumlerModel model) {
            holder.bolumAdi.setText(model.getBolumAdi());

            }
        };
            bolumlerList.setHasFixedSize(true);
            bolumlerList.setLayoutManager(new LinearLayoutManager(this));
            bolumlerList.setAdapter(adapter);





    }

    /* public void onclickitem(View view) {

        Intent i= new Intent(BolumlerActivity.this,DerslerActivity.class);
        i.putExtra("key",sessionBolumadi);
        startActivity(i);

    }

     */

    private class BolumlerViewHolder extends RecyclerView.ViewHolder{

        private TextView bolumAdi;
        public BolumlerViewHolder(@NonNull View itemView) {
            super(itemView);

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
