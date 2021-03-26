package concurrency;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadDemo {
    public static void show(){
    }

    public static void ConcurrentCollectionExample(){
        // thread safe and better performance than synchronize method
        // use partitioning technique - allow multiple threads to access different segment of a collection
        Map<Integer, String> map = new ConcurrentHashMap<>();

        map.put(1, "a");
        map.get(1);
        map.remove(1);
    }

    public static void SynchronizedCollectionExample(){

        // SynchronizedCollection use lock internally; maybe impact performance

        Collection<Integer> collection = Collections.synchronizedCollection(new ArrayList<>());

        var thread1 = new Thread(() -> {
            collection.addAll(Arrays.asList(1 ,2 ,3));
        });
        var thread2 = new Thread(() -> {
            collection.addAll(Arrays.asList(4 ,5 ,6));
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(collection);

    }

    public static void WaitAndNotifyExample(){

        var status = new DownloadStatus();

        var thread1 = new Thread(new DownloadFileTask(status));
        var thread2 = new Thread(()-> {
            while(!status.isDone()){
                synchronized (status){
                    try {
                        status.wait(); // this thread will be put to sleep until the status field changes
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(status.getTotalBytes());
        });

        thread1.start();
        thread2.start();
    }

    public static void ConfinementExample(){
        List<Thread> threads = new ArrayList<>();
        List<DownloadFileTask> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            var task = new DownloadFileTask();
            tasks.add(task);
            var thread =  new Thread(task); // create 10 threads
            thread.start();
            threads.add(thread);
        }

        for(var thread : threads){  // this is to wait for all the threads to finish
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        var totalBytes = tasks.stream()
                .map(t -> t.getStatus().getTotalBytes())
                .reduce(0, (a, b) -> a + b);

        System.out.println(totalBytes);
    }

    public static void simulateARaceCondition(){

        // Race Conditions: multiple threads try to modify a data/field

        var status = new DownloadStatus();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            var thread =  new Thread(new DownloadFileTask(status)); // create 10 threads
            thread.start();
            threads.add(thread);
        }

        for(var thread : threads){  // this is to wait for all the threads to finish
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(status.getTotalBytes());
    }

    public static void interruptThreadExample(){
        Thread thread = new Thread(new DownloadFileTask());
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt(); // send an interrupt signal
    }

    public static void JoingThreadExample(){
        Thread thread = new Thread(new DownloadFileTask());
        thread.run();

        try {
            thread.join(); // make the main thread wait for the completion of the other thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("File is ready to be scanned");
    }

    public static void CreatingThreadExample(){
        System.out.println(Thread.currentThread().getName());

        for(var i=0; i< 100; i++){
            Thread thread = new Thread(new DownloadFileTask());
            thread.start();
        }
    }

    public static void example1(){
        System.out.println(Thread.activeCount());
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
