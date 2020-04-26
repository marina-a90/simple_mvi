package com.example.simplemvijava.presenter;

import com.example.simplemvijava.model.Blog;

import java.util.List;

public class MainPresenter {

    public interface View {
        void showProgressBar(Boolean isVisible);
        void updateBlogList(List<Blog> blogs);
    }

    private View view;

    public MainPresenter(View view) {
        this.view = view;
    }

    public void handleBlogs(List<Blog> blogs) {
        // optional action
        view.updateBlogList(blogs);
    }
}
