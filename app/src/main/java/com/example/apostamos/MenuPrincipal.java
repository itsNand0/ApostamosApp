package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuPrincipal extends AppCompatActivity {
    private TextView tv_usuario;
    private ExpandableListView esplv;
    private EsplistView adapter;
    private ArrayList<String> listCategoria;
    private Map<String, ArrayList<String>> mapChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        esplv = (ExpandableListView) findViewById(R.id.expLv);
        listCategoria = new ArrayList<>();
        mapChild = new HashMap<>();
        cargaDatos();

        esplv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent i = new Intent(MenuPrincipal.this, NuevaApuesta.class);
                startActivity(i);
                return true;
            }
        });
        /*tv_usuario = findViewById(R.id.tv_usuario);

        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("usuario");
        tv_usuario.setText("Bienvenido " + dato);*/

    }
    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menuprincipal, mimenu);
        return true;
    }

    private void cargaDatos(){
        ArrayList<String> listprimerJuego = new ArrayList<>();
        ArrayList<String> listsegundoJuego = new ArrayList<>();
        ArrayList<String> listtercerJuego = new ArrayList<>();
        ArrayList<String> listcuartoJuego = new ArrayList<>();
        ArrayList<String> listquintoJuego = new ArrayList<>();
        ArrayList<String> listsextoJuego = new ArrayList<>();

        listCategoria.add("");
        listCategoria.add("");
        listCategoria.add("");
        listCategoria.add("");
        listCategoria.add("");
        listCategoria.add("");

        listprimerJuego.add("Apuestas Disponibles");

        listsegundoJuego.add("Apuestas Disponibles");

        listtercerJuego.add("Apuestas Disponibles");

        listcuartoJuego.add("Apuestas Disponibles");

        listquintoJuego.add("Apuestas Disponibles");

        listsextoJuego.add("Apuestas Disponibles");

        mapChild.put(listCategoria.get(0), listprimerJuego);
        mapChild.put(listCategoria.get(1), listsegundoJuego);
        mapChild.put(listCategoria.get(2), listtercerJuego);
        mapChild.put(listCategoria.get(3), listcuartoJuego);
        mapChild.put(listCategoria.get(4), listquintoJuego);
        mapChild.put(listCategoria.get(5), listsextoJuego);

        adapter = new EsplistView(this, listCategoria, mapChild);
        esplv.setAdapter(adapter);
    }


}
