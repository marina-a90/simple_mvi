package com.example.simplemvijava.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemvijava.R;
import com.example.simplemvijava.model.Blog;
import com.example.simplemvijava.presenter.MainPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, MainPresenter.View {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter presenter;

    private BlogRecyclerAdapter blogListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPresenter();

        setUI();
    }


    private void setPresenter() {
        presenter = new MainPresenter(this);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        blogListRecyclerAdapter = new BlogRecyclerAdapter();
        recyclerView.setAdapter(blogListRecyclerAdapter);
    }

    private void setUI() {
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);

        initRecyclerView();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "clicked on button to load blogs");
        presenter.getBlogs();
    }


    @Override
    public void showProgressBar(Boolean isVisible) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void updateBlogList(List<Blog> blogs) {
        blogListRecyclerAdapter.submitList(blogs);
    }

}
