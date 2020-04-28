package com.example.simplemvijava.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.simplemvijava.model.Blog;
import com.example.simplemvijava.ui.state.MainViewState;
import com.example.simplemvijava.util.DataState;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Repository {

    private static List<Blog> blogList = new ArrayList<>();
    private MutableLiveData<DataState<MainViewState>> blogsLiveData = new MutableLiveData<>();

    private static Repository instance;
    private Repository() {}

    public static Repository getInstance () {
        if (Repository.instance == null) {
            Repository.instance = new Repository ();
        }
        return Repository.instance;
    }

    private void setBlogsList() {
        Blog b1 = new Blog(1, "awesome title 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        Blog b2 = new Blog(2, "awesome title 2", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        Blog b3 = new Blog(3, "awesome title 3", "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.");

        blogList.add(b1);
        blogList.add(b2);
        blogList.add(b3);
    }

    public LiveData<DataState<MainViewState>> getBlogsList() {
        blogsLiveData.postValue(DataState.loading(true));

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        setBlogsList();
                        getBlogsList();

                        // on api call success
                        // postValue because it's on Background Thread
                        blogsLiveData.postValue(DataState.data(null, new MainViewState(blogList)));
                    }
                },
                2000
        );

        return blogsLiveData;
    }

}
