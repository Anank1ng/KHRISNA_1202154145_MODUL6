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

public class LoginActivity extends AppCompatActivity {

    //deklarasi variable
    EditText logEmail, logPass;
    Button btnLog, btnReg;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //bikin auth dari firebase pake feature email/password
        auth = FirebaseAuth.getInstance();

        //jika user sudah login langsung ke main activity
        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //referencing
        logEmail = findViewById(R.id.etEmail);
        logPass = findViewById(R.id.etPass);
        btnReg = findViewById(R.id.btnRegister);
        btnLog = findViewById(R.id.btnLogin);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logFirebase();
            }
        });
    }

    private void logFirebase() {
        String email = logEmail.getText().toString();
        String pass = logPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "periksa kembali email Anda", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "periksa kembali password Anda", Toast.LENGTH_SHORT).show();
        }

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //jika ngga berhasil login
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login tidak berhasil", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
