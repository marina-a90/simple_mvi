package com.example.simplemvijava.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemvijava.R;
import com.example.simplemvijava.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel viewModel;
    private BlogRecyclerAdapter blogListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setUI();

        observeBlogs();
    }

    private void observeBlogs() {
        viewModel.getBlogsListLiveData().observe(this, blogs -> {

            blogListRecyclerAdapter.submitList(blogs);
            Log.d(TAG, "blogs: " + blogs);

            showProgressBar(false);
        });
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

    private void showProgressBar(Boolean isVisible) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        showProgressBar(true);

        Log.d(TAG, "clicked on button to load blogs");
        viewModel.getBlogsList();
    }
}
