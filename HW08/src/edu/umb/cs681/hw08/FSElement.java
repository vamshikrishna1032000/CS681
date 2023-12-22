package edu.umb.cs681.hw08;

import java.time.LocalDateTime;

public abstract class FSElement {
    protected String name;
    protected int size;
    protected LocalDateTime creationTime;

    private Directory parent;
    public  FSElement(String name, int size, LocalDateTime creationTime,Directory parent){
        this.name=name;
        this.size=size;
        this.creationTime=creationTime;
        this.parent = parent;
    }

    public Directory getParent() {
        return parent;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public abstract boolean isDirectory();

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public boolean isLink() {
        return this instanceof Link;
    }
}
