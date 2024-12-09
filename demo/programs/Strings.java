package programs;

import java.util.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Strings {

    public static void main(String[] args) {

        //check whether the string is palindrome or nor
        String str = "madam";
        // Reverse the string using StringBuilder
        String reversed = new StringBuilder(str).reverse().toString();
        // Check if the original string is equal to the reversed string
        if (str.equals(reversed)) {
            System.out.println(str + " is a palindrome.");
        } else {
            System.out.println(str + " is not a palindrome.");
        }
        //using streams
//        String str = "hello";
//        // Reverse the string using Stream API
//        String reversed = new StringBuilder(str)
//                .reverse()
//                .toString();
//        // Print the reversed string
//        System.out.println("Reversed string: " + reversed);




        //Given a string str = "hello world", write a program to find the substring "world".
        String sam = "hello world";
        String substring = "world";
        // Find the index of the substring
        int index = sam.indexOf(substring);
        // Check if the substring is found
        if (index != -1) {
            System.out.println("Substring \"" + substring + "\" found at index: " + index);
        } else {
            System.out.println("Substring \"" + substring + "\" not found.");
        }




        //Given two strings str1 = "listen" and str2 = "silent", write a program to check if they are anagrams.
        String str1 = "listen";
        String str2 = "silent";

        // Convert strings to character arrays and sort them
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        boolean areAnagrams = Arrays.equals(arr1, arr2);
        // Print the result
        if (areAnagrams) {
            System.out.println(str1 + " and " + str2 + " are anagrams.");
        } else {
            System.out.println(str1 + " and " + str2 + " are not anagrams.");
        }




        //Given a string str = "abcabcbb", write a program to find the maximum length of a substring with no repeating characters.
        String stri = "abcabcbb";
        // Call the method to find the maximum length of a substring with no repeating characters
        int maxLength = lengthOfLongestSubstring(stri);
        // Print the result
        System.out.println("Maximum length of substring with no repeating characters: " + maxLength);




       //Given a string str = "aeioavdstheu", write a program to find vowels and consonents.
        String stro = "aeioavdstheu";

        // Find and print vowels and consonants
        findVowelsAndConsonants(stro);



    }


    public static void findVowelsAndConsonants(String s) {
        // Define sets for vowels and consonants
        Set<Character> vowels = new HashSet<>(Set.of('a', 'e', 'i', 'o', 'u'));
        Set<Character> consonants = new HashSet<>();
        Set<Character> foundVowels = new HashSet<>();
        Set<Character> foundConsonants = new HashSet<>();

        // Traverse the string
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                if (vowels.contains(c)) {
                    foundVowels.add(c);
                } else {
                    consonants.add(c);
                    foundConsonants.add(c);
                }
            }
        }

        // Print results
        System.out.println("Vowels found: " + foundVowels);
        System.out.println("Consonants found: " + foundConsonants);
    }



    public static int lengthOfLongestSubstring(String s) {
        Set<Character> charSet = new HashSet<>();
        int left = 0; // Left pointer of the window
        int maxLength = 0; // Maximum length of substring without repeating characters
        // Iterate over the string with the right pointer
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            // Move the left pointer if the character is already in the set
            while (charSet.contains(currentChar)) {
                charSet.remove(s.charAt(left));
                left++;
            }
            // Add the current character to the set
            charSet.add(currentChar);
            // Update the maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }







    }

