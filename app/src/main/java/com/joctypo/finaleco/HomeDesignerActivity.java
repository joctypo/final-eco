package com.joctypo.finaleco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeDesignerActivity extends AppCompatActivity {

    ListView listviewMyProjects,listViewProjects;
    FirebaseDatabase db;
    FirebaseAuth auth;
    ProjectAdapter myProjectAdapter;
    ImageView btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_designer);

        listviewMyProjects=findViewById(R.id.listviewMyProjects);
        listViewProjects=findViewById(R.id.listViewProjects);
        btnProfile=findViewById(R.id.btnProfile);
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myProjectAdapter = new ProjectAdapter();

        btnProfile.setOnClickListener(v -> {

            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        LoadProjects();

    }

    private void LoadProjects() {

        db.getReference().child("projects").addValueEventListener(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {

                listViewProjects.setAdapter(myProjectAdapter);

                myProjectAdapter.Clear();
                for (DataSnapshot child:
                     snapshot.getChildren()) {

                    Project project = child.getValue(Project.class);
                    myProjectAdapter.AddNewProject(project);

                }

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

    }

}