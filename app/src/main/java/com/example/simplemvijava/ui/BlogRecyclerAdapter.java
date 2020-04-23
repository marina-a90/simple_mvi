package com.example.simplemvijava.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemvijava.R;
import com.example.simplemvijava.model.Blog;

import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public BlogRecyclerAdapter() {
    }

    private DiffUtil.ItemCallback<Blog> DIFF_CALLBACK = new DiffUtil.ItemCallback<Blog>() {

        @Override
        public boolean areItemsTheSame(@NonNull Blog oldItem, @NonNull Blog newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Blog oldItem, @NonNull Blog newItem) {
            return oldItem.equals(newItem);
        }
    };

    private AsyncListDiffer<Blog> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BlogViewHolder) holder).bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<Blog> list) {
        differ.submitList(list);
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;

        BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }

        void bind(Blog model) {
            title.setText(model.getTitle());
            description.setText(model.getDescription());
        }
    }
}