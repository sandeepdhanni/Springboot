package programs;

import java.util.stream.Collectors;

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
    }
}
