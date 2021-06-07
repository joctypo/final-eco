package com.joctypo.finaleco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    ImageView btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnProfile =  findViewById(R.id.btnProfile);

        //boton del menu que lleva al perfil
        btnProfile.setOnClickListener(v->{

            Intent intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        });
    }
}