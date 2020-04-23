package com.example.simplemvijava.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataState<T> {

    private Event<String> message = null;
    private Boolean loading = false;
    private Event<T> data = null;

    public DataState(Event<String> message, Boolean loading, Event<T> data) {
        this.message = message;
        this.loading = loading;
        this.data = data;
    }

    public Event<String> getMessage() {
        return message;
    }

    public Boolean getLoading() {
        return loading;
    }

    public Event<T> getData() {
        return data;
    }

    public static <T> DataState<T> error(String message) {
        return new DataState<T>(Event.messageEvent(message), false, null);
    }

    public static <T> DataState<T> loading(Boolean loading) {
        return new DataState<T>(null, loading, null);
    }

    public static <T> DataState<T> data(@Nullable String message, @Nullable T data) {
        return new DataState<T>(Event.messageEvent(message), false, Event.dataEvent(data));
    }

    @NonNull
    @Override
    public String toString() {
        return "DataState (message = " + message + ", loading = " + loading + ", data = " + data + ")";
    }
}
