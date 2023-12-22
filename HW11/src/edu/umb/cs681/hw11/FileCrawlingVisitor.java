package edu.umb.cs681.hw11;



import edu.umb.cs681.hw11.Directory;
import edu.umb.cs681.hw11.FSVisitor;
import edu.umb.cs681.hw11.File;
import edu.umb.cs681.hw11.Link;

import java.util.LinkedList;
import java.util.List;

public class FileCrawlingVisitor implements FSVisitor {

    private static volatile Boolean Flag = false;

    public void changeFlag() {
        Flag = true;
    }


    public Boolean getFlag(){return this.Flag;}
    private LinkedList<File> files = new LinkedList<>();

    @Override
    public void visit(Link link) {
        return;
    }

    @Override
    public void visit(Directory dir) {
        return;
    }

    @Override
    public void visit(File file) {
        files.add(file);
    }

    public LinkedList<File> getFilesVisited() {
        return files;
    }


}
