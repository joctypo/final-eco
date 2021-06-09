package com.joctypo.finaleco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView tvEmail, tvName, tvEditProfile, tvAge, tvInstitution, tvCareer;
    FirebaseAuth auth;
    FirebaseDatabase db;
    ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvEmail = findViewById(R.id.tvEmail);
        tvEditProfile = findViewById(R.id.tvEditProfile);
        tvAge = findViewById(R.id.tvAge);
        tvInstitution = findViewById(R.id.tvInstitution);
        tvCareer = findViewById(R.id.tvCareer);
        tvName = findViewById(R.id.tvName);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        imageViewProfile = findViewById(R.id.imageViewProfile);

        LoadProfile();

        tvEditProfile.setOnClickListener(v -> {

            Intent intent = new Intent(this, UpdateProfileActivity.class);
            startActivity(intent);
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
                        imageViewProfile.setImageResource(R.drawable.weyui);
                        break;
                    case "client":
                        imageViewProfile.setImageResource(R.drawable.weyui2);
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