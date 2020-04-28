package com.example.simplemvijava.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.simplemvijava.model.Blog;
import com.example.simplemvijava.ui.state.MainStateEvent;
import com.example.simplemvijava.ui.state.MainViewState;
import com.example.simplemvijava.util.AbsentLiveData;
import com.example.simplemvijava.util.DataState;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private static List<Blog> blogList = new ArrayList<>();


    // the goal is
    // to have only 2 live data objects in the view model
    // 1. for the state events
    // 2. for data being displayed

    // handle different events
    private MutableLiveData<MainStateEvent> _stateEvent = new MutableLiveData<>();

    // single mutable state object
    // the view state to be observed
    private MutableLiveData<MainViewState> _viewState = new MutableLiveData<>();


    private MutableLiveData<DataState<MainViewState>> blogsLiveData = new MutableLiveData<>();

    public LiveData<MainViewState> getViewStateLiveData() {
        return _viewState;
    }

    // listening for _stateEvent MutableLiveData object
    // if it changes, detect the change and execute the code
    private LiveData<DataState<MainViewState>> dataState = Transformations.switchMap(_stateEvent, this::handleStateEvent);

    public LiveData<DataState<MainViewState>> getBlogsLiveData() {
        return dataState;
    }


    private LiveData<DataState<MainViewState>> handleStateEvent(MainStateEvent stateEvent) {
        if (stateEvent.getClass().equals(MainStateEvent.GetBlogsEvent.class)) {
            blogsLiveData.setValue(DataState.loading(true));

            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            // simulate api call
                            getBlogsList();

                            // on api call success
                            // postValue because it's on Background Thread
                            blogsLiveData.postValue(DataState.data(null, new MainViewState(blogList)));
                        }
                    },
                    2000
            );

            return blogsLiveData;
        } else {
            return AbsentLiveData.create();
        }
    }

    public void setBlogListData(List<Blog> blogs) {
        MainViewState mainViewStateUpdate = getCurrentViewStateOrNew();
        mainViewStateUpdate.setBlogs(blogs);
        _viewState.setValue(mainViewStateUpdate);
    }

    private MainViewState getCurrentViewStateOrNew() {
        if (_viewState.getValue() != null) {
            return _viewState.getValue();
        }
        else {
            return new MainViewState();
        }
    }

    // set state event - to trigger the switch map
    public void setStateEvent(MainStateEvent event) {
        _stateEvent.setValue(event);
        Log.d(TAG, "set state event " + event);
    }

    private void getBlogsList() {
        Blog b1 = new Blog(1, "awesome title 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        Blog b2 = new Blog(2, "awesome title 2", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        Blog b3 = new Blog(3, "awesome title 3", "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.");

        blogList.add(b1);
        blogList.add(b2);
        blogList.add(b3);
    }


}