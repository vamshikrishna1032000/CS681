package edu.umb.cs681.hw11;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Directory extends FSElement {
    private LinkedList<FSElement> children = new LinkedList<>();

    public Directory(String name, int size, LocalDateTime creationTime, Directory parent) {
        super(name, size, creationTime, parent);
		if (parent != null)
			parent.appendChild(this);

    }

    @Override
    public boolean isDirectory() {
    	
        return true;
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
        	for (FSElement child : children) {
            	child.accept(fsVisitor);
        	}
    	}finally {
    		lock.unlock();
    	}
    }

    public void appendChild(FSElement child) {
    	lock.lock();
    	try {
    		children.add(child);
    	} finally {
    		lock.unlock();
    	}
    	
    }

    public LinkedList<FSElement> getChildren() {
    	lock.lock();
    	try {
    		 children.sort(Comparator.comparing(FSElement::getName));
    	        return children;
    	} finally {
    		lock.unlock();
    	}
       
    }

    public LinkedList<FSElement> getChildren(Comparator<FSElement> comparator) {
    	lock.lock();
    	try {
    		LinkedList<FSElement> child = getChildren();
            child.sort(comparator);
            return child;
    	} finally {
    		lock.unlock();
    	}
        
    }



    public int countChildren() {
    	lock.lock();
    	try {
    		return children.size();
    	} finally {
    		lock.unlock();
    	}
        
    }

    public void setChildren(LinkedList<FSElement> children) {
    	lock.lock();
    	try {
    		this.children = children;
    	} finally {
    		lock.unlock();
    	}
        
    }

    public LinkedList<Directory> getSubDirectories() {
    	lock.lock();
    	try {
    		LinkedList<Directory> SubDir = new LinkedList<Directory>();
            for (FSElement children : getChildren()) {
                if (children.isDirectory()) {
                    SubDir.add((Directory) children);
                }
            }
            children.sort(Comparator.comparing(FSElement::getName));
            return SubDir;
    	} finally {
    		lock.unlock();
    	}
        
    }

    public LinkedList<File> getFiles(Comparator<File> comparator) {
    	lock.lock();
    	try {
    		 LinkedList<File> files = getFiles();
    	        files.sort(comparator);
    	        return files;
    	} finally {
    		lock.unlock();
    	}
       
    }

    public LinkedList<File> getFiles() {
    	lock.lock();
    	try {
    		LinkedList<File> file = new LinkedList<>();
            for (FSElement children : getChildren()) {
                if (children instanceof File) {
                    file.add((File) children);
                }
            }
            children.sort(Comparator.comparing(FSElement::getName));
            return file;
    	} finally {
    		lock.unlock();
    	}
        
    }


    public int getTotalSize() {
    	lock.lock();
    	try {
    		int Size = 0;
            for (FSElement f : getChildren()) {
                if (f.isDirectory()) {
                    Size = Size + ((Directory) f).getTotalSize();
                } else {
                    Size = Size + f.getSize();
                }
            }
            return Size;
    	} finally {
    		lock.unlock();
    	}
        
    }


    public LinkedList<Directory> getSubDirectories(Comparator<FSElement> comparator) {
    	lock.lock();
    	try {
    		 LinkedList<Directory> Directories = getSubDirectories();
    	        Directories.sort(comparator);
    	        return Directories;
    	} finally {
    		lock.unlock();
    	}
       
    }
}




