package com.joctypo.finaleco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    ArrayList<Comment> commentArraylist;
    FirebaseDatabase db;

    public CommentAdapter(){

        commentArraylist = new ArrayList<>();
        db=FirebaseDatabase.getInstance();
    }
    @Override
    public int getCount() {
        return commentArraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return commentArraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.comment,null);
        TextView tvNameComment,tvComment;

        tvNameComment =  convertView.findViewById(R.id.tvNameComment);
        tvComment=convertView.findViewById(R.id.tvComment);
        tvComment.setText(commentArraylist.get(position).getComment());

        //carga informacion del usuario que comentó

        db.getReference().child("users").child(commentArraylist.get(position).getDesignerId()).addValueEventListener(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        User user = snapshot.getValue(User.class);
                        tvNameComment.setText(user.getName());

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );

        return convertView;
    }

    public void AddComment(Comment comment){
        commentArraylist.add(comment);
        notifyDataSetChanged();
    }

    public void Clear(){

        commentArraylist.clear();
    }
}
