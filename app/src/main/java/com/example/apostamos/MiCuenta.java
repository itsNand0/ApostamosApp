package com.example.apostamos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;


public class MiCuenta extends AppCompatActivity {
    private DatabaseReference mDataBase;
    private TextView tv_saldo,tv_nombre;
    private Button btn_cargar,btn_retirar, btn_validar;
    private ImageView img_foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);
        tv_saldo = findViewById(R.id.tv_saldo);
        tv_nombre = findViewById(R.id.tv_nombre);
        btn_validar = findViewById(R.id.button9);
        btn_cargar = findViewById(R.id.btn_cargar_saldo);
        btn_retirar = findViewById(R.id.btn_retirar);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        img_foto = findViewById(R.id.img_foto_circular);
        Picasso.with(this).
                load(user.getPhotoUrl()).
                fit().
                into(img_foto);
        tv_nombre.setText(user.getDisplayName());

        String email = user.getEmail();
        Query query = mDataBase.child("Usuarios").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   String saldo = dataSnapshot.child("saldo").getValue().toString();
                   tv_saldo.setText("Saldo: "+saldo+" Gs");
                   btn_validar.setVisibility(View.INVISIBLE);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MiCuenta.this);
                alert.setMessage("Antes de continuar asegurese de tener este numero entre sus contactos, 0975285091")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String emailcargar = user.getEmail();
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "Buenas, quisiera cargar mi saldo. \n Email: "+emailcargar+ "\n Monto: " );
                                sendIntent.setType("text/plain");
                                sendIntent.setPackage("com.whatsapp");
                                startActivity(sendIntent);

                                finish();
                            }
                        });
                AlertDialog titulo = alert.create();
                titulo.setTitle("WhatsApp");
                titulo.show();
            }
        });

        btn_validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = user.getUid();
                String nombre = user.getDisplayName();
                String email = user.getEmail();
                Boolean administrador = false;

                Map<String, Object> map = new HashMap<>();
                map.put("email",email);
                map.put("usuario",nombre);
                map.put("permiso",administrador);
                mDataBase.child("Usuarios").child(uid).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MiCuenta.this, "Gracias, su cuenta ha sido validada",Toast.LENGTH_LONG).show();
                        btn_validar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        btn_retirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarDialogAlert();
            }
        });

    }

    private void MostrarDialogAlert(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AlertDialog.Builder builder = new AlertDialog.Builder(MiCuenta.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.retirar_alert_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        final TextView tv_saldo_retirar = view.findViewById(R.id.textView17);
        final EditText et_valor_retirar = view.findViewById(R.id.editTextNumber);
        final EditText et_celular = view.findViewById(R.id.editTextNumber2);

        String email = user.getEmail();
        Query query = mDataBase.child("Usuarios").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String saldo = dataSnapshot.child("saldo").getValue().toString();
                    tv_saldo_retirar.setText(saldo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btn_enviar = view.findViewById(R.id.button7);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = user.getDisplayName();
                String uid = user.getUid();
                String saldo_retirar = tv_saldo_retirar.getText().toString();
                String valor_retirar = et_valor_retirar.getText().toString();
                String celular = et_celular.getText().toString();
                int saldoint = Integer.parseInt(saldo_retirar);
                int valorint = Integer.parseInt(valor_retirar);
                if (!TextUtils.isEmpty(valor_retirar)){
                    if (valorint <= saldoint){
                        int resu = valorint - saldoint;
                        Map<String,Object>map = new HashMap<>();
                        map.put("restirar",valorint);
                        map.put("usuario",usuario);
                        map.put("celular",celular);
                        mDataBase.child("Retiro").child(uid).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                               Toast.makeText(MiCuenta.this,"En unos minutos recibira su giro, muchas gracias",Toast.LENGTH_LONG).show();
                            }
                        });
                        Map<String,Object> map1 = new HashMap<>();
                        map1.put("saldo",resu);
                         mDataBase.child("Usuarios").child(uid).updateChildren(map1);
                    }
                }
            }
        });
    }
}