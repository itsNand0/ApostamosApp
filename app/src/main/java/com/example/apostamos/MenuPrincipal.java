package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;


public class MenuPrincipal extends AppCompatActivity {
    private TextView tv_usuario;
    private ListView lv_clubes;
    String[][] datos = {
            {"Olimpia"},
            {"Guarani"},
            {"Nacional"},
            {"Sol"},
            {"Gral Diaz"},
            {"San Lorenzo"},
            {"Guaireña"},
            {"Luqueño"},
            {"River Plate"},
            {"Libertad"},
            {"Cerro Porteño"},
            {"Doce de octubre"},

    };
    int[] img_club = {R.drawable.olimpia,R.drawable.guarani,R.drawable.nacional,R.drawable.sol,R.drawable.generaldiaz,R.drawable.san_lorenzo,R.drawable.guairena,R.drawable.sportivo_luqueno,R.drawable.river_plate,R.drawable.club_libertad,R.drawable.cerro_porteno,R.drawable.club_doce_de_octubre_};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        lv_clubes = (ListView)findViewById(R.id.lv_clubes);
        lv_clubes.setAdapter(new MPadaptador(this,datos,img_club));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menuprincipal, mimenu);
        return true;
    }
}
