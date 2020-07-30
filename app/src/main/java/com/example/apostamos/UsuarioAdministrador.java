package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class UsuarioAdministrador extends AppCompatActivity {
    private EditText et_email;
    private TextView tv_saldo, tv_nombre, tv_id;
    private DatabaseReference mDataBase;
    private Button btn_eliminar, btn_buscar;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_administrador);
        et_email = findViewById(R.id.editTextTextEmailAddress);
        tv_saldo = findViewById(R.id.textView29);
        tv_nombre = findViewById(R.id.textView25);
        tv_id = findViewById(R.id.textView31);
        btn_buscar = findViewById(R.id.button11);
        btn_eliminar = findViewById(R.id.button10);
        mDataBase = FirebaseDatabase.getInstance().getReference("Usuarios");

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                Query query = mDataBase.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String nombre = dataSnapshot.child("usuario").getValue().toString();
                            String saldo = dataSnapshot.child("saldo").getValue().toString();
                            Log.e("datos",""+dataSnapshot);
                            String id = dataSnapshot.getKey();
                            tv_saldo.setText( "Saldo: "+saldo);
                            tv_nombre.setText("Nombre: "+nombre);
                            tv_id.setText(id);
                            btn_eliminar.setEnabled(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                Query query = mDataBase.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            dataSnapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UsuarioAdministrador.this,"Usuario eliminado",Toast.LENGTH_SHORT).show();
                                    tv_id.setText("");
                                    et_email.setText("");
                                    tv_nombre.setText("");
                                    tv_saldo.setText("");
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
            if (id == R.id.retiro){
                Intent i = new Intent(this,SolicitudRetiroSaldo.class);
                startActivity(i);
            }
        return super.onOptionsItemSelected(item);
    }
}