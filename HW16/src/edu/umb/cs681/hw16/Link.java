package edu.umb.cs681.hw16;

import java.time.LocalDateTime;

public class Link extends FSElement {
    private FSElement target;

    public Link(String name, int size, LocalDateTime creationTime, Directory parent, FSElement target) {
        super(name, size, creationTime, parent);
        this.target = target;
        parent.appendChild(this);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public void accept(FSVisitor fsVisitor) {
    		fsVisitor.visit(this);
        
    }

    @Override
    public boolean isLink() {
        return true;
    }

    public FSElement getTarget() {
    		return target;
        
    }
}