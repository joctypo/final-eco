package com.joctypo.finaleco.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joctypo.finaleco.R;
import com.joctypo.finaleco.fragments.ProjectDialogFragmentUser;
import com.joctypo.finaleco.model.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyProjectsAdapter extends BaseAdapter {

    ArrayList<Project> projectArrayList;
    ProjectDialogFragmentUser dialog;
    FirebaseDatabase db;

    public MyProjectsAdapter() {

        projectArrayList = new ArrayList<>();
        db = FirebaseDatabase.getInstance();


    }

    @Override
    public int getCount() {
        return projectArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        convertView = layoutInflater.inflate(R.layout.my_projects, null);
        TextView proyectDescription = convertView.findViewById(R.id.tvProjectDescriptionTaken);
        ImageView imgCategory = convertView.findViewById(R.id.imgCategory);
        ImageView imgComment = convertView.findViewById(R.id.imgComment);

        TextView tvDate = convertView.findViewById(R.id.tvDate);
        proyectDescription.setText(projectArrayList.get(position).getProjectDescription());
        switch (projectArrayList.get(position).getCategory()) {

            case "diseño2D":
                imgCategory.setImageResource(R.drawable.ic_diseno_2d);
                break;
            case "modelado3D":
                imgCategory.setImageResource(R.drawable.ic_diseno_3d);
                break;
            case "video":
                imgCategory.setImageResource(R.drawable.ic_fotografia);
                break;
        }

        String month ="";

        switch(projectArrayList.get(position).getMonth()){

            case 0:
                month= "ene";
                break;
            case 1:
                month= "feb";

                break;
            case 2:

                month= "mar";
                break;
            case 3:

                month= "abr";
                break;
            case 4:
                month= "may";
                break;
            case 5:
                month= "jun";
                break;
            case 6:
                month= "jul";
                break;
            case 7:
                month= "ago";
                break;
            case 8:
                month= "sep";
                break;
            case 9:
                month= "oct";
                break;
            case 10:
                month= "nov";
                break;
            case 11:
                month= "dic";
                break;
        }


        tvDate.setText(month + " " + projectArrayList.get(position).getDay());
//        convertView.setOnClickListener(v -> {
//
//            OpenFragment(parent.getContext(), projectArrayList.get(position).getId());
//
//        });

        return convertView;
    }


    public void AddNewProject(Project project) {

        projectArrayList.add(project);
        notifyDataSetChanged();

    }


//    public void OpenFragment(Context context, String id) {
//
//        Bundle args = new Bundle();
//        args.putString("id", id);
//        dialog = ProjectDialogFragmentUser.newInstance();
//        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
//        dialog.setArguments(args);
//        dialog.show(manager, "dialog");
//
//    }

    public void Clear() {

        projectArrayList.clear();
    }
}
