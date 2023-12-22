package edu.umb.cs681.hw10;

import java.time.LocalDateTime;

public class Link extends FSElement {
    private FSElement target;

    public Link(String name, int size, LocalDateTime creationTime, Directory parent, FSElement target) {
        super(name, size, creationTime, parent);
        this.target = target;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isLink() {
        return true;
    }

    public FSElement getTarget() {
    	lock.lock();
    	try {
    		return target;
    	} finally {
    		lock.unlock();
    	}
        
    }
}