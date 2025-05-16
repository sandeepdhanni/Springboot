package com.example.ReactiveProgramming;


import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MonoFluxTest {

    private Mono<String> testMono(){
        return Mono.just("sandeep").log();
    }
    private Mono<String> testMono1(){
        return Mono.just(null);
    }
    private Mono<String> testMono2(){
        return Mono.justOrEmpty(null);
    }


    private Flux<String> test(){
        return Flux.just("sam","dhanni","rohit","babu");
    }

    private Flux<String> testFlux(){
        List<String> sam = List.of("sam", "dhanni", "rohit", "babu");
       return  Flux.fromIterable(sam);
    }

    private Flux<String> testMap(){
        Flux<String> sam = Flux.just("sam", "dhanni", "rohit", "babu");
        return  sam.map(s->s.toUpperCase(Locale.ROOT));
    }

    private Flux<String> testFlatMap(){
        Flux<String> sam = Flux.just("sam", "dhanni", "rohit", "babu");
        return  sam.flatMap(s->Mono.just(s.toUpperCase(Locale.ROOT)));
    }

    //if i want to skip the elements
    private Flux<String> testSkip(){
        Flux<String> sam = Flux.just("sam", "dhanni", "rohit", "babu");
        return  sam.skip(2);
    }

    //if i want to delay the elements like 1 second for every element
    private Flux<String> testSkip1(){
        Flux<String> sam = Flux.just("sam", "dhanni", "rohit", "babu");
        return  sam.delayElements(Duration.ofSeconds(1)).log();
    }

    //if i want to skip based on time period like if i want to skip the elements for first 2 sec
    //and then give me the rest of the data
    private Flux<String> testSkip2(){
        Flux<String> sam = Flux.just("sam", "dhanni", "rohit", "babu")
                .delayElements(Duration.ofSeconds(1));
        return sam.skip(Duration.ofSeconds(2));
    }


    //if i want to skip the from the last element then use skiplast
    private Flux<String> testSkip3(){
        Flux<String> sam = Flux.just("sam", "dhanni", "rohit", "babu")
                .delayElements(Duration.ofSeconds(1));
        return sam.skipLast(2);
    }

    //it will display the count upto 20
    private Flux<Integer> testComplexSkip(){
        Flux<Integer> sam =Flux.range(1,20);
        return sam;
    }

    //it will skip until the condition is true
    private Flux<Integer> testComplexSkip1(){
        Flux<Integer> sam =Flux.range(1,20);
        return sam.skipUntil(integer->integer==10);
    }

    private Flux<Integer> testComplexSkip2(){
        Flux<Integer> sam =Flux.range(1,20);
        return sam.skipWhile(integer->integer<10);
    }


    //if i want to join two flux then use concat
    private Flux<Integer> testConcat() {
        Flux<Integer> flux1 = Flux.range(1, 20);
        Flux<Integer> flux2 = Flux.range(101, 20);
        Flux<Integer> flux3 = Flux.range(1001, 20);
        return Flux.concat(flux3, flux2, flux1);
    }


    //it is same like concat but the main difference is if we use concat then it will
    // sequentially display values when 1 flux complete then other starts and when
    // we use merge then it will randomly display
    private Flux<Integer> testMerge() {
        Flux<Integer> flux1 = Flux.range(1, 20)
                .delayElements(Duration.ofMillis(500));
        Flux<Integer> flux2 = Flux.range(101, 20)
                .delayElements(Duration.ofMillis(500));

        return Flux.merge(flux1, flux2);
    }


    private Flux<Tuple2<Integer, Integer>> testZip() {
        Flux<Integer> flux1 = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(500));
        Flux<Integer> flux2 = Flux.range(101, 20)
                .delayElements(Duration.ofMillis(500));
        return Flux.zip(flux1, flux2);
    }


    //as the first flux count is 10 it will go upto 10 and stop
    private Flux<Tuple3<Integer, Integer,Integer>> testComplex() {
        Flux<Integer> flux1 = Flux.range(1, 20)
                .delayElements(Duration.ofMillis(500));
        Flux<Integer> flux2 = Flux.range(101, 30)
                .delayElements(Duration.ofMillis(500));
        Flux<Integer> flux3 = Flux.range(101, 40)
                .delayElements(Duration.ofMillis(500));
        return Flux.zip(flux3,flux1, flux2);
    }


    private Flux<Tuple2<Integer, Integer>> testComplexZip() {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(500));
        Mono<Integer> mono = Mono.just(1);
        return Flux.zip(flux, mono);
    }


    private Mono<List<Integer>> testCollect() {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000));
        return flux.collectList();
    }


    private Flux<List<Integer>> testBuffer() {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000));
        return flux.buffer(Duration.ofMillis(3_100));
    }


    private Mono<Map<Integer, Integer>> testMapCollection() {
        //a, a*a
        //5, 25
        //6, 36
        Flux<Integer> flux = Flux.range(1, 10);
        return flux.collectMap(integer -> integer * 2, integer -> integer * integer);
    }

    //doeach will work based on signal
    private Flux<Integer> doOnEach(){
        Flux<Integer>  flux=Flux.range(1,10);
        return flux.doOnEach(signal-> System.out.println(signal));
    }


    private Flux<Integer> testDoFunctions() {
        Flux<Integer> flux = Flux.range(1, 10);
        return flux.doOnEach(signal -> {
            if (signal.getType() == SignalType.ON_COMPLETE) {
                System.out.println("I am done!");
            } else {
                System.out.println(signal.get());
            }
        });
    }


    private Flux<Integer> doOnNext() {
        Flux<Integer> flux = Flux.range(1, 10);
        return flux.doOnNext((integer) -> System.out.println(integer));
    }


    private Flux<Integer> testDoFunctions2() {
        Flux<Integer> flux = Flux.range(1, 10);
        return flux.doOnComplete(() -> System.out.println("I am complete"));
    }


    private Flux<Integer> testDoFunctions3() {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1));
        return flux.doOnCancel(() -> System.out.println("Cancelled!"));
    }


    private Flux<Integer> testErrorHandling() {
        Flux<Integer> flux = Flux.range(1, 10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Unexpected number!");
                    }
                    return integer;
                });
        return flux
                .onErrorContinue((throwable, o) -> System.out.println("Don't worry about " + o));
    }


    private Flux<Integer> testErrorHandling2() {
        Flux<Integer> flux = Flux.range(1, 10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Unexpected number!");
                    }
                    return integer;
                });
        return flux
                .onErrorResume(throwable -> Flux.range(100, 5));
    }

//onErrorMap() doesn't suppress the error — it just changes its type or message
// retry(1) → restarts the entire stream once
    private Flux<Integer> testErrorHandling3() {
        Flux<Integer> flux = Flux.range(1, 10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Unexpected number!");
                    }
                    return integer;
                });
        return flux
                .onErrorMap(throwable -> new UnsupportedOperationException(throwable.getMessage()));
    }

    private Flux<Integer> onErrorWithretry() {
        Flux<Integer> numbers = Flux.just(1, 0, 2)
                .map(i -> 10 / i)  // division by zero
                .onErrorMap(e -> new IllegalStateException("Illegal operation"))  // change the exception
                .retry(1); // retry once on error

        return numbers;
    }


    private Flux<Integer> testErrorHandling4() {
        Flux<Integer> flux = Flux.range(1, 10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Unexpected number!");
                    }
                    return integer;
                });
        return flux
                .onErrorComplete();
    }


    public static void main(String[] args) throws InterruptedException {
        MonoFluxTest monoFluxTest=new MonoFluxTest();
//        monoFluxTest.testMono().subscribe(System.out::println);
//        monoFluxTest.testMono1().subscribe(System.out::println);
//        monoFluxTest.testMono2().subscribe(System.out::println);
//        monoFluxTest.test().subscribe(System.out::println);
//        monoFluxTest.testFlux().subscribe(System.out::println);
//        monoFluxTest.testMap().subscribe(System.out::println);
//        monoFluxTest.testMap().subscribe(System.out::println);
//        monoFluxTest.testSkip().subscribe(System.out::println);

//          monoFluxTest.testSkip1().subscribe(System.out::println);
//          Thread.sleep(10000);

//        monoFluxTest.testSkip2().subscribe(System.out::println);
//        Thread.sleep(10000);

//        monoFluxTest.testSkip3().subscribe(System.out::println);
//        Thread.sleep(10000);

//        monoFluxTest.testComplexSkip().subscribe(System.out::println);
//        monoFluxTest.testComplexSkip1().subscribe(System.out::println);
//        monoFluxTest.testComplexSkip2().subscribe(System.out::println);
//          monoFluxTest.testConcat().subscribe(System.out::println);

//        monoFluxTest.testMerge().subscribe(System.out::println);
//        Thread.sleep(10000);

//        monoFluxTest.testZip().subscribe(System.out::println);
//        Thread.sleep(10000);

//        monoFluxTest.testComplex().subscribe(System.out::println);
//        Thread.sleep(10000);

//        monoFluxTest.testComplexZip().subscribe(System.out::println);
//        Thread.sleep(10000);

//        monoFluxTest.testCollect().subscribe(System.out::println);

//        monoFluxTest.testBuffer().subscribe(System.out::println);
//        Thread.sleep(30000);

//        monoFluxTest.testMapCollection().subscribe(System.out::println);
//        monoFluxTest.doOnEach().subscribe(System.out::println);
//        monoFluxTest.testDoFunctions().subscribe();
//        monoFluxTest.doOnNext().subscribe(System.out::println);
//        monoFluxTest.testDoFunctions2().subscribe(System.out::println);

//        Disposable disposable=monoFluxTest.testDoFunctions3().subscribe(System.out::println);
//        Thread.sleep(3500);
//        disposable.dispose();


//        monoFluxTest.testErrorHandling().subscribe(System.out::println);
//        monoFluxTest.testErrorHandling2().subscribe(System.out::println);
//        monoFluxTest.testErrorHandling3().subscribe(System.out::println);
//        monoFluxTest.testErrorHandling4().subscribe(System.out::println);
        monoFluxTest.onErrorWithretry().subscribe(System.out::println,
                err -> System.out.println("Final error: " + err));


    }


}
