package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_usuario, et_contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_usuario = (EditText)findViewById(R.id.et_user);
        et_contraseña = (EditText)findViewById(R.id.et_pass);
    }

    public void registrar(View view){
        Intent i = new Intent(this,Registrar.class);
        startActivity(i);
    }

    public void ingresar(View view){
        BDdeUsuarios admin = new BDdeUsuarios(this,"administrador",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String usu = et_usuario.getText().toString();
        String con = et_contraseña.getText().toString();
        Cursor fila = bd.rawQuery("select usuario from usuario where usuario='"+usu+"'"+" and contraseña='"+con+"'",null);
        if(usu.isEmpty()){
            Toast.makeText(this,"Debe insertar un usuario",Toast.LENGTH_LONG).show();
        }else if (con.isEmpty()){
            Toast.makeText(this,"La contraseña no puede estar vacia",Toast.LENGTH_LONG).show();
            }else{
                if(fila.moveToFirst()) {
                Intent i = new Intent(this, MenuPrincipal.class);
                //i.putExtra("usuario", et_usuario.getText().toString());
                startActivity(i);
                } else {
                Toast.makeText(this, "Usuario o contraseña incorrectas", Toast.LENGTH_LONG).show();
                }
            }
        }

        public void administrador(View view){
            Intent i = new Intent(this,AdministradorUsuario.class);
            startActivity(i);
        }
    }

