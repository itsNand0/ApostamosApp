package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaUsuarios extends AppCompatActivity {
    private ListView lv_usuario;
    private ArrayList<String> listusuario;
    private String nombre,cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        lv_usuario = (ListView)findViewById(R.id.lv_usuario);
        listusuario = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listusuario);
        lv_usuario.setAdapter(adapter);
        listaUsuario();
        }

    private void listaUsuario(){
        BDdeUsuarios admin = new BDdeUsuarios(this,"administrador",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from usuario",null);
        if (fila.moveToFirst()){
            do{
              listusuario.add(fila.getString(0));
              listusuario.add(fila.getString(1));
              listusuario.add(fila.getString(2));
              listusuario.add(fila.getString(3));
              listusuario.add(fila.getString(4));
              listusuario.add(fila.getString(5));
              listusuario.add(fila.getString(6));
              listusuario.add("Siguiente Usuario");
            }while (fila.moveToNext());
        }
    }

}
