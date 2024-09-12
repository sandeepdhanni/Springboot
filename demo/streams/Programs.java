package streams;

import java.util.*;
import java.util.stream.Collectors;

public class Programs {

    public static void main(String args[]){


        try{
            System.out.println("1");
            int arr3[]=null;
            System.out.println(arr3.length);
        }catch(ArithmeticException e){
            System.out.println("2");
        }catch(NullPointerException e){
            System.out.println("3");
        }finally{
            System.out.println("4");
        }



        //filter ------->  conditional check
        List<String> list1=new ArrayList<>();
        list1.add("sandeep");
        list1.add("dhanni");
        list1.add("john");
        list1.add("sam");
        for(String s:list1){
            if(s.startsWith("s")){
                System.out.println(s);
            }
        }
        list1.stream().filter(t->t.startsWith("d")).forEach(t->System.out.println(t));



        Map<Integer,String> map1=new HashMap<>();
        map1.put(1,"sun");
        map1.put(2,"joe");
        map1.put(3,"sam");
        map1.put(4,"jess");
        map1.forEach((k,v)->System.out.println(k+":"+v));






        //iterate over list
        List<String> list=new ArrayList<>();
        list.add("sandeep");
        list.add("dhanni");
        list.add("john");
         list.add("sam");
//using conditional statements
         for(String s:list){
             System.out.println(s);
         }
         //using stream api
         list.stream().forEach(t->System.out.println(t));



         //iterate over map
         Map<Integer,String> map=new HashMap<>();
         map.put(1,"sun");
         map.put(2,"joe");
         map.put(3,"sam");
         map.put(4,"jess");
         map.forEach((key,value)->System.out.println(key+":"+value));
         //using stream api
         map.entrySet().stream().forEach((k)->System.out.println(k));




         List<Integer> list2=new ArrayList<>();
         list2.add(6);
        list2.add(5);
        list2.add(9);
        list2.add(1);
        list2.add(8);
        Collections.sort(list2);
        Collections.reverse(list2);
        System.out.println(list2);

        list2.stream().sorted().forEach(s->System.out.println(s));
        list2.stream().sorted(Comparator.reverseOrder()).forEach(s->System.out.println(s));








        //program to find the occurances of each character in a string
        String input="sandepdhanni";
      //  String[] result = input.split("");//this will convert string to string array
//        System.out.println(Arrays.toString(result));


    }
}
