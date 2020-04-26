package com.example.simplemvijava.controller;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.simplemvijava.model.Blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller extends Application {

    // getters and setters here

    private MutableLiveData<List<Blog>> blogsLiveData = new MutableLiveData<>();

    public void getBlogs() {
        List<Blog> blogList = new ArrayList<>();

        Blog b1 = new Blog(1, "awesome title 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        Blog b2 = new Blog(2, "awesome title 2", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        Blog b3 = new Blog(3, "awesome title 3", "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.");

        blogList.add(b1);
        blogList.add(b2);
        blogList.add(b3);

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {

                        blogsLiveData.postValue(blogList);

                    }
                },
                2000
        );
    }

    public LiveData<List<Blog>> getBlogsListLiveData() {
        return blogsLiveData;
    }

}
