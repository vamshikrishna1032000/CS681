package edu.umb.cs681.hw10;

import java.time.LocalDateTime;

public class File extends FSElement {
    public File(String name, int size, LocalDateTime creationTime, Directory parent) {
        super(name, size, creationTime, parent);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isLink() {
        return false;
    }
}
