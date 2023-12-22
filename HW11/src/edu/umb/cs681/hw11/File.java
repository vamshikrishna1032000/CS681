package edu.umb.cs681.hw11;

import java.time.LocalDateTime;

public class File extends FSElement {

        public File(String name, int size, LocalDateTime creationTime, Directory parent) {
            super(name, size, creationTime, parent);
            parent.appendChild(this);
        }

        @Override
        public boolean isDirectory() {
            return false;
        }

    @Override
    public boolean isLink() {
        return false;
    }

    @Override
        public void accept(FSVisitor fsVisitor) {
        	lock.lock();
        	try {
        		 fsVisitor.visit(this);
        	}finally {
        		lock.unlock();
        	}
           
        }
}
