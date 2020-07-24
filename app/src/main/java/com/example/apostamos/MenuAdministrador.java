package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MenuAdministrador extends AppCompatActivity {
    private EditText et_email,et_monto;
    private DatabaseReference mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);
        mDataBase = FirebaseDatabase.getInstance().getReference("Usuarios");
        et_email = findViewById(R.id.et_email);
        et_monto = findViewById(R.id.et_monto);

        Button btn_cargar = findViewById(R.id.btn_cargar);
        btn_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                if(!TextUtils.isEmpty(email)){
                    Query query = mDataBase.orderByChild("email").equalTo(email);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String clave = dataSnapshot.getKey();
                                String saldo = et_monto.getText().toString();
                                Map<String, Object> map = new HashMap<>();
                                map.put("saldo", saldo);
                                mDataBase.child(clave).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MenuAdministrador.this, "Carga realizada",Toast.LENGTH_LONG).show();
                                        et_monto.setText("");
                                        et_email.setText("");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(MenuAdministrador.this,"Debe ingresar el email del usuario",Toast.LENGTH_LONG).show();
                }
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
            if (id == R.id.usuarios){
                Intent i = new Intent(this,UsuarioAdministrador.class);
                startActivity(i);
            }
        return super.onOptionsItemSelected(item);
    }
}