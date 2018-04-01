package com.example.anantyakhrisna.khrisna_1202154145_modul6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    //deklarasi variable
    EditText regEmail, regPass;
    Button btnReg;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //bikin auth dari firebase pake feature email/password
        auth = FirebaseAuth.getInstance();

        //referencing
        regEmail = findViewById(R.id.etEmail);
        regPass = findViewById(R.id.etPass);
        btnReg = findViewById(R.id.btnRegister);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manggil method regFirebase
                regFirebase();
            }
        });
    }

    private void regFirebase() {
        String email = regEmail.getText().toString();
        String pass = regPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "periksa kembali email Anda", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "periksa kembali password Anda", Toast.LENGTH_SHORT).show();
        }

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //jika ngga berhasil register
                        if(!task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "Register Tidak Berhasil!", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
