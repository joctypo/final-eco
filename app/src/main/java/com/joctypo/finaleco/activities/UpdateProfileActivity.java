package com.joctypo.finaleco.activities;

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
import com.joctypo.finaleco.R;
import com.joctypo.finaleco.activities.ProfileActivity;
import com.joctypo.finaleco.model.User;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText etAge,etInstitution,etProfesion;
    TextView tvName2;
    Button btnUpdate;
    FirebaseAuth auth;
    FirebaseDatabase db;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvName2 = findViewById(R.id.tvName2);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        etAge = findViewById(R.id.etAge);
        etInstitution = findViewById(R.id.etInstitution);
        etProfesion = findViewById(R.id.etProfesion);
        LoadProfile();

        btnUpdate.setOnClickListener(v->{

            //Verifica que todos los inputs tengan valor
            if(etAge.getText().toString().isEmpty()||etInstitution.getText().toString().isEmpty()||etProfesion.getText().toString().isEmpty()){

                Toast.makeText(this, "Por favor llene todos las datos para actualizar su perfil",Toast.LENGTH_SHORT).show();
            }
            else{

                if(currentUser!=null){

                    currentUser.setAge(etAge.getText().toString());
                    currentUser.setInstitution(etInstitution.getText().toString());
                    currentUser.setProfesion(etProfesion.getText().toString());

                    db.getReference().child("users").child(currentUser.getId()).setValue(currentUser).addOnCompleteListener(task->{

                        Intent intent = new Intent(this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    });


                }
            }

        });


    }


    private void LoadProfile(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {



            db.getReference().child("users").child(user.getUid()).addValueEventListener( new ValueEventListener() {


                @Override
                public void onDataChange( DataSnapshot snapshot) {

                    currentUser = snapshot.getValue(User.class);

                    tvName2.setText(currentUser.getName());
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }







    }
}