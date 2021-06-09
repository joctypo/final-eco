package com.joctypo.finaleco.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joctypo.finaleco.R;
import com.joctypo.finaleco.model.Project;
import com.joctypo.finaleco.model.User;

public class ContractActivity extends AppCompatActivity {

    FirebaseDatabase db;
    ImageView imageViewProfile4;
    TextView tvInstitution, tvAge, tvCareer, tvEmail, tvNameUserContract;
    String designerId, projectId;
    ImageButton btnContract;
    Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        Intent intent = getIntent();
        tvEmail = findViewById(R.id.tvEmail);
        tvAge = findViewById(R.id.tvAge);
        tvInstitution = findViewById(R.id.tvInstitution);
        tvCareer = findViewById(R.id.tvCareer);
        imageViewProfile4 = findViewById(R.id.imageViewProfile4);
        tvNameUserContract = findViewById(R.id.tvNameUserContract);
        btnContract = findViewById(R.id.btnContract);
        db = FirebaseDatabase.getInstance();
        designerId = intent.getStringExtra("designerId");
        projectId = intent.getStringExtra("projectId");

        LoadProfile();

        btnContract.setOnClickListener(v -> {

            //cambia valor de proyecto

            db.getReference().child("projects").child(projectId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    project = snapshot.getValue(Project.class);

                    project.setTaken(true);
                    project.setDesignerId(designerId);

                    db.getReference().child("projects").child(projectId).setValue(project).addOnCompleteListener(task -> {


                                if (task.isSuccessful()) {

                                    db.getReference().child("onGoing").child(projectId).setValue(project).addOnCompleteListener(query->{

                                        Toast.makeText(getApplicationContext(),"Diseñador contratado",Toast.LENGTH_SHORT).show();
                                        finish();
                                    });

                                }
                            }


                    );
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        });
    }

    private void LoadProfile() {

        db.getReference().child("users").child(designerId).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                switch (user.getRol()) {

                    case "designer":
                        imageViewProfile4.setImageResource(R.drawable.weyui);
                        break;
                    case "client":
                        imageViewProfile4.setImageResource(R.drawable.weyui2);
                        break;
                }
                tvEmail.setText(user.getEmail());
                tvNameUserContract.setText(user.getName());
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