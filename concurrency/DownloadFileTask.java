package concurrency;

public class DownloadFileTask implements Runnable{

    private DownloadStatus status;

    public DownloadFileTask() {
        this.status = new DownloadStatus();
    }

    public DownloadFileTask(DownloadStatus status) {
        this.status = status;
    }

    @Override
    public void run() {
        System.out.println("Downloading a file" + Thread.currentThread().getName());
/*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        for (int i = 0; i < 1_000_000; i++) {
            if(Thread.currentThread().isInterrupted()) return; //check for any interrupt signal
            status.incrementTotalBytes();
        }

        status.done();

        synchronized (status){
            status.notifyAll();
        }

        System.out.println("Download completed:" + Thread.currentThread().getName());
    }

    public DownloadStatus getStatus() {
        return status;
    }


}
