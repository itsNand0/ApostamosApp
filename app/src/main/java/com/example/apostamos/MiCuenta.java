package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class MiCuenta extends AppCompatActivity {
    private DatabaseReference mDataBase;
    private TextView tv_saldo,tv_nombre;
    private Button btn_cargar,btn_retirar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);
        tv_saldo = findViewById(R.id.tv_saldo);
        tv_nombre = findViewById(R.id.tv_nombre);
        mDataBase = FirebaseDatabase.getInstance().getReference("Usuarios");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email = user.getEmail();
        Query query = mDataBase.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   String saldo = dataSnapshot.child("saldo").getValue().toString();
                   tv_saldo.setText("Saldo: "+saldo+" Gs");
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_cargar = findViewById(R.id.btn_cargar_saldo);
        btn_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailcargar = user.getEmail();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Buenas, quisiera cargar mi saldo. \n Email: "+emailcargar+ "\n Monto: " );
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });
    }
}