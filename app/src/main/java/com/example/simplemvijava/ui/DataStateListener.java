package com.example.simplemvijava.ui;

import androidx.annotation.Nullable;

import com.example.simplemvijava.util.DataState;

public interface DataStateListener {

    void onDataStateChange(@Nullable DataState<?> dataState);
}
