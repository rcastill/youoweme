package com.mokadevel.youoweme;

import java.util.ArrayList;

public class EventSource<T> {
    private ArrayList<EventListener<T>> eventListeners;

    public EventSource() {
        eventListeners = new ArrayList<>();
    }

    public void register(EventListener<T> eventListener) {
        eventListeners.add(eventListener);
    }

    public void notifyEvent(T t) {
        for (EventListener<T> eventListener : eventListeners)
            eventListener.eventPerformed(t);
    }

}
