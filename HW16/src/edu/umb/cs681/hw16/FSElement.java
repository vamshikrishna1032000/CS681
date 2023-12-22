package edu.umb.cs681.hw16;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public abstract class FSElement {
    private String name;
    public static ReentrantLock lock = new ReentrantLock();
    private int size;
    private LocalDateTime creationTime;
    private Directory parent;

    public FSElement(String name, int size, LocalDateTime creationTime, Directory parent) {
        this.name = name;
        this.size = size;
        this.creationTime = creationTime;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Directory getParent() {
        return parent;
    }

    public abstract boolean isDirectory();

    public abstract boolean isLink();

    public abstract  void accept(FSVisitor fsVisitor);
}
