package completableFuture;

import java.util.concurrent.CompletableFuture;


public class CompletableFutureDemo {



    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(result -> result + " World");

        System.out.println(future.join()); // Hello World

    }
}
