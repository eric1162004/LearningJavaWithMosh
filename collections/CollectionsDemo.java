package collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CollectionsDemo {
    public static void show(){
        Collection<String> collection = new ArrayList<>();

        Collections.addAll(collection, "a", "b", "c");

        collection.add("a");
        collection.add("b");
        collection.add("c");

        collection.remove("a");
        collection.clear();

        var isContainA= collection.contains("a");
        var isEmpty = collection.isEmpty();

        Object[] objectArray = collection.toArray(); // this will return an array of objects, not strings!
        String[] stringArray = collection.toArray(new String[0]); // this string array expands automatically

        Collection<String> other = new ArrayList<>();
        other.addAll(collection);

        System.out.println(collection == other); //false. comparing different objects
        System.out.println(collection.equals(other)); // true


        System.out.println(collection.size());

        for(var i : collection)
            System.out.println(i);
    }
}
