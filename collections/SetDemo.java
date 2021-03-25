package collections;

import java.util.*;

public class SetDemo {

    public static void show(){
        Set<String> set = new HashSet<>();
        set.add("sky");
        set.add("is");
        set.add("blue");
        set.add("blue");

        Collection<String> collection = new ArrayList<>();
        Collections.addAll(collection, "a", "b", "c", "d", "d");
        Set<String> set2 = new HashSet<>(collection);

        Set<String> set3 = new HashSet<>(Arrays.asList("a", "b", "c"));
        Set<String> set4 = new HashSet<>(Arrays.asList("b", "c", "d"));
        // Union
        // set3.addAll(set4);
        // Intersection
        // set3.retainAll(set4);
        // Difference
        set3.removeAll(set4);

        System.out.println(set3); // note the order of the items are not guarantee
    }

}
