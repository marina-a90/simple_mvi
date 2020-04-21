package com.example.simplemvijava.ui.state;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemvijava.R;
import com.example.simplemvijava.model.Blog;

import java.util.ArrayList;
import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Blog> modelList;

    public BlogRecyclerAdapter(List<Blog> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BlogViewHolder) holder).bind(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void setBlogs(List<Blog> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
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