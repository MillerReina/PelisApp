package com.example.pelisapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    private Button btnRegister;
    private Button btnLogin;


    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        txtEmail = (TextInputLayout) findViewById(R.id.textEmail);
        txtPassword = (TextInputLayout) findViewById(R.id.textPassword);
        btnRegister = (Button) findViewById(R.id.button3);
        btnLogin = (Button) findViewById(R.id.button4);

        progressDialog = new ProgressDialog(this);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }


    private void register() {
        String email = txtEmail.getEditText().getText().toString().trim();
        String password = txtPassword.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingrese por favor un correo", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese por favor una contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Registrando usuario");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Se ha registrado el usuario ", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "El usuario ya fue registrado", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void login() {
        final String email = txtEmail.getEditText().getText().toString().trim();
        String password = txtPassword.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingrese por favor un correo", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese por favor una contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Iniciando sesión");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            int position = email.indexOf("@");
                            String user = email.substring(0, position);
                            Toast.makeText(MainActivity.this, "Bienvenido: "+user, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(), Bienvenido.class);
                            intent.putExtra(Bienvenido.USER_SERVICE, user);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "El usuario ya fue registrado", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button3:
                register();
                break;

            case R.id.button4:
                login();
                break;
        }
    }

}
