package com.example.simplemvijava.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemvijava.R;
import com.example.simplemvijava.model.Blog;
import com.example.simplemvijava.presenter.MainPresenter;

import java.util.ArrayList;
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

        observeBlogs();
    }

    public void observeBlogs() {
        getBlogsListLiveData().observe(this, blogs -> {
            presenter.handleBlogs(blogs);
            Log.d(TAG, "blogs: " + blogs);

            showProgressBar(false);
        });
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
        showProgressBar(true);

        Log.d(TAG, "clicked on button to load blogs");
        getBlogs();
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

    // simulate fake api call
    // <start>
    private MutableLiveData<List<Blog>> blogsLiveData = new MutableLiveData<>();

    private void getBlogs() {
        List<Blog> blogList = new ArrayList<>();

        Blog b1 = new Blog(1, "awesome title 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        Blog b2 = new Blog(2, "awesome title 2", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        Blog b3 = new Blog(3, "awesome title 3", "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.");

        blogList.add(b1);
        blogList.add(b2);
        blogList.add(b3);

        final Handler handler = new Handler();
        handler.postDelayed(() -> setBlogsLiveData(blogList), 2000);
    }

    public void setBlogsLiveData(List<Blog> blogs) {
        blogsLiveData.setValue(blogs);
    }

    public LiveData<List<Blog>> getBlogsListLiveData() {
        return blogsLiveData;
    }
    // <end>
}
