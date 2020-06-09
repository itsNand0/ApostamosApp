package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {
    private TextView tv_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        tv_usuario = findViewById(R.id.tv_usuario);

        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("usuario");
        tv_usuario.setText("Bienvenido " + dato);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menuprincipal, mimenu);
        return true;
    }
}
