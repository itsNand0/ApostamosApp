package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class MenuPrincipal extends AppCompatActivity {
    private TextView tv_usuario;
    private ListView lv_clubes;
    private Spinner sp_fechas;
    String[] fechas = {"17 de Julio de 2020","24 de Julio de 2020"};
    String[] datos = {"Olimpia", "Guarani","Nacional","Sol", "Gral Diaz", "San Lorenzo","Guaireña","Luqueño","River Plate","Libertad","Cerro Porteño","Doce de octubre"};
    int[] img_club = {R.drawable.olimpia,R.drawable.guarani,R.drawable.nacional,R.drawable.sol,R.drawable.generaldiaz,R.drawable.san_lorenzo,R.drawable.guairena,R.drawable.sportivo_luqueno,R.drawable.river_plate,R.drawable.club_libertad,R.drawable.cerro_porteno,R.drawable.club_doce_de_octubre_};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        lv_clubes = (ListView)findViewById(R.id.lv_clubes);
        lv_clubes.setAdapter(new MPadaptador(this,datos,img_club));
        sp_fechas = (Spinner)findViewById(R.id.sp_fechas);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menuprincipal, mimenu);
        return true;
    }

    //logOut
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrar_sesion){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(MenuPrincipal.this,MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
