package com.example.simplemvijava.ui.state;

import com.example.simplemvijava.model.Blog;

import java.util.List;

// packaging objects into a single class in the view
// single mutable live data object in a class that wraps everything together
public class MainViewState {

    // data model that seen in the view
    private List<Blog> blogs = null;

    public MainViewState(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public MainViewState() {}

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
