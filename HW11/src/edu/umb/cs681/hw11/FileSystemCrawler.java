package edu.umb.cs681.hw11;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileSystemCrawler {
    private static volatile Boolean flag = false;

    public static void main(String[] args) {

    	List<File> SharedList=new ArrayList<>();


    	
        //FileSystem-1
        // Create root directory
        Directory root1 = new Directory("root", 1, LocalDateTime.now(), null);  
        // Create sub-directories
        Directory home = new Directory("home", 1, LocalDateTime.now(), root1);
        // Create files
        File a = new File("a", 10, LocalDateTime.now(), home);
        File b = new File("b", 10, LocalDateTime.now(), home);
        // Create links
        Link x = new Link("x", 10, LocalDateTime.now(),home,a);
        Link y = new Link("y", 1, LocalDateTime.now(), home,b);
        
        //FileSystem-2
        // Create root directory
        Directory root2 = new Directory("root", 1, LocalDateTime.now(), null);  
        // Create sub-directories
        Directory system = new Directory("system", 1, LocalDateTime.now(), root2);
        // Create files
        File c = new File("c", 10, LocalDateTime.now(), system);
        File d = new File("d", 10, LocalDateTime.now(), system);
        // Create links
        Link w = new Link("w", 10, LocalDateTime.now(),system,c);
        Link z = new Link("z", 1, LocalDateTime.now(),system,d);
        
        
        //FileSystem-3
        // Create root directory
        Directory root3 = new Directory("root", 1, LocalDateTime.now(), null);  
        // Create sub-directories
        Directory pictures = new Directory("pictures", 1, LocalDateTime.now(), root3);
        // Create files
        File e = new File("e", 10, LocalDateTime.now(), pictures);
        File f = new File("f", 10, LocalDateTime.now(), pictures);
        // Create links
        Link u = new Link("u", 10, LocalDateTime.now(), pictures, e);
        Link v = new Link("v", 1, LocalDateTime.now(), pictures, f);
        ThreadLocal<FileCrawlingVisitor> fs1 = ThreadLocal.withInitial(() -> new FileCrawlingVisitor());
        ThreadLocal<FileCrawlingVisitor> fs2 = ThreadLocal.withInitial(() -> new FileCrawlingVisitor());
        ThreadLocal<FileCrawlingVisitor> fs3 = ThreadLocal.withInitial(() -> new FileCrawlingVisitor());




        Thread t1 =new Thread(()->{
                root1.accept(fs1.get());
                SharedList.addAll(fs1.get().getFilesVisited());
            try {
                while(!flag) {
                    Thread.sleep(300);
                }
            } catch(InterruptedException ex) {
                System.out.println(ex);
            }


        });
        Thread t2 =new Thread(()->{
                root2.accept(fs2.get());
                SharedList.addAll(fs2.get().getFilesVisited());
            try {
                while(!flag) {
                    Thread.sleep(300);
                }
            } catch(InterruptedException ex) {
                System.out.println(ex);
            }


        });
        Thread t3 =new Thread(()->{
                root3.accept(fs3.get());
                SharedList.addAll(fs3.get().getFilesVisited());
            try {
                while(!flag) {
                    Thread.sleep(300);
                }
            } catch(InterruptedException ex) {
                System.out.println(ex);
            }



        });
        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        flag=true;


        try {
            t1.interrupt();
            t2.interrupt();
            t3.interrupt();
        } catch (Exception exx) {
            throw new RuntimeException(exx);
        }




        System.out.println("Visited files by three threads stored in Shared list");
        for (File file : SharedList) {
            System.out.println(file.getName());
        }
    }
}