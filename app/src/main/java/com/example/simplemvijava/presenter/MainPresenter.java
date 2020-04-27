package com.example.simplemvijava.presenter;

import com.example.simplemvijava.model.Blog;
import com.example.simplemvijava.model.BlogRepositoryInterface;

import java.util.List;

public class MainPresenter implements BlogRepositoryInterface {

    public interface View {
        void showProgressBar(Boolean isVisible);
        void updateBlogList(List<Blog> blogs);
    }

    private View view;

    private Blog blog;

    public MainPresenter(View view) {
        this.view = view;
        blog = new Blog();
    }

    @Override
    public void getBlogs() {
        view.showProgressBar(true);
        blog.getBlogs(this);
    }

    @Override
    public void onBlogsReceived(List<Blog> blogList) {
        view.updateBlogList(blogList);
        view.showProgressBar(false);
    }
}
