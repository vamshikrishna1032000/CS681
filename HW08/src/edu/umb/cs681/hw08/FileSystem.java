package edu.umb.cs681.hw08;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {
    private static ReentrantLock lock = new ReentrantLock();
    private List<Directory> rootdir = new LinkedList<>();
    private static FileSystem fs = null;

    private FileSystem() {
    }

    public static FileSystem getInstance() {
        lock.lock();
        try {
            if (fs == null) {
                fs = new FileSystem();
            }
            return fs;
        } finally {
            lock.unlock();
        }
    }

    public List<Directory> getRootDirs() {
        return rootdir;
    }

    public void appendRootDi(Directory root) {
        rootdir.add(root);
    }

    public static void main(String[] args) {
    	Thread t1=new Thread(()-> {
    		FileSystem fs = FileSystem.getInstance();
    		Directory root = new Directory("root-1 ", 1, LocalDateTime.now(), null);
    		fs.appendRootDi(root);
    		System.out.println("Thread-1 : 1st File system :  "+fs.getRootDirs().get(0).getName() );
    	});
    	Thread t2=new Thread(()-> {
    		FileSystem fs = FileSystem.getInstance();
    		Directory root = new Directory("root-2 ", 1, LocalDateTime.now(), null);
    		fs.appendRootDi(root);
    		System.out.println("Thread-2 : 2nd File system :  "+fs.getRootDirs().get(0).getName() );
    	});
    	Thread t3=new Thread(()-> {
    		FileSystem fs = FileSystem.getInstance();
    		Directory root = new Directory("root-3 ", 1, LocalDateTime.now(), null);
    		fs.appendRootDi(root);
    		System.out.println("Thread-3 : 3rd File system :  "+fs.getRootDirs().get(0).getName() );
    	});
    	
    	t1.start();
    	t2.start();
    	t3.start();    
    	try {
    		t1.join();
    		t2.join();
    		t3.join();
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    	}


    }
}