package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaUsuarios extends AppCompatActivity {
    private ExpandableListView expanded_listaUsu;
    private EsplistView adapter;
    private ArrayList<String> listUsuario;
    private Map<String, ArrayList<String>> mapChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuario_prueba);

        expanded_listaUsu = (ExpandableListView) findViewById(R.id.expanded_listaUsu);
        listUsuario = new ArrayList<>();
        mapChild = new HashMap<>();
        cargarUsuario();

    }

    private void cargarUsuario(){

        BDdeUsuarios admin = new BDdeUsuarios(this,"administrador",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ArrayList<String> groupUsuario = new ArrayList<>();
        Cursor fila = bd.rawQuery("select cedula from usuario",null);
        if (fila.moveToFirst()){
            do{
                groupUsuario.add(fila.getString(0));
            }while (fila.moveToNext());
        }
        listUsuario.add("ci: " + groupUsuario);


        ArrayList<String> childUsuario = new ArrayList<>();
        Cursor childFila = bd.rawQuery("select nombre,apellido,telefono,correo,usuario,contraseña from usuario",null);
        if (childFila.moveToFirst()){
            do{
                childUsuario.add(childFila.getString(0));
                childUsuario.add(childFila.getString(1));
                childUsuario.add(childFila.getString(2));
                childUsuario.add(childFila.getString(3));
                childUsuario.add(childFila.getString(4));
                childUsuario.add(childFila.getString(5));
            }while (childFila.moveToNext());
        }


        mapChild.put(listUsuario.get(0), childUsuario);

        adapter = new EsplistView(this, listUsuario, mapChild);
        expanded_listaUsu.setAdapter(adapter);
    }

}
