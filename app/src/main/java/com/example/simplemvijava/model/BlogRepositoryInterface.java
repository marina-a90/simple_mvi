package com.example.simplemvijava.model;

import java.util.List;

public interface BlogRepositoryInterface {

        void getBlogs();
        void onBlogsReceived(List<Blog> blogList);

}
