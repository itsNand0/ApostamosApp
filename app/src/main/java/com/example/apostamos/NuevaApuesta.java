package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NuevaApuesta extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText et_monto;
    private ListView lv_apuestas;
    private NAadaptador nAadaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_apuesta);
        lv_apuestas = findViewById(R.id.lv_apuestas);
        et_monto = findViewById(R.id.et_monto);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        enviarDatosdelFirebase();


    }

    public void apostar(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
                String uid = user.getUid();
                String email =  user.getEmail();
                String name = user.getDisplayName();
                String monto = et_monto.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("usuario", name);
                map.put("email", email);
                map.put("monto", monto);
                mDatabase.child("Apuestas").push().setValue(map);
                Toast.makeText(this,"La apuesta esta hecha, Mucha suerte.",Toast.LENGTH_LONG).show();
                et_monto.setText("");
            }else {
            Toast.makeText(this,"Debe iniciar sesion",Toast.LENGTH_SHORT).show();
        }
    }


    private void enviarDatosdelFirebase(){
        final ArrayList<ListaApuestas> arrayApuesta = new ArrayList<>();
        mDatabase.child("Apuestas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                        mDatabase.child("Apuestas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String apostador = snapshot.child("usuario").getValue().toString();
                                String apuesta = snapshot.child("monto").getValue().toString();
                                arrayApuesta.add(new ListaApuestas(apostador,R.drawable.cerro_porteno,apuesta));
                                nAadaptador = new NAadaptador(NuevaApuesta.this,arrayApuesta);
                                lv_apuestas.setAdapter(nAadaptador);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
