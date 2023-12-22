package edu.umb.cs681.hw16;



import java.util.LinkedList;

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
