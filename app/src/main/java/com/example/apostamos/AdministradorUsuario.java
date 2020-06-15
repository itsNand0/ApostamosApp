package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdministradorUsuario extends AppCompatActivity {
    private EditText et_cedula,et_nombre,et_apellido,et_telefono,et_correo,et_usuario,et_contraseña;

    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.administrador_usuario, mimenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.todos){
            Intent i = new Intent(this, ListaUsuarioPrueba.class);
            //i.putExtra("usuarios", );
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_usuario);

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
    }

    public void consultar(View view){
        BDdeUsuarios admin = new BDdeUsuarios(this,"administrador",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String ced = et_cedula.getText().toString();
        Cursor fila = bd.rawQuery("select nombre,apellido,telefono,correo,usuario,contraseña from usuario where cedula="+ced,null);
        if(fila.moveToFirst()){
            et_nombre.setText(fila.getString(0));
            et_apellido.setText(fila.getString(1));
            et_telefono.setText(fila.getString(2));
            et_correo.setText(fila.getString(3));
            et_usuario.setText(fila.getString(4));
            et_contraseña.setText(fila.getString(5));
        }else{
            Toast.makeText(this,"El usuario no existe",Toast.LENGTH_LONG).show();
            bd.close();
        }
    }

    public void borrar(View view){
        BDdeUsuarios admin = new BDdeUsuarios(this,"administrador",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String ced = et_cedula.getText().toString();
        String usu = et_usuario.getText().toString();
        int cantUno = bd.delete("usuario", "cedula="+ced,null);
        bd.close();
        et_nombre.setText("");
        et_apellido.setText("");
        et_telefono.setText("");
        et_correo.setText("");
        et_usuario.setText("");
        et_contraseña.setText("");
        if(cantUno==1){
            Toast.makeText(this,"Usuario eliminado",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Usuario inexistente",Toast.LENGTH_LONG).show();
        }
    }


    public void modificar(View view){
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
        int cant = bd.update("usuario",registro,"cedula="+ced,null);
        bd.close();
        if(cant==1){
            Toast.makeText(this,"Usuario Modificado",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Usuario inexistente",Toast.LENGTH_LONG).show();
        }
    }
}
