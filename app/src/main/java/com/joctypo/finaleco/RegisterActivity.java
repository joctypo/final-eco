package com.joctypo.finaleco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etEmailRegister,etPasswordRegister,etPhoneNumber;
    Button btnDesigner,btnClient,btnRegister;
    FirebaseDatabase db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);

        //definicion UI
        etName =  findViewById(R.id.etName);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnDesigner = findViewById(R.id.btnDesigner);
        btnClient = findViewById(R.id.btnClient);
        btnRegister = findViewById(R.id.btnRegister);

        db=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {

            Register();
        });



    }

    private void Register(){

        //dejó algun input vacio
    if(etName.getText().toString().isEmpty()||etEmailRegister.getText().toString().isEmpty()||
            etPasswordRegister.getText().toString().isEmpty()||etPhoneNumber.getText().toString().isEmpty()){

        Toast.makeText(this, "Por favor complete todos los datos", Toast.LENGTH_SHORT).show();
    }

    else{

        String name,email,password,phoneNumber;
        name = etName.getText().toString();
        email = etEmailRegister.getText().toString();
        password = etPasswordRegister.getText().toString();
        phoneNumber=etPhoneNumber.getText().toString();
     auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task ->{

         if(task.isComplete()){

             String id = auth.getCurrentUser().getUid();

             User user = new User(id,name,email,password,phoneNumber);

             db.getReference().child("users").child(id).setValue(user).addOnCompleteListener(registro ->{

                if(registro.isSuccessful()){

                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(this, registro.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
             });

         }
         else{

             Toast.makeText(this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
         }

     });








    }


    }
}