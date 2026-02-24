package com.example.y.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.y.Post;
import com.example.y.R;
import com.example.y.Session;
import com.example.y.data.Api;
import com.example.y.ui.otherprofile.OtherProfileActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private Context context;

    private boolean showDelete;

    public PostAdapter(Context context, List<Post> postList, boolean showDelete) {
        this.context = context;
        this.postList = postList;
        this.showDelete = showDelete;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Post post = postList.get(position);
        holder.setPost(post);
        holder.tvUsername.setText(post.getUsername());
        holder.tvContent.setText(post.getContent());
        holder.tvDate.setText(post.getCreatedAt());
        if (showDelete) {
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Eliminar post")
                        .setMessage("¿Seguro que quieres eliminar este post?")
                        .setCancelable(true)
                        .setPositiveButton("Eliminar", (dialog, which) -> {


                            new Thread(() -> {
                                boolean success = Api.deletePost(post.getId());
                                ((Activity) context).runOnUiThread(() -> {
                                    if (success) {
                                        postList.remove(position);
                                        notifyItemRemoved(position);
                                        Toast.makeText(context, "Post eliminado", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }).start();

                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                        .show();
            });

        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername, tvContent, tvDate;
        ImageView btnDelete;
        Post currentPost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvPostUsername);
            tvContent = itemView.findViewById(R.id.tvPostContent);
            tvDate = itemView.findViewById(R.id.tvPostDate);
            btnDelete = itemView.findViewById(R.id.btnDeletePost);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), OtherProfileActivity.class);
                intent.putExtra("id_user", currentPost.getUserId());
                itemView.getContext().startActivity(intent);
            });
        }

        public void setPost(Post post) {
            this.currentPost = post;
        }
    }
}
