package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeleccionTercerPartido extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText et_monto;
    private RadioButton rb_club1,rb_club2;
    private NAadaptador nAadaptador;
    private ArrayList<ListaApuestas> listas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_tercer_partido);
        ListView lv_apuestas = findViewById(R.id.lv_apuestas);
        et_monto = findViewById(R.id.et_monto3);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ImageView club1 = findViewById(R.id.imageView6);
        ImageView club2 = findViewById(R.id.imageView5);
        rb_club1 = findViewById(R.id.radioButton5);
        rb_club2 = findViewById(R.id.radioButton6);

        ListadePartidos item = (ListadePartidos) getIntent().getSerializableExtra("partidoSeleccionado");
        club1.setImageResource(item.getImagen1());
        club2.setImageResource(item.getImgen1());
        rb_club1.setText(item.getClub1());
        rb_club2.setText(item.getClub2());

        nAadaptador = new NAadaptador(this,listas);
        lv_apuestas.setAdapter(nAadaptador);

        mDatabase.child("TercerPartido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String nombre = snapshot.child("usuario").getValue().toString();
                    String apuesta = snapshot.child("monto").getValue().toString();
                    String equipo = snapshot.child("club").getValue().toString();
                    listas.add(new ListaApuestas(nombre,equipo,apuesta));
                    nAadaptador.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void apostar(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            String email = user.getEmail();
            Query query = mDatabase.child("Usuarios").orderByChild("email").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String name = user.getDisplayName();
                        String email = user.getEmail();
                        String uid = user.getUid();
                        String caso1 = rb_club1.getText().toString();
                        String caso2 = rb_club2.getText().toString();
                        String monto = et_monto.getText().toString();
                        String saldo = dataSnapshot.child("saldo").getValue().toString();
                        int saldoint = Integer.parseInt(saldo);
                        int montoint = Integer.parseInt(monto);

                        if(!monto.isEmpty()){
                            if (saldoint >= montoint) {
                                int resta = saldoint - montoint;
                                String restaString = String.valueOf(resta);

                                if (rb_club1.isChecked()){
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("usuario", name);
                                    map.put("email", email);
                                    map.put("monto", monto);
                                    map.put("club", caso1);
                                    mDatabase.child("TercerPartido").push().setValue(map);

                                    Map<String, Object> map2 = new HashMap<>();
                                    map2.put("usuario", name);
                                    map2.put("email", email);
                                    map2.put("saldo", restaString);
                                    mDatabase.child("Usuarios").child(uid).updateChildren(map2);
                                    Toast.makeText(SeleccionTercerPartido.this,"La apuesta esta hecha, Mucha Suerte.",Toast.LENGTH_LONG).show();
                                    et_monto.setText("");
                                }else
                                if(rb_club2.isChecked()){
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("usuario", name);
                                    map.put("email", email);
                                    map.put("monto", monto);
                                    map.put("club", caso2);
                                    mDatabase.child("TercerPartido").push().setValue(map);

                                    Map<String, Object> map2 = new HashMap<>();
                                    map2.put("usuario", name);
                                    map2.put("email", email);
                                    map2.put("saldo", restaString);
                                    mDatabase.child("Usuarios").child(uid).updateChildren(map2);
                                    Toast.makeText(SeleccionTercerPartido.this,"La apuesta esta hecha, Mucha suerte.",Toast.LENGTH_LONG).show();
                                    et_monto.setText("");
                                }else {
                                    Toast.makeText(SeleccionTercerPartido.this,"Seleccione un equipo",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(SeleccionTercerPartido.this,"Saldo insuficiente",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SeleccionTercerPartido.this,"Ingrese un monto a su apuesta",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        }else {
            Toast.makeText(SeleccionTercerPartido.this,"Debe iniciar sesion",Toast.LENGTH_SHORT).show();
        }
    }
}