package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MenuPrincipal extends AppCompatActivity {
    private DatabaseReference mDataBase;
    private ListView lv_clubes;
    private Spinner sp_fechas;
    private MPadaptador mPadaptador;
    private ArrayList<ListadePartidos> listadePartidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        listadePartidos = fechaDiecisiete();
        mDataBase = FirebaseDatabase.getInstance().getReference("Usuarios");

            lv_clubes = findViewById(R.id.lv_clubes);
            lv_clubes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent i = new Intent(MenuPrincipal.this, SeleccionPrimerPartido.class);
                    i.putExtra("partidoSeleccionado",listadePartidos.get(position));
                    startActivity(i);
                }else if (position == 1){
                    Intent i = new Intent(MenuPrincipal.this, SeleccionSegundoPartido.class);
                    i.putExtra("partidoSeleccionado",listadePartidos.get(position));
                    startActivity(i);
                }else if (position == 2){
                    Intent i = new Intent(MenuPrincipal.this, SeleccionTercerPartido.class);
                    i.putExtra("partidoSeleccionado",listadePartidos.get(position));
                    startActivity(i);
                }else if (position == 3){
                    Intent i = new Intent(MenuPrincipal.this, SeleccionCuartoPartido.class);
                    i.putExtra("partidoSeleccionado",listadePartidos.get(position));
                    startActivity(i);
                }else if (position == 4){
                    Intent i = new Intent(MenuPrincipal.this, SeleccionQuintoPartido.class);
                    i.putExtra("partidoSeleccionado",listadePartidos.get(position));
                    startActivity(i);
                }else if (position == 5){
                    Intent i = new Intent(MenuPrincipal.this, SeleccionSextoPartido.class);
                    i.putExtra("partidoSeleccionado",listadePartidos.get(position));
                    startActivity(i);
                }
            }
        });

        sp_fechas = findViewById(R.id.sp_fechas);
        final ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.fechas,R.layout.spinner_item_personalizado);
        sp_fechas.setAdapter(spinnerAdapter);
        sp_fechas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("17 de Julio")){
                    mPadaptador = new MPadaptador(MenuPrincipal.this, listadePartidos);
                    lv_clubes.setAdapter(mPadaptador);
                }
                if (parent.getItemAtPosition(position).equals("19 de Julio")){
                    mPadaptador = new MPadaptador(MenuPrincipal.this, fechaDiecisiete());
                    lv_clubes.setAdapter(mPadaptador);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mDataBase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("permiso").getValue().equals(true)){
                    sp_fechas.setVisibility(View.VISIBLE);
                }else {
                    sp_fechas.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private ArrayList<ListadePartidos> fechaDiecisiete(){
        ArrayList<ListadePartidos> listaJuegos = new ArrayList<>();
        listaJuegos.add(new ListadePartidos(R.drawable.sportivo_luqueno,"luqueño",R.drawable.olimpia,"Olimpia"));
        listaJuegos.add(new ListadePartidos(R.drawable.guairena,"Guaireña",R.drawable.guarani,"Guarani"));
        listaJuegos.add(new ListadePartidos(R.drawable.generaldiaz,"Gral Diaz",R.drawable.san_lorenzo,"San Lorenzo"));
        listaJuegos.add(new ListadePartidos(R.drawable.river_plate,"River Plate",R.drawable.nacional,"Nacional"));
        listaJuegos.add(new ListadePartidos(R.drawable.sol,"Sol de America",R.drawable.club_doce_de_octubre_,"12 de octubre"));
        listaJuegos.add(new ListadePartidos(R.drawable.cerro_porteno,"Cerro Porteño",R.drawable.club_libertad,"Libertad"));
        return listaJuegos;
    }


    //referencia al ActivityBar creado
    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menuprincipal, mimenu);
        return true;
    }

    // Funcion para la barra de menu.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrar_sesion){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(MenuPrincipal.this,MainActivity.class);
            startActivity(i);
        }else
            if(id == R.id.mi_cuenta){
                Intent i = new Intent(this,MiCuenta.class);
                startActivity(i);
            }else
                if(id == R.id.administrador){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    mDataBase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("permiso").getValue().equals(true)){
                                Intent i = new Intent(MenuPrincipal.this,MenuAdministrador.class);
                                startActivity(i);
                            }else {
                                Toast.makeText(MenuPrincipal.this,"No esta autorizado",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
        return super.onOptionsItemSelected(item);
    }
}
