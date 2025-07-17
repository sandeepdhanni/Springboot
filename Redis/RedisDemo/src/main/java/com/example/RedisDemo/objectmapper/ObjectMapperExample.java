package com.example.RedisDemo.objectmapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ObjectMapperExample {
    public static String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Employee emp = new Employee("John", 30);
        String json = null;
            json = mapper.writeValueAsString(emp);
            return json; // {"name":"John","age":30}
    };


    public static void main(String[] args) throws JsonProcessingException {

        Employee emp = new Employee("John", 30);
        System.out.println(convertToJson(emp)); // {"name":"John","age":30}
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"name\":\"John\",\"age\":30}";
        Employee empObj = mapper.readValue(jsonString, Employee.class);
        System.out.println(empObj);



    }
}
