package com.example.simplemvijava.ui;

import com.example.simplemvijava.util.DataState;

public interface DataStateListener {

    void onDataStateChange(DataState<?> dataState);
}
