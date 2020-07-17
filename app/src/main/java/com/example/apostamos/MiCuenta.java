package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MiCuenta extends AppCompatActivity {
    private TextView tv_saldo;
    private Button btn_cargar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);
        tv_saldo = findViewById(R.id.tv_saldo);

        btn_cargar = findViewById(R.id.btn_cargar_saldo);
        btn_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Buenas, quisiera cargar mi saldo. \n Email: \n Monto: ");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });
    }
}