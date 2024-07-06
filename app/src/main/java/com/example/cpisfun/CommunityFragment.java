package com.example.cpisfun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private TextView welcomeTextView;
    private ListView communityListView;
    private EditText postEditText;
    private Button postButton;
    private DatabaseReference mDatabase;
    private List<Post> postList;
    private PostAdapter postAdapter;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);

        welcomeTextView = rootView.findViewById(R.id.welcomeTextView);
        communityListView = rootView.findViewById(R.id.communityListView);
        postEditText = rootView.findViewById(R.id.postEditText);
        postButton = rootView.findViewById(R.id.postButton);
        postList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            welcomeTextView.setText("Welcome, " + user.getDisplayName());
        }

        postAdapter = new PostAdapter(getActivity(), postList);
        communityListView.setAdapter(postAdapter);

        setWelcomeMessage();
        fetchPosts();

        postButton.setOnClickListener(v -> {
            String text = postEditText.getText().toString().trim();
            if (!text.isEmpty()) {
                post(text);
            } else {
                Toast.makeText(getActivity(), "Please enter a post", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void setWelcomeMessage() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            mDatabase.child("users").child(userId).child("fullName").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String fullName = dataSnapshot.getValue(String.class);
                    if (fullName != null) {
                        welcomeTextView.setText("Welcome, " + fullName);
                    } else {
                        welcomeTextView.setText("Welcome, User");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    welcomeTextView.setText("Welcome, User");
                    Toast.makeText(getActivity(), "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            welcomeTextView.setText("Welcome, Guest");
        }
    }

    private void fetchPosts() {
        mDatabase.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    if (post != null) {
                        // Fetch comments for the post and set user names
                        fetchCommentsForPost(post);
                        postList.add(post);
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to load posts: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCommentsForPost(Post post) {
        mDatabase.child("posts").child(post.getPostId()).child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                            Comment comment = commentSnapshot.getValue(Comment.class);
                            if (comment != null) {
                                // Fetch user name based on userId and set it to comment
                                fetchUserNameForComment(comment, post);
                            }
                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Failed to load comments: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchUserNameForComment(Comment comment, Post post) {
        mDatabase.child("users").child(comment.getUserId()).child("fullName")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String fullName = dataSnapshot.getValue(String.class);
                        if (fullName != null) {
                            comment.setUserName(fullName);
                            post.addComment(comment); // Add comment to post after setting full name
                            postAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Failed to load user name for comment", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void post(String text) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String postId = mDatabase.child("posts").push().getKey();
            String userId = user.getUid();
            String userEmail = user.getEmail();
            String userName = user.getDisplayName(); // Fetch user's display name (full name)

            Post post = new Post(postId, userId, userEmail, text);
            mDatabase.child("posts").child(postId).setValue(post)
                    .addOnSuccessListener(aVoid -> {
                        postEditText.setText("");
                        Toast.makeText(getActivity(), "Post added successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to add post", Toast.LENGTH_SHORT).show());

            // Add the comment with user's full name
            String commentId = mDatabase.child("posts").child(postId).child("comments").push().getKey();
            Comment comment = new Comment(commentId, userId, postId, text);
            comment.setUserName(userName); // Set the user's full name
            mDatabase.child("posts").child(postId).child("comments").child(commentId).setValue(comment);
        }
    }
}
