package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaUsuarios extends AppCompatActivity {
    private ListView lv_usuario;
    private  String[] lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        lv_usuario = (ListView)findViewById(R.id.lv_usuario);

        BDdeUsuarios admin = new BDdeUsuarios(ListaUsuarios.this,"administrador",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select usuario,contraseña from usuario",null);
        if(fila.moveToFirst()){
            lista[0] = fila.getString(0);
            lista[1] = fila.getString(1);
         }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lista);
        lv_usuario.setAdapter(adapter);

    }


}
