import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;



public class Collections {

    public static String reverseString(String input){
       return IntStream.range(0,input.length())
               .mapToObj(i-> input.charAt(input.length()-1-i))
               .map(String :: valueOf)
               .collect(Collectors.joining());


    }

    public static List<Integer> reverseNumberList(List<Integer> numbers) {
        return IntStream.range(0, numbers.size())
                .mapToObj(i -> numbers.get(numbers.size() - i - 1))
                .collect(Collectors.toList());
    }

    public static String removeDuplicates(String input) {
        // Convert the string to a stream of characters and remove duplicates
        return input.chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    public static List<Integer> removeDuplicates(List<Integer> numbers) {
        // Use Stream API to remove duplicates
        return numbers.stream()
                .distinct()
                .collect(Collectors.toList());
    }


    public static void findMaxAndMinStream(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("Array is empty or null");
            return;
        }

        int max = Arrays.stream(arr).max().getAsInt();
        int min = Arrays.stream(arr).min().getAsInt();

        System.out.println("Stream API method:");
        System.out.println("Maximum element: " + max);
        System.out.println("Minimum element: " + min);
    }





    public static void main(String[] args) {
        String original = "Hello, World!";
        String reversed = reverseString(original);
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> reversedNumbers = reverseNumberList(numbers);
        System.out.println("Original List: " + numbers);
        System.out.println("Reversed List: " + reversedNumbers);


        String input = "Hello, World!";
        String withoutDuplicates = removeDuplicates(input);
        System.out.println("Original: " + input);
        System.out.println("Without duplicates: " + withoutDuplicates);


        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 2, 4, 5, 1, 6, 3, 7);
        List<Integer> withoutDuplicates1 = removeDuplicates(numbers);

        System.out.println("Original List: " + numbers1);
        System.out.println("List Without Duplicates: " + withoutDuplicates1);




        int[] numbers2 = {3, 5, 7, 2, 8, -1, 4, 10, 12, -4};

        IntSummaryStatistics stats = Arrays.stream(numbers2).summaryStatistics();

        int max = stats.getMax();
        int min = stats.getMin();

        System.out.println("Array: " + Arrays.toString(numbers2));
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);



        int[] numbers4 = {4, 1, 13, 90, 16, 2, 0, 31, 5, 1};
        findMaxAndMinStream(numbers4);

    }
//    private static double calculateTeamAverageHours(List<AvgEffective> employees) {
//        return employees.stream()
//                .mapToDouble(e -> e.getEffectiveHours().getHour() + e.getEffectiveHours().getMinute() / 60.0)
//                .average()
//                .orElse(0);
//    }
//    private static Map<Integer, Double> calculateEmployeeAverageHours(List<AvgEffective> employees) {
//        return employees.stream()
//                .collect(Collectors.groupingBy(
//                        AvgEffective::getId,
//                        Collectors.averagingDouble(e -> e.getEffectiveHours().getHour() + e.getEffectiveHours().getMinute() / 60.0)
//                ));
//    }

}


