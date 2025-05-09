package streams;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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




        //Find Common Elements Between Two Lists Using Streams
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(3, 4, 5, 6, 7);

        List<Integer> common = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());

        System.out.println("Common: " + common);



        // Convert a List of Integers to a Map of Squares
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        Map<Integer, Integer> squares = nums.stream()
                .collect(Collectors.toMap(n -> n, n -> n * n));
        System.out.println(squares);


// extracting the substring after the 4th letter (i.e., starting from index 4)
        List<String> value = Arrays.asList("sandeep", "dhanni", "morning");
        value.stream()
                .map(str -> str.length() > 4 ? str.substring(4) : "") // Safely handle short strings
                .forEach(System.out::println);



// Converting a list of strings to uppercase
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        words.stream()
                .map(String::toUpperCase)  // Converts each word to uppercase
                .forEach(System.out::println);

// Extracting first letters from a list of words
        List<String> words1 = Arrays.asList("apple", "banana", "cherry");
        words.stream()
                .map(word -> word.charAt(0))  // Extracts the first letter of each word
                .forEach(System.out::println);

       // Filtering strings that start with a specific letter
        List<String> words2 = Arrays.asList("apple", "banana", "cherry", "avocado");
        words.stream()
                .filter(word -> word.startsWith("a"))  // Filters words that start with "a"
                .forEach(System.out::println);

//Filtering even numbers and squaring them
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.stream()
                .filter(num -> num % 2 == 0)  // Filters even numbers
                .map(num -> num * num)        // Squares each even number
                .forEach(System.out::println);

//Capitalize each string
        List<String> names = Arrays.asList("john", "doe", "alice");
        names.stream()
                .map(str -> str.substring(0, 1).toUpperCase() + str.substring(1))
                .forEach(System.out::println);


//Filter strings by length
        List<String> items = Arrays.asList("pen", "notebook", "book", "pencil");
        items.stream()
                .filter(s -> s.length() > 4)
                .forEach(System.out::println);


//Count strings with specific character
        long count = Arrays.asList("apple", "banana", "cherry", "avocado")
                .stream()
                .filter(str -> str.contains("a"))
                .count();
        System.out.println("Words containing 'a': " + count);




    }


}

