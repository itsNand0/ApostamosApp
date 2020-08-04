package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;


public class SeleccionPrimerPartido extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText et_monto;
    private RadioButton rb_club1,rb_club2;
    private NAadaptador nAadaptador;
    private ArrayList<ListaApuestas> listas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_primer_partdio);
        et_monto = findViewById(R.id.et_monto);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final ImageView club1 = findViewById(R.id.imageView);
        ImageView club2 = findViewById(R.id.imageView3);
        rb_club1 = findViewById(R.id.radioButton);
        rb_club2 = findViewById(R.id.radioButton2);

        ListadePartidos item = (ListadePartidos) getIntent().getSerializableExtra("partidoSeleccionado");
        club1.setImageResource(item.getImagen1());
        club2.setImageResource(item.getImgen1());
        rb_club1.setText(item.getClub1());
        rb_club2.setText(item.getClub2());


        nAadaptador = new NAadaptador(this,listas);
        final ListView lv_apuestas = findViewById(R.id.lv_apuestas);
        lv_apuestas.setAdapter(nAadaptador);
        mDatabase.child("PrimerPartido").addValueEventListener(new ValueEventListener() {
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

        lv_apuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SeleccionPrimerPartido.this);

                LayoutInflater inflater = getLayoutInflater();
                View view1 = inflater.inflate(R.layout.alert_dialog_ir_contra,null);
                final TextView Usuario = view1.findViewById(R.id.textView42);
                final TextView Monto = view1.findViewById(R.id.textView40);
                TextView Club = view1.findViewById(R.id.textView41);
                ListaApuestas ItemPosition = listas.get(position);
                Usuario.setText(ItemPosition.getApostador());
                Monto.setText(ItemPosition.getClub());
                Club.setText(ItemPosition.getApuesta());

                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.setTitle("Ir en Contra");
                dialog.show();

                Button btn_confirmar = view1.findViewById(R.id.button8);
                btn_confirmar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String uid = user.getUid();
                        final String email = user.getEmail();
                        final String usuario = user.getDisplayName();
                        mDatabase.child("Usuarios").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String ApuestaOponente = Monto.getText().toString();

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    String SaldoUsuario = dataSnapshot.child("saldo").getValue().toString();
                                    int intSaldoUsuario = Integer.parseInt(SaldoUsuario);
                                    final int intApuestaOponente = Integer.parseInt(ApuestaOponente);
                                    if (intSaldoUsuario >= intApuestaOponente) {
                                        int resultado = intSaldoUsuario - intApuestaOponente;
                                        final Map<String,Object> map = new HashMap<>();
                                        map.put("saldo", resultado);
                                        mDatabase.child("Usuarios").child(uid).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SeleccionPrimerPartido.this,"Apuesta Hecha, se ha descontado "+intApuestaOponente+" de su Saldo",Toast.LENGTH_SHORT).show();
                                                Map<String, Object> map1 = new HashMap<>();
                                                map1.put("En contra de",Usuario);
                                                map1.put("Monto",Monto);
                                                map1.put("email", email);
                                                map1.put("usuario", usuario);
                                                mDatabase.child("PrimerPartido").push().setValue(map1);
                                            }
                                        });
                                    }else {
                                        Toast.makeText(SeleccionPrimerPartido.this,"Saldo insuficiente",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
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

                        if(!TextUtils.isEmpty(monto)){
                            if (saldoint >= montoint) {
                                int resta = saldoint - montoint;
                                String restaString = String.valueOf(resta);

                                if (rb_club1.isChecked()){
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("usuario", name);
                                    map.put("email", email);
                                    map.put("monto", monto);
                                    map.put("club", caso1);
                                    mDatabase.child("PrimerPartido").push().setValue(map);

                                    Map<String, Object> map2 = new HashMap<>();
                                    map2.put("saldo", restaString);
                                    mDatabase.child("Usuarios").child(uid).updateChildren(map2);
                                    Toast.makeText(SeleccionPrimerPartido.this,"La apuesta esta hecha, Mucha Suerte.",Toast.LENGTH_LONG).show();
                                    et_monto.setText("");
                                }else
                                    if(rb_club2.isChecked()){
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("usuario", name);
                                        map.put("email", email);
                                        map.put("monto", monto);
                                        map.put("club", caso2);
                                        mDatabase.child("PrimerPartido").push().setValue(map);

                                        Map<String, Object> map2 = new HashMap<>();
                                        map2.put("saldo", restaString);
                                        mDatabase.child("Usuarios").child(uid).updateChildren(map2);
                                        Toast.makeText(SeleccionPrimerPartido.this,"La apuesta esta hecha, Mucha suerte.",Toast.LENGTH_LONG).show();
                                        et_monto.setText("");
                                    }else {
                                        Toast.makeText(SeleccionPrimerPartido.this,"Seleccione un equipo",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(SeleccionPrimerPartido.this,"Saldo insuficiente",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(SeleccionPrimerPartido.this,"Ingrese un monto a su apuesta",Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        }else {
            Toast.makeText(SeleccionPrimerPartido.this,"Debe iniciar sesion",Toast.LENGTH_SHORT).show();
        }
    }

}
