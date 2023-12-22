package edu.umb.cs681.hw14;

import edu.umb.cs681.hw14.Observable;

public interface Observer<T> {
    public void update(Observable<T> sender, T event);
}
