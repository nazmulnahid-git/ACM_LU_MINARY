package com.example.cpisfun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostAdapter extends ArrayAdapter<Post> {

    private List<Post> posts;
    private DatabaseReference mDatabase;

    public PostAdapter(@NonNull Context context, @NonNull List<Post> objects) {
        super(context, 0, objects);
        this.posts = objects;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }

        final Post currentPost = posts.get(position);

        TextView userEmailTextView = listItem.findViewById(R.id.userEmailTextView);
        TextView postTextView = listItem.findViewById(R.id.postTextView);
        TextView likesTextView = listItem.findViewById(R.id.likesTextView);
        ImageButton likeButton = listItem.findViewById(R.id.likeButton);
        ImageButton deleteButton = listItem.findViewById(R.id.deleteButton);
        TextView commentsTextView = listItem.findViewById(R.id.commentsTextView);
        EditText commentEditText = listItem.findViewById(R.id.commentEditText);
        Button addCommentButton = listItem.findViewById(R.id.addCommentButton);

        userEmailTextView.setText(currentPost.getUserEmail());
        postTextView.setText(currentPost.getText());
        likesTextView.setText("Likes: " + currentPost.getLikes());

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            boolean hasLiked = currentPost.getLikedUsers() != null && currentPost.getLikedUsers().containsKey(currentUser.getUid());
            likeButton.setImageResource(hasLiked ? R.drawable.ic_liked : R.drawable.ic_like);

            // Show delete button only for the post owner
            deleteButton.setVisibility(currentPost.getUserId().equals(currentUser.getUid()) ? View.VISIBLE : View.GONE);
        }

        likeButton.setOnClickListener(v -> likePost(currentPost));
        deleteButton.setOnClickListener(v -> deletePost(currentPost));
        addCommentButton.setOnClickListener(v -> {
            String commentText = commentEditText.getText().toString().trim();
            if (!commentText.isEmpty()) {
                addComment(currentPost, commentText);
                commentEditText.setText("");
            }
        });

        // Display comments
        StringBuilder commentsBuilder = new StringBuilder("Comments:\n");
        if (currentPost.getComments() != null) {
            for (Comment comment : currentPost.getComments().values()) {
                commentsBuilder.append(comment.getUserId()).append(": ").append(comment.getText()).append("\n");
            }
        }
        commentsTextView.setText(commentsBuilder.toString());

        return listItem;
    }

    private void likePost(final Post post) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;

        String userId = currentUser.getUid();
        DatabaseReference postRef = mDatabase.child("posts").child(post.getPostId());

        if (post.getLikedUsers() == null) {
            post.setLikedUsers(new HashMap<>());
        }

        if (post.getLikedUsers().containsKey(userId)) {
            // Unlike the post
            post.getLikedUsers().remove(userId);
            post.setLikes(Math.max(0, post.getLikes() - 1)); // Ensure likes don't go below 0
        } else {
            // Like the post
            post.getLikedUsers().put(userId, true);
            post.setLikes(post.getLikes() + 1);
        }

        // Update only the changed fields
        Map<String, Object> updates = new HashMap<>();
        updates.put("likes", post.getLikes());
        updates.put("likedUsers", post.getLikedUsers());
        postRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    // Update successful
                    notifyDataSetChanged(); // Refresh the list view
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(getContext(), "Failed to update like", Toast.LENGTH_SHORT).show();
                });
    }

    private void deletePost(Post post) {
        mDatabase.child("posts").child(post.getPostId()).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Post deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to delete post", Toast.LENGTH_SHORT).show());
    }

    private void addComment(Post post, String commentText) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;

        String commentId = mDatabase.child("comments").push().getKey();
        Comment comment = new Comment(commentId, currentUser.getUid(), post.getPostId(), commentText);

        post.addComment(comment);
        mDatabase.child("posts").child(post.getPostId()).setValue(post)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Comment added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to add comment", Toast.LENGTH_SHORT).show());
    }
}