package collections;

/* Java: Map
 *  C#: Dictionary
 *  Python: Dictionary
 *  Javascript: Objects
 * */

import java.util.HashMap;
import java.util.Map;

public class MapDemo {
    public static void show(){
        var c1 = new Customer("a", "e1");
        var c2 = new Customer("b", "e2");
        Map<String, Customer> map = new HashMap<>();
        map.put(c1.getEmail(), c1);
        map.put(c2.getEmail(), c2);

        var customer = map.get("e1");
        System.out.println(customer);

        var unknown = new Customer("Unknown", "");
        var customer2 = map.getOrDefault("e1", unknown); // return default if the item does not exist

        var exists = map.containsKey("e10"); // return boolean whether this key exists

        map.replace("e1", new Customer("c", "e3"));

        for(var key : map.keySet())
            System.out.println(key);

        for(var c : map.values())
            System.out.println(c);

        for(var entry : map.entrySet())
            System.out.println(entry);

    }
}
