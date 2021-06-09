package com.joctypo.finaleco.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joctypo.finaleco.R;
import com.joctypo.finaleco.adapters.CommentDesignerAdapter;
import com.joctypo.finaleco.model.Comment;
import com.joctypo.finaleco.model.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;


public class ProjectDialogFragment extends DialogFragment {

    private EditText etComment;
    private TextView tvProjectDescription, tvDate,tvNumberComents;
    ImageView imgComment;
    CommentDesignerAdapter commentDesignerAdapter;
    Button btnComment;
    FirebaseDatabase db;
    FirebaseAuth auth;
    Project project;
    Context context;
    FirebaseUser user;
    ListView listviewComments;

    public ProjectDialogFragment() {
        // Required empty public constructor
    }


    public static ProjectDialogFragment newInstance() {
        ProjectDialogFragment fragment = new ProjectDialogFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_project_dialog, container, false);

        Bundle args = getArguments();
        commentDesignerAdapter = new CommentDesignerAdapter();
        String id = args.getString("id");
        db = FirebaseDatabase.getInstance();
        listviewComments = root.findViewById(R.id.listviewComments);
        auth = FirebaseAuth.getInstance();
        etComment = root.findViewById(R.id.etComment);
        tvProjectDescription = root.findViewById(R.id.tvProjectDescription);
        btnComment = root.findViewById(R.id.btnComment);
        tvDate = root.findViewById(R.id.tvDate);
        listviewComments.setAdapter(commentDesignerAdapter);
        tvNumberComents = root.findViewById(R.id.tvNumberComents);
        user = FirebaseAuth.getInstance().getCurrentUser();
        imgComment=root.findViewById(R.id.imgComment);


        if(user != null){

            //baja la informacion del proyecto
            db.getReference().child("projects").child(id).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            project = snapshot.getValue(Project.class);
                            tvProjectDescription.setText(project.getProjectDescription());
                            DateFormat df = new SimpleDateFormat("MMM");
                            String month = df.format(project.getMonth());
                            tvDate.setText(month+""+ project.getDay());


                            LoadComments();
                        }

                        @Override
                        public void onCancelled( DatabaseError error) {

                        }
                    }
            );


        }

        btnComment.setOnClickListener(v -> {

        UploadComment();
        });
        return root;

    }

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

    private void LoadComments(){

        db.getReference().child("comments").orderByChild("projectId").equalTo(project.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.getChildrenCount()>0){

                    imgComment.setImageResource(R.drawable.ic_comment_number);
                    tvNumberComents.setText(String.valueOf(snapshot.getChildrenCount()));

                }
                commentDesignerAdapter.Clear();
                for (DataSnapshot child:
                      snapshot.getChildren()) {

                    Comment comment = child.getValue(Comment.class);
                    commentDesignerAdapter.AddComment(comment);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    private void UploadComment() {

        if (etComment.getText().toString().isEmpty()) {

            Toast.makeText(context, "No puedes publicar un comentario vacio", Toast.LENGTH_SHORT).show();
        } else {

            Comment comment = new Comment(UUID.randomUUID().toString(),etComment.getText().toString(), project.getId(), user.getUid());

            db.getReference().child("comments").child(comment.getId()).setValue(comment).addOnCompleteListener(task->{

                if(task.isSuccessful()){

                    dismiss();
                }
            });
        }

    }


}