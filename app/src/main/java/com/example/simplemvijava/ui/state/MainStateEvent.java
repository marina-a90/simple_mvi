package com.example.simplemvijava.ui.state;

// value can have one of the types from a limited set, but cannot have any other type.
public class MainStateEvent {

    // data models and none
    // different things that can be returned from the wrapper - all return MainStateEvent

    public static class GetBlogsEvent extends MainStateEvent {}

    public static class None extends MainStateEvent {}

}
