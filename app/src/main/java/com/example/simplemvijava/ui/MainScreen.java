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
import com.example.simplemvijava.controller.Controller;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainScreen.class.getSimpleName();

    private Controller controller = null;

    private BlogRecyclerAdapter blogListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();

        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        controller = (Controller) getApplicationContext();

        observeBlogs();
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
        showProgressBar(true);

        Log.d(TAG, "clicked on button to load blogs");
        controller.getBlogs();
    }

    private void observeBlogs() {
        controller.getBlogsListLiveData().observe(this, blogs -> {

            blogListRecyclerAdapter.submitList(blogs);
            Log.d(TAG, "blogs: " + blogs);

            showProgressBar(false);
        });
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

}
