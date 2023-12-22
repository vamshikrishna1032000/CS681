package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {
    public static ReentrantLock lock = new ReentrantLock();
    private LinkedList<Directory> rootdir = new LinkedList<>();
    private static AtomicReference<FileSystem> fs = null;
    
    private static AtomicBoolean setDone= new AtomicBoolean(false);
    private FileSystem() {
    }


    public static FileSystem getInstance() {
        lock.lock();
        try {
            if (fs == null) {
                fs = new AtomicReference<>(new FileSystem());
            }
            return fs.get();
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<Directory> getRootDirs() {
        return rootdir;
    }

    public void appendRootDi(Directory root) {
        rootdir.add(root);
    }

    public static void main(String[] args) {

    	List<Thread> threads=new ArrayList<>();
    	for (int i=0;i<13;i++) {
    		Thread t=new Thread(()->{
    			while(!setDone.get()) {
    				Directory root = new Directory("root", 1, LocalDateTime.now(), null);
    				FileSystem fs = FileSystem.getInstance();
    	    		fs.appendRootDi(root);
    	    		
    	    		System.out.println("Thread"+Thread.currentThread().getId()+" File system :  "+ fs.getRootDirs().get(0).getName() );
    			}
    		});
    		threads.add(t);
    		t.start();
    		
    	}
    	try {
    		Thread.sleep(1000);
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    	setDone.set(true);
    	
    	for(Thread thread:threads) {
    		thread.interrupt();
    	}
    	
    	for(Thread thread:threads) {
    		try {
    		thread.join();
    		}catch(Exception e) {
    			System.out.println(e);
    		}
    	}

    }
}