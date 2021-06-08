package com.joctypo.finaleco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnLogin;
    TextView register;
    FirebaseAuth auth;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword =  findViewById(R.id.etPasswordLogin);
        btnLogin =  findViewById(R.id.btnLogin);
        register =  findViewById(R.id.tvRegister);
        db=FirebaseDatabase.getInstance();


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

                   FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                   //Verifica que tipo de usuario es
                    db.getReference().child("users").child(user.getUid()).addValueEventListener( new ValueEventListener() {


                        @Override
                        public void onDataChange( DataSnapshot snapshot) {

                            User user = snapshot.getValue(User.class);
                            Intent intent;

                            switch(user.getRol()){

                                case "designer":
                                    intent = new Intent(getApplicationContext(),HomeDesignerActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;

                                case "user":
                                     intent = new Intent(getApplicationContext(),HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                                    break;
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });

               }

               else {

                   Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
               }
            });
        }
    }
}