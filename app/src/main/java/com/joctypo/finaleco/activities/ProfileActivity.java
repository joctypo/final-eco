package com.joctypo.finaleco.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joctypo.finaleco.R;
import com.joctypo.finaleco.model.User;

public class ProfileActivity extends AppCompatActivity {

    TextView tvEmail, tvName, tvEditProfile, tvAge, tvInstitution, tvCareer;
    FirebaseAuth auth;
    FirebaseDatabase db;
    ImageView imageViewProfile,btnHome;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvEmail = findViewById(R.id.tvEmail);
        tvEditProfile = findViewById(R.id.tvEditProfile);
        tvAge = findViewById(R.id.tvAge);
        tvInstitution = findViewById(R.id.tvInstitution);
        btnHome=findViewById(R.id.btnHome);
        tvCareer = findViewById(R.id.tvCareer);
        tvName = findViewById(R.id.tvName);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        imageViewProfile = findViewById(R.id.imageViewProfile);
        logout = findViewById(R.id.logout);

        LoadProfile();

        logout.setOnClickListener(v-> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Salir")
                    .setMessage("¿Estas seguro de salir?")
                    .setNegativeButton("No",(dialog,id)->{
                        dialog.dismiss();
                    })
                    .setPositiveButton("Si" ,(dialog,id)->{
                        auth.signOut();
                        Intent i = new Intent(this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    });
            builder.show();

        });

        tvEditProfile.setOnClickListener(v -> {

            Intent intent = new Intent(this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        });
        btnHome.setOnClickListener(v -> {

            finish();
        });

    }

    private void LoadProfile() {

        String id = auth.getCurrentUser().getUid();

        db.getReference().child("users").child(id).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                switch (user.getRol()) {

                    case "designer":
                        imageViewProfile.setImageResource(R.drawable.imagen);
                        break;
                    case "client":
                        imageViewProfile.setImageResource(R.drawable.imagen2);
                        break;
                }
                tvEmail.setText(user.getEmail());
                tvName.setText(user.getName());
                tvAge.setText(user.getAge());
                tvCareer.setText(user.getProfesion());
                tvInstitution.setText(user.getInstitution());

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }
}