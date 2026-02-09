package com.example.y.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.y.Post;
import com.example.y.R;
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

        holder.tvUsername.setText(post.getUsername());
        holder.tvContent.setText(post.getContent());
        holder.tvDate.setText(post.getCreatedAt());
        if (showDelete) {
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnDelete.setOnClickListener(v -> {
                new MaterialAlertDialogBuilder(v.getContext())
                        .setTitle("Eliminar post")
                        .setMessage("¿Seguro que quieres eliminar este post?")
                        .setCancelable(true)
                        .setPositiveButton("Eliminar", (dialog, which) -> {

                            int pos = holder.getAbsoluteAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION) {
                                postList.remove(pos);
                                notifyItemRemoved(pos);
                                notifyItemRangeChanged(pos, postList.size());
                            }

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

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvPostUsername);
            tvContent = itemView.findViewById(R.id.tvPostContent);
            tvDate = itemView.findViewById(R.id.tvPostDate);
            btnDelete=itemView.findViewById(R.id.btnDeletePost);
        }
    }
}
