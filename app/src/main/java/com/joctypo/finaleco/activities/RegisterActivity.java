package com.joctypo.finaleco.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.joctypo.finaleco.R;
import com.joctypo.finaleco.activities.HomeActivity;
import com.joctypo.finaleco.activities.HomeDesignerActivity;
import com.joctypo.finaleco.model.User;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmailRegister, etPasswordRegister, etPhoneNumber;
    Button btnDesigner, btnClient, btnRegister;
    FirebaseDatabase db;
    FirebaseAuth auth;
    String rol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);

        //definicion UI
        etName = findViewById(R.id.etName);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnDesigner = findViewById(R.id.btnDesigner);
        btnClient = findViewById(R.id.btnClient);
        btnRegister = findViewById(R.id.btnRegister);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> Register());

        btnDesigner.setOnClickListener(v -> {

            rol = "designer";
            btnDesigner.setBackgroundResource(R.drawable.btn_nobg);
            btnClient.setBackgroundResource(R.drawable.btn_unselected);

        });

        btnClient.setOnClickListener(v -> {

            rol = "client";
            btnDesigner.setBackgroundResource(R.drawable.btn_unselected);
            btnClient.setBackgroundResource(R.drawable.btn_nobg);

        });

    }

    private void Register() {

        //dejó algun input vacio


        if (etName.getText().toString().isEmpty() || etEmailRegister.getText().toString().isEmpty() ||
                etPasswordRegister.getText().toString().isEmpty() || etPhoneNumber.getText().toString().isEmpty() || rol.contentEquals("")) {


            Toast.makeText(this, "Por favor complete todos los datos", Toast.LENGTH_SHORT).show();

        } else {

            String name, email, password, phoneNumber;
            name = etName.getText().toString();
            email = etEmailRegister.getText().toString();
            password = etPasswordRegister.getText().toString();
            phoneNumber = etPhoneNumber.getText().toString();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                if (task.isComplete()) {


                    String id = auth.getCurrentUser().getUid();

                    User user = new User(id, name, email, phoneNumber, rol);

                    db.getReference().child("users").child(id).setValue(user).addOnCompleteListener(registro -> {

                        if (registro.isSuccessful()) {
                            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                            Intent intent;
                            switch (user.getRol()) {

                                case "designer":

                                    intent = new Intent(this, HomeDesignerActivity.class);
                                    startActivity(intent);
                                    break;

                                case "client":
                                    intent = new Intent(this, HomeActivity.class);
                                    startActivity(intent);
                                    break;

                            }


                        } else {

                            Toast.makeText(this, registro.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {

                    Toast.makeText(this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                }

            });


        }


    }
}