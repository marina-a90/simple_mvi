package com.example.simplemvijava.presenter;

import com.example.simplemvijava.model.Blog;
import com.example.simplemvijava.model.BlogPresenterInterface;

import java.util.List;

public class MainPresenter implements BlogPresenterInterface {

    public interface View {
        void showProgressBar(Boolean isVisible);
        void updateBlogList(List<Blog> blogs);
    }

    private View view;

    public MainPresenter(View view) {
        this.view = view;
    }

    @Override
    public void gettingBlogs() {
        view.showProgressBar(true);
    }

    @Override
    public void onBlogsReceived(List<Blog> blogs) {
        view.updateBlogList(blogs);
        view.showProgressBar(false);
    }

}
