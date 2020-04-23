package com.example.simplemvijava.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Event<T> {

    private static final String TAG = Event.class.getSimpleName();

    private T content;

    private Boolean hasBeenHandled = false;

    public Event(T content) {
        this.content = content;
    }

    // Allow external read
    public Boolean getHasBeenHandled() {
        return this.hasBeenHandled;
    }
    // but not write
    private void setHasBeenHandled(Boolean hasBeenHandled) {
        this.hasBeenHandled = hasBeenHandled;
    }

    // Returns the content and prevents its use again.
    @Nullable
    public T getContentIfNotHandled() {
        if (getHasBeenHandled()) {
            return null;
        } else {
            setHasBeenHandled(true);
            return content;
        }
    }

    // Returns the content, even if it's already been handled.
    public T peekContent() {
        return content;
    }

    // don't want an event if there's no data
    @Nullable
    public static <T> Event<T> dataEvent(@Nullable T data) {
        Log.d(TAG, "dataEvent: data " + data);
        if (data != null) {
            return new Event<>(data);
        }
        return null;
    }

    // don't want an event if there is no message
    @Nullable
    public static Event<String> messageEvent(@Nullable String message) {
        Log.d(TAG, "messageEvent: message - " + message);
        if (message != null) {
            return new Event<>(message);
        }
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return "Event (content = " + content + " , hasBeenHandled = " + getHasBeenHandled() + ")";
    }
}