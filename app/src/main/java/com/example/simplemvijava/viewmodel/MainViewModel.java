package com.example.simplemvijava.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.simplemvijava.api.Repository;
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
            return Repository.getInstance().getBlogsList();
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

}