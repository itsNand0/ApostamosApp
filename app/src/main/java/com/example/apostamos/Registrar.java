package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registrar extends AppCompatActivity {
    private EditText et_cedula,et_nombre,et_apellido,et_telefono,et_correo,et_usuario,et_contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        et_cedula = (EditText)findViewById(R.id.et_cedula);
        et_nombre = (EditText)findViewById(R.id.et_nombre);
        et_apellido = (EditText)findViewById(R.id.et_apellido);
        et_telefono = (EditText)findViewById(R.id.et_telefono);
        et_correo = (EditText)findViewById(R.id.et_correo);
        et_usuario = (EditText)findViewById(R.id.et_usuario);
        et_contraseña = (EditText)findViewById(R.id.et_contraseña);

    }

    public void registrar(View view){
        BDdeUsuarios admin = new BDdeUsuarios(this,"administrador",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String ced = et_cedula.getText().toString();
        String nom = et_nombre.getText().toString();
        String ape = et_apellido.getText().toString();
        String tel = et_telefono.getText().toString();
        String cor = et_correo.getText().toString();
        String usu = et_usuario.getText().toString();
        String con = et_contraseña.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("cedula",ced);
        registro.put("nombre",nom);
        registro.put("apellido",ape);
        registro.put("telefono",tel);
        registro.put("correo",cor);
        registro.put("usuario",usu);
        registro.put("contraseña",con);
        bd.insert("usuario",null,registro);
        bd.close();
        Toast.makeText(this,"Se ha registrado con exito",Toast.LENGTH_LONG).show();

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
