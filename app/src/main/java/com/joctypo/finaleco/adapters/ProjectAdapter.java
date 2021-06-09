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
import com.joctypo.finaleco.model.Project;
import com.joctypo.finaleco.fragments.ProjectDialogFragment;
import com.joctypo.finaleco.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProjectAdapter extends BaseAdapter {

    ArrayList<Project> projectArrayList;
    ProjectDialogFragment dialog;
    FirebaseDatabase db;

    public ProjectAdapter() {

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
        convertView = layoutInflater.inflate(R.layout.projects_for_me, null);
        TextView proyectDescription = convertView.findViewById(R.id.tvProjectDescription);
        TextView tvNumberComents = convertView.findViewById(R.id.tvNumberComents);
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
        DateFormat df = new SimpleDateFormat("MMM");
        String month = df.format(projectArrayList.get(position).getMonth());
        Log.e("TAG", month);

        db.getReference().child("comments").orderByChild("projectId").equalTo(projectArrayList.get(position).getId()).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {

                    imgComment.setImageResource(R.drawable.ic_comment_number);
                    tvNumberComents.setText(String.valueOf(snapshot.getChildrenCount()));

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }


        });

        tvDate.setText(month + "" + projectArrayList.get(position).getDay());
        convertView.setOnClickListener(v -> {

            OpenFragment(parent.getContext(), projectArrayList.get(position).getId());

        });

        return convertView;
    }


    public void AddNewProject(Project project) {

        projectArrayList.add(project);
        notifyDataSetChanged();

    }


    public void OpenFragment(Context context, String id) {

        Bundle args = new Bundle();
        args.putString("id", id);
        dialog = ProjectDialogFragment.newInstance();
        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
        dialog.setArguments(args);
        dialog.show(manager, "dialog");

    }

    public void Clear() {

        projectArrayList.clear();
    }

}
