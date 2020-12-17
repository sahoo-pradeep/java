package projects.sahoo.myspringboot.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class TestClass {
    public static void main(String[] args) {
        Map<String, String> carMap = new HashMap<>();
        carMap.put("color", "White");
        carMap.put("manufacturer", "Hyundai");

        Map<String, String> empMap = new HashMap<>();
        empMap.put("firstName", "Preetom");
        empMap.put("lastName", "Bhowmik");
        empMap.put("age", "25");

        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.convertValue(carMap, Car.class);
        System.out.println(car);
    }

}
