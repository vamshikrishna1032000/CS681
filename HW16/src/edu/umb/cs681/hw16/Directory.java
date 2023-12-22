package edu.umb.cs681.hw16;

import java.time.LocalDateTime;
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
			fsVisitor.visit(this);
        	for (FSElement child : children) {
            	child.accept(fsVisitor);
        	}
    }

    public void appendChild(FSElement child) {
    		children.add(child);
    }

    public LinkedList<FSElement> getChildren() {
    		 children.sort(Comparator.comparing(FSElement::getName));
    	        return children;
       
    }

    public LinkedList<FSElement> getChildren(Comparator<FSElement> comparator) {
    		LinkedList<FSElement> child = getChildren();
            child.sort(comparator);
            return child;

        
    }



    public int countChildren() {
    		return children.size();
    }

    public void setChildren(LinkedList<FSElement> children) {
    		this.children = children;
        
    }

    public LinkedList<Directory> getSubDirectories() {

    		LinkedList<Directory> SubDir = new LinkedList<Directory>();
            for (FSElement children : getChildren()) {
                if (children.isDirectory()) {
                    SubDir.add((Directory) children);
                }
            }
            children.sort(Comparator.comparing(FSElement::getName));
            return SubDir;
        
    }

    public LinkedList<File> getFiles(Comparator<File> comparator) {

    		 LinkedList<File> files = getFiles();
    	        files.sort(comparator);
    	        return files;

    }

    public LinkedList<File> getFiles() {
    		LinkedList<File> file = new LinkedList<>();
            for (FSElement children : getChildren()) {
                if (children instanceof File) {
                    file.add((File) children);
                }
            }
            children.sort(Comparator.comparing(FSElement::getName));
            return file;
    }


    public int getTotalSize() {
    		int Size = 0;
            for (FSElement f : getChildren()) {
                if (f.isDirectory()) {
                    Size = Size + ((Directory) f).getTotalSize();
                } else {
                    Size = Size + f.getSize();
                }
            }
            return Size;
    }


    public LinkedList<Directory> getSubDirectories(Comparator<FSElement> comparator) {
    		 LinkedList<Directory> Directories = getSubDirectories();
    	        Directories.sort(comparator);
    	        return Directories;
    	}
}




