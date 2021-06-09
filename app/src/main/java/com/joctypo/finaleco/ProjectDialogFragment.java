package com.joctypo.finaleco;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectDialogFragment extends DialogFragment {

    private EditText etComment;
    private TextView tvProjectDescription, tvDate,tvNumberComents;
    ImageView imgComment;
    CommentAdapter commentAdapter;
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
        commentAdapter = new CommentAdapter();
        String id = args.getString("id");
        db = FirebaseDatabase.getInstance();
        listviewComments = root.findViewById(R.id.listviewComments);
        auth = FirebaseAuth.getInstance();
        etComment = root.findViewById(R.id.etComment);
        tvProjectDescription = root.findViewById(R.id.tvProjectDescription);
        btnComment = root.findViewById(R.id.btnComment);
        tvDate = root.findViewById(R.id.tvDate);
        listviewComments.setAdapter(commentAdapter);
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
                commentAdapter.Clear();
                for (DataSnapshot child:
                      snapshot.getChildren()) {

                    Comment comment = child.getValue(Comment.class);
                    commentAdapter.AddComment(comment);

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