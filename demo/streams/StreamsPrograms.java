package streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsPrograms {

    public static void main(String[] args) {

        // Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}......print  {(1,10),(2,9),(3,8),(4,7),(5,6)}  where the total is 11
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int targetSum = 11;

        List<String> pairs = IntStream.range(0, arr.length / 2)
                .filter(i -> arr[i] + arr[arr.length - 1 - i] == targetSum)
                .mapToObj(i -> "(" + arr[i] + "," + arr[arr.length - 1 - i] + ")")
                .collect(Collectors.toList());

        System.out.println(pairs);




        //Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}......print  {(1,9),(2,10),(3,7),(4,8)}
        int[] arr11 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Separate odd and even numbers
        List<Integer> odds = IntStream.of(arr11)
                .filter(num -> num % 2 != 0)
                .boxed()
                .toList();

        List<Integer> evens = IntStream.of(arr11)
                .filter(num -> num % 2 == 0)
                .boxed()
                .toList();

        // Generate pairs (odd, even)
        List<String> pairs1 = IntStream.range(0, Math.min(odds.size(), evens.size()))
                .mapToObj(i -> "(" + odds.get(i) + "," + evens.get(i) + ")")
                .collect(Collectors.toList());

        System.out.println(pairs1);

    }


}

