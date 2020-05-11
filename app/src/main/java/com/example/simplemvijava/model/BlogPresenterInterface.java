package com.example.simplemvijava.model;

import java.util.List;

public interface BlogPresenterInterface {

        void gettingBlogs();
        void onBlogsReceived(List<Blog> blogs);
}
