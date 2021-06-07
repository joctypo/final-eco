package com.joctypo.finaleco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnLogin;
    TextView register;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword =  findViewById(R.id.etPasswordLogin);
        btnLogin =  findViewById(R.id.btnLogin);
        register =  findViewById(R.id.tvRegister);


        btnLogin.setOnClickListener(v->{

           Login();
        });

        register.setOnClickListener(v->{

            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);


        });
    }


    private void Login(){

        if(etEmail.getText().toString().isEmpty()||etPassword.getText().toString().isEmpty()){

            Toast.makeText(this, "Por favor complete todos los datos", Toast.LENGTH_SHORT).show();
        }

        else{
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

                //el usuario se logea
               if(task.isSuccessful()){

                   Intent intent = new Intent(this,HomeActivity.class);
                   startActivity(intent);

                   Toast.makeText(this, "Logeado papu",Toast.LENGTH_SHORT).show();
               }

               else {

                   Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
               }
            });
        }
    }
}