package programs;

import java.util.ArrayList;
import java.util.List;

public class Arrays {


     public static void main(String[] args) {


         //this will print {1,3,6,10,15}..which will print the first and skip next anmd print and skip 2 value and it continues
         int k=0;
         int arr[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
         for(int i=0;i<=arr.length;i++){
             System.out.println(arr[i]);
             k++;
             i=i+k;
         }





         //Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print every second element.{2,4,6,8}
         int arr1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         for (int i = 1; i < arr1.length; i+= 2) {
             System.out.println(arr1[i]);
         }

         //Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print every first element.{1,3,5,7,9}
         int arr2[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         for (int i = 0; i < arr2.length; i+= 2) {
             System.out.println(arr2[i]);
         }




//Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print the sum of every third element.{3+6+9=18}
         int arr3[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         int sum = 0;
         // Start from index 2 (the third element) and increment by 3
         for (int i = 2; i < arr3.length; i += 3) {
             sum += arr3[i];
         }
         System.out.println("Sum of every third element: " + sum);



         //Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print the elements in reverse order.
         int arr4[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         // Start from the last element and move backwards
         for (int i = arr4.length - 1; i >= 0; i--) {
             System.out.println(arr4[i]);
         }



//Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, find the maximum and minimum elements.
         int arr5[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         int max = arr5[0];
         int min = arr5[0];
         // Loop through the array to find max and min
         for (int i = 1; i < arr5.length; i++) {
             if (arr5[i] > max) {
                 max = arr5[i];
             }
             if (arr[i] < min) {
                 min = arr5[i];
             }
         }
         System.out.println("Maximum element: " + max);
         System.out.println("Minimum element: " + min);


//Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, swap adjacent elements.{2,1,4,3,6,5,8,7,10,9}
         int arr6[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         // Swap adjacent elements
         for (int i = 0; i < arr6.length - 1; i += 2) {
             // Swap arr6[i] and arr6[i + 1]
             int temp = arr6[i];
             arr6[i] = arr6[i + 1];
             arr6[i + 1] = temp;
         }
         // Print the array after swapping
         for (int num : arr6) {
             System.out.print(num + " ");
         }





//Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, find the index of the first occurrence of a given element.
         int arr7[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         int target = 7; // Element to find
         int index = 0; // Default value if element is not found
         // Loop through the array to find the index of the target element
         for (int i = 0; i < arr7.length; i++) {
             if (arr7[i] == target) {
                 index = i;
                 break; // Exit the loop once the element is found
             }
         }
         // Print the index of the first occurrence
         System.out.println("Index of the first occurrence of " + target + ": " + index);



//Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print the elements that are multiples of 3.
         int arr8[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         // Loop through the array
         for (int num : arr8) {
             if (num % 3 == 0) { // Check if the number is a multiple of 3
                 System.out.println(num);
             }
         }





        // Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}......print  {(1,10),(2,9),(3,8),(4,7),(5,6)}  where the total is 11
         int[] arr12 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         int targetSum = 11;
         List<String> pairs = new ArrayList<>();

         int left = 0;
         int right = arr12.length - 1;

         while (left < right) {
             int sum1 = arr12[left] + arr12[right];
             if (sum1 == targetSum) {
                 pairs.add("(" + arr12[left] + "," + arr12[right] + ")");
                 left++;
                 right--;
             } else if (sum1 < targetSum) {
                 left++;
             } else {
                 right--;
             }
         }

         System.out.println(pairs);






         //Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}......print  {(1,9),(2,10),(3,7),(4,8)}
         int[] arr11 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

         // Separate odd and even numbers
         List<Integer> odds = new ArrayList<>();
         List<Integer> evens = new ArrayList<>();

         for (int num : arr11) {
             if (num % 2 == 0) {
                 evens.add(num);
             } else {
                 odds.add(num);
             }
         }

         // Generate pairs (odd, even)
         List<String> pairs1 = new ArrayList<>();
         int size = Math.min(odds.size(), evens.size());
         for (int i = 0; i < size; i++) {
             pairs1.add("(" + odds.get(i) + "," + evens.get(i) + ")");
         }

         System.out.println(pairs1);





































































































//         //given array of elements output like this...{1,3,6,10,15} using conditional statements
//         int k=0;
//         int arr[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
//         for(int i=0;i<arr.length;i++)
//         {
//             System.out.println(arr[i]);
//             k++;
//             i=i+k;
//         }
//
//
//
//
////Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print every second element.
//         int arr1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//         for (int i = 1; i < arr1.length; i += 2) {
//             System.out.println(arr[i]);
//         }
//
//
//
////Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print every first element.
//         int arr2[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//         for (int i = 0; i < arr2.length; i += 2) {
//             System.out.println(arr[i]);
//         }
//         //using stream api
////        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
////
////        IntStream.range(0, arr.length) // Generates a stream of indices from 0 to arr.length - 1
////                .filter(i -> i % 2 == 0) // Filters the stream to include only even indices
////                .map(i -> arr[i]) // Maps the indices to the corresponding array values
////                .forEach(System.out::println); // Prints each value
//
//
//
//
////Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print sum of every third element.
//         int arr3[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//         int sum = 0;
//         for (int i = 2; i < arr3.length; i += 3) {
//             sum += arr[i];
//         }
//         System.out.println("Sum of every third element: " + sum);
////using stream api
////        int arr3[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11};
////        int sum = IntStream.range(0, arr.length) // Generates a stream of indices from 0 to arr.length - 1
////                .filter(i -> (i + 1) % 3 == 0) // Filters to keep only indices that are every third element
////                .map(i -> arr3[i]) // Maps the indices to the corresponding array values
////                .sum(); // Sums the elements
////        System.out.println("Sum of every third element: " + sum);
//
//
//
//
//
////Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print the elements in reverse order.
//         int arr4[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//
//         // Start from the last element and move backwards
//         for (int i = arr4.length - 1; i >= 0; i--) {
//             System.out.println(arr4[i]);
//         }
////using stream api
////        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
////        // Convert the array to a List, reverse it, and then stream it
////        List<Integer> list = Arrays.asList(arr);
////        Collections.reverse(list);
////        list.forEach(System.out::println);
//
//
//
//
//
//
//         //Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, find the maximum and minimum elements.
//         int arr5[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//         int max = arr[0];
//         int min = arr[0];
//         // Loop through the array to find max and min
//         for (int i = 1; i < arr5.length; i++) {
//             if (arr5[i] > max) {
//                 max = arr5[i];
//             }
//             if (arr5[i] < min) {
//                 min = arr5[i];
//             }
//         }
//         System.out.println("Maximum element: " + max);
//         System.out.println("Minimum element: " + min);
//
//
//
//
//
//         //Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, swap adjacent elements.
//         int arr6[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//         // Swap adjacent elements
//         for (int i = 0; i < arr6.length - 1; i += 2) {
//             // Swap arr[i] and arr[i + 1]
//             int temp = arr6[i];
//             arr6[i] = arr6[i + 1];
//             arr6[i + 1] = temp;
//         }
//         // Print the array after swapping
//         for (int num : arr6) {
//             System.out.print(num + " ");
//         }
//
//
//
//
//         //Given an array arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, print the elements that are multiples of 3.
//         int[] arr7 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//         for (int num : arr7) {
//             if (num % 3 == 0) { // Check if the number is a multiple of 3
//                 System.out.println(num);
//             }
//         }


     }
}
