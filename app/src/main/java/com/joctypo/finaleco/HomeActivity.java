package com.joctypo.finaleco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    ImageView btnProfile,btn2d,btn3d,btnvideo;
    Button btnPublishProject;
    EditText etProjectDescription;
    String category;
    FirebaseDatabase db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnProfile =  findViewById(R.id.btnProfile);
        btn2d = findViewById(R.id.img2d);
        btn3d = findViewById(R.id.img3d);
        btnvideo =  findViewById(R.id.imgVideo);
        btnPublishProject =  findViewById(R.id.btnPublishProject);
        etProjectDescription=findViewById(R.id.etProjectDescription);
        db=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        category="";

        btnPublishProject.setOnClickListener(v->{

            if(etProjectDescription.getText().toString().isEmpty()){

                Toast.makeText(this, "Necesitas agregar una descripción a tu proyecto",Toast.LENGTH_SHORT).show();

            }
            else if(category.equals("")){

                Toast.makeText(this,"por favor seleccione una categoria",Toast.LENGTH_SHORT).show();
            }
            else{

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){

                String id = UUID.randomUUID().toString();

                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);

                Project project = new Project(id, user.getUid(), etProjectDescription.getText().toString(),category,day,month);

                db.getReference().child("projects").child(id).setValue(project).addOnCompleteListener(task->{

                    if(task.isSuccessful()){

                        Toast.makeText(this, "proyecto subido correctamente",Toast.LENGTH_SHORT).show();
                        etProjectDescription.setText("");
                    }
                });

                }

            }

        });

        btn2d.setOnClickListener(v->{

           btn2d.setImageResource(R.drawable.ic_diseno_2d);
           btn3d.setImageResource(R.drawable.ic_modelado3d_nobg);
           btnvideo.setImageResource(R.drawable.ic_fotografia_nobg);
           category="diseño2D";
        });

        btn3d.setOnClickListener(v->{

            btn3d.setImageResource(R.drawable.ic_diseno_3d);
            btn2d.setImageResource(R.drawable.ic_diseno2d_nobg);
            btnvideo.setImageResource(R.drawable.ic_fotografia_nobg);
            category="modelado3D";
        });

        btnvideo.setOnClickListener(v->{

            btn3d.setImageResource(R.drawable.ic_modelado3d_nobg);
            btn2d.setImageResource(R.drawable.ic_diseno2d_nobg);
            btnvideo.setImageResource(R.drawable.ic_fotografia);
            category="video";
        });


        //boton del menu que lleva al perfil
        btnProfile.setOnClickListener(v->{

            Intent intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        });
    }
}