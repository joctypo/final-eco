package com.joctypo.finaleco;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProjectAdapter extends BaseAdapter {

    ArrayList<Project> projectArrayList;

    public ProjectAdapter(){

    projectArrayList = new ArrayList<>();

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
        ImageView imgCategory = convertView.findViewById(R.id.imgCategory);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        proyectDescription.setText(projectArrayList.get(position).getProjectDescription());
        switch(projectArrayList.get(position).getCategory()){

            case "diseño2D":
                imgCategory.setImageResource(R.drawable.ic_diseno_2d);
                break;
            case "modelado3D":
                imgCategory.setImageResource(R.drawable.ic_diseno_3d);

            case "video":

                imgCategory.setImageResource(R.drawable.ic_fotografia);
                break;
        }
        DateFormat df = new SimpleDateFormat("MMM");
        String month = df.format(projectArrayList.get(position).getMonth());
        Log.e("TAG", month );

        tvDate.setText(month+""+ projectArrayList.get(position).getDay());

        return convertView;
    }

    public void AddNewProject(Project project) {

        projectArrayList.add(project);
        notifyDataSetChanged();

    }

    public void Clear(){

        projectArrayList.clear();
    }

}
