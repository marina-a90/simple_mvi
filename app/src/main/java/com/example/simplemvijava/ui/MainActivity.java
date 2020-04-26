package com.example.simplemvijava.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemvijava.R;
import com.example.simplemvijava.model.Blog;
import com.example.simplemvijava.ui.state.MainStateEvent;
import com.example.simplemvijava.ui.state.MainViewState;
import com.example.simplemvijava.util.DataState;
import com.example.simplemvijava.util.Event;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataStateListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Blog> blogList = new ArrayList<>();

    private MainViewModel viewModel;
    private DataStateListener dataStateHandler;
    private BlogRecyclerAdapter blogListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setDataStateHandler();

        setUI();

        subscribeObservers();
    }

    private void setDataStateHandler() {
        try {
            dataStateHandler = this; // context
        }
        catch (ClassCastException e) {
            Log.d(TAG, "setDataStateHandler: " + this + " must implement DataStateListener");
        }
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

    // subscribe to all the observers in the view model
    private void subscribeObservers() {

        // new Observer<DataState<MainViewState>>()
        viewModel.getBlogsLiveData().observe(this, dataState -> {
            if (dataState != null) {
                Log.d(TAG, "DEBUG: onChanged: DataState: " + dataState.toString());

                // Handle Loading and Message
                dataStateHandler.onDataStateChange(dataState);

                // handle Data<T>
                if (dataState.getData() != null) {

                    MainViewState mainViewState = dataState.getData().getContentIfNotHandled();

                    if (mainViewState != null) {
                        Log.d(TAG, "DEBUG: MainViewState: " + mainViewState.toString());

                        if (mainViewState.getBlogs() != null) {
                            blogList = mainViewState.getBlogs();
                            viewModel.setBlogListData(blogList);
                        }
                    }
                }
            }
        });

        // observe(this, new Observer<MainViewState>()
        viewModel.getViewStateLiveData().observe(this, mainViewState -> {

            List<Blog> blogsUI = mainViewState.getBlogs();

            if (blogsUI != null) {
                Log.d(TAG, "DEBUG: Setting blog posts to RecyclerView: " + blogsUI);
                blogListRecyclerAdapter.submitList(blogsUI);
            }
        });
    }

    @Override
    public void onDataStateChange(DataState<?> dataState) {
        handleDataStateChange(dataState);
    }

    private void handleDataStateChange(@Nullable DataState<?> dataState) {
        if (dataState != null) {
            // Handle loading
            showProgressBar(dataState.getLoading());
            Log.d(TAG, "DEBUG: dataState.getLoading() " + dataState.getLoading());

            // Handle Message
            if (dataState.getMessage() != null) {
                showToast(dataState.getMessage());
            }
        }
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

    private void showToast(Event<String> message) {
        Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "clicked on button to load blogs");
        triggerGetBlogsEvent();
    }

    private void triggerGetBlogsEvent() {
        viewModel.setStateEvent(new MainStateEvent.GetBlogsEvent());
    }
}
