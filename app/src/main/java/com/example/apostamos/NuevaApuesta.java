package com.example.apostamos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class NuevaApuesta extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText et_monto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_apuesta);

        et_monto = findViewById(R.id.et_monto);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void apostar(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
                String uid = user.getUid();
                String email =  user.getEmail();
                String name = user.getDisplayName();
                String monto = et_monto.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("usuario", name);
                map.put("email", email);
                map.put("monto", monto);
                mDatabase.child("apuestas").child(uid).setValue(map);
                Toast.makeText(this,"La apuesta esta hecha, Mucha suerte.",Toast.LENGTH_LONG).show();
                et_monto.setText("");
            }else {
            Toast.makeText(this,"Debe iniciar sesion",Toast.LENGTH_SHORT).show();
        }
    }


}
