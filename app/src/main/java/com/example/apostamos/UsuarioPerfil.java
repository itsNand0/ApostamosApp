package com.example.apostamos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UsuarioPerfil extends AppCompatActivity {
    private TextView tv_nombre, tv_email, tv_saldo, tv_cedula, tv_celular;
    private ImageView img_perfil;
    private Button btn_editarFoto;
    private static final int GALLERY_INTENT = 1;
    private StorageReference mStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);

        mStorage = FirebaseStorage.getInstance().getReference();
        tv_cedula = findViewById(R.id.tv_ci);
        tv_celular = findViewById(R.id.tv_celular);
        tv_email = findViewById(R.id.tv_email);
        tv_nombre = findViewById(R.id.tv_nombre);
        tv_saldo = findViewById(R.id.tv_saldo);


        btn_editarFoto = findViewById(R.id.btn_editarFoto);
        btn_editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_INTENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Uri uri = data.getData();
            StorageReference filepath = mStorage.child("FotoPerfil").child(uri.getEncodedPath());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UsuarioPerfil.this,"Has cambiado tu foto de perfil",Toast.LENGTH_LONG).show();
                }
            });


    }
}