package com.example.simplemvijava.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Blog {

    private int id;
    private String title;
    private String description;

    public Blog(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return "Blog (title = " + title + ", description = " + description + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return id == blog.id &&
                title.equals(blog.title) &&
                description.equals(blog.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
