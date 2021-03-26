package concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadStatus{
    private volatile boolean isDone; // Volatile keyword ensure any changes on this field will be visible across threads

    // private int totalBytes;
    // private AtomicInteger totalBytes = new AtomicInteger(); // Atomic types use compare and swap technique; great for implementing counter
    private LongAdder totalBytes = new LongAdder(); // good if the field is updated frequently
    private int totalFiles;

    private Object totalBytesLock = new Object();
    private Object totalFilesLock = new Object();

    // private Lock lock = new ReentrantLock();

    public void incrementTotalBytes(){
            //totalBytes.incrementAndGet(); // looks like this: a++
        totalBytes.increment();

/*
        synchronized (totalBytesLock){
            totalBytes++;
        }
*/

        // Example on using lock
/*        lock.lock();

        try{ // in case the following throw an exception
            totalBytes++;
        }
        finally { // always release the lock
            lock.unlock();
        }*/
    }

    public void incrementTotalFiles(){
        synchronized (totalFilesLock){
            totalFiles++;
        }
    }

    public int getTotalBytes() {
        return totalBytes.intValue();
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public boolean isDone() {
        return isDone;
    }

    public void done() {
        isDone = true;
    }
}
