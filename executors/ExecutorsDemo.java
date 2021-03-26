package executors;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ExecutorsDemo {

    public static void show(){

        var start = LocalTime.now();
        var service = new FlightService();
        var futures = service.getQuotes()
                .map(future -> future.thenAccept(System.out::println))
                .collect(Collectors.toList());

        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(()->{
                    var end = LocalTime.now();
                    var duration = Duration.between(start, end);
                    System.out.println("Retrieved all quotes in " + duration.toMillis()+"msec");
                });

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void HandlingTimeOut(){
        var future = CompletableFuture.supplyAsync(()-> {
            LongTask.simulate();
            return 1;
        });

        try {
            // var result = future.orTimeout(1, TimeUnit.SECONDS).get(); // This throws exception if timeout
            var result = future
                    .completeOnTimeout(1, 1, TimeUnit.SECONDS) // this will return a default value if timeout
                    .get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static void WaitingForTheFistTask(){
        var first = CompletableFuture.supplyAsync(()->{
            LongTask.simulate();
            return 20;
        });

        var second = CompletableFuture.supplyAsync(()->20);

        CompletableFuture
                .anyOf(first, second)
                .thenAccept(temp -> System.out.println(temp));
    }

    public static void WaitingForManyTasks(){
        var first = CompletableFuture.supplyAsync(()->1);
        var second = CompletableFuture.supplyAsync(()->2);
        var third = CompletableFuture.supplyAsync(()->3);

        var all  = CompletableFuture.allOf(first, second, third);
        all.thenRun(()-> {
            try {
                var firstResult = first.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("All tasks completed successfully");
        });

    }

    public static void CombiningCompletableFutures(){
        var first = CompletableFuture.supplyAsync(()->"20USD")
                .thenApply( str -> {
                    var price = str.replace("USD", "");
                    return Integer.parseInt(price);
                });
        var second = CompletableFuture.supplyAsync(()->0.9);

        first
                .thenCombine(second, (price, exchangeRate)-> price * exchangeRate)
                .thenAccept(result -> System.out.println(result));
    }

    public static CompletableFuture<String> getUserEmailAsync(){
        return CompletableFuture.supplyAsync(()->"email");
    }
    public static CompletableFuture<String> getPlayListAsync(String email){
        return CompletableFuture.supplyAsync(()-> "playList");
    }

    public static void ComposingCompletable(){
        getUserEmailAsync()
                .thenCompose(ExecutorsDemo::getPlayListAsync)
                .thenAccept(playlist -> System.out.println(playlist));
    }

    public static void TransformingACompletableFuture(){
        var future = CompletableFuture.supplyAsync(()-> 20);
        future
                .thenApply(ExecutorsDemo::toFahrenheit)
                .thenAccept(f -> System.out.println(f));
    }

    public static int toFahrenheit(int celsius){
        return (int)(celsius * 1.8) + 32;
    }

    public static void HandlingException(){
        var future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Getting the current weather");
            throw new IllegalStateException();
        });

        try {
            var temperature = future.exceptionally(ex -> 1 ).get(); // return a default value if exception happens
            System.out.println(temperature);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static void runningCodeOnCompletion(){
        var future = CompletableFuture.supplyAsync(()-> 1);

/*        future.thenRunAsync(()-> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("done");
        });*/

        future.thenAcceptAsync(result -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(result);
        });

        // Note: future.thenRun will run in the main thread
    }

    public static void ImplementingAnAsynchronousAPI(){
        var service = new MailService();
        service.sendAsync(); // non-blocking
        System.out.println("Hello World"); // continue operation

        try {
            Thread.sleep(5000); // to prevent the program closing too early..
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void CompletableFutureExample(){
        // Runnable task = () -> System.out.println("a");
        Supplier<Integer> task = () -> 1;
        var future = CompletableFuture.supplyAsync(task);
        try {
            var result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void CallablesAndFuturesExample(){
        var executor = Executors.newFixedThreadPool(2);

        try{
            // System.out.println(executor.getClass().getName());
            var future = executor.submit(() -> {
                LongTask.simulate(); // a very long operation
                return 1;
            });

            System.out.println("Do more work"); // main thread continues

            var result = future.get();  // this is a blocking method
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public static void ExecutorExample() {
        var executor = Executors.newFixedThreadPool(2);

        try{
            // System.out.println(executor.getClass().getName());
            executor.submit(() ->
                    System.out.println(Thread.currentThread().getName())
            );
        }
        finally {
            executor.shutdown();
        }
    }
}
