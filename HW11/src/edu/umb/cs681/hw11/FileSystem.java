package edu.umb.cs681.hw11;

import java.util.LinkedList;
import java.util.List;

public class FileSystem {
    private List<Directory> root = new LinkedList<>();
    private static FileSystem instance = null;

    private FileSystem() {

    }

    public static FileSystem getFileSystem() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    public void appendRootDir(Directory root) {
        this.root.add(root);
    }


    public List<Directory> getRoot() {
        return this.root;
    }
}
