package com.example.apostamos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SolicitudRetiroSaldo extends AppCompatActivity {
    private ListView lv_retiro;
    private DatabaseReference mDataBase;
    private SRadaptador sRadaptador;
    private ArrayList<ListaSolicitudRetiro> listaSolicitudRetiros = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solicitud_retiro_saldo);
        lv_retiro = findViewById(R.id.lv_retiro);
        mDataBase = FirebaseDatabase.getInstance().getReference("Retiro");

        sRadaptador = new SRadaptador(this,listaSolicitudRetiros);
        lv_retiro.setAdapter(sRadaptador);
        mDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String celular = dataSnapshot.child("celular").getValue().toString();
                    String usuario = dataSnapshot.child("usuario").getValue().toString();
                    String retirar = dataSnapshot.child("retirar").getValue().toString();
                    listaSolicitudRetiros.add(new ListaSolicitudRetiro(usuario,celular,retirar));
                    sRadaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadministrador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cargasaldo){
            Intent i = new Intent(this,MenuAdministrador.class);
            startActivity(i);
        }else
        if (id == R.id.usuarios){
            Intent i = new Intent(this,UsuarioAdministrador.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
