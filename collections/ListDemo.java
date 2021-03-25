package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListDemo {
    public static void show(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add(0, "!"); // add to specific position

        Collections.addAll(list, "a", "b", "c");

        var item = list.get(0);
        list.set(0, "a+");
        list.remove(1);

        var firstOccurrence = list.indexOf("a");
        var lastOccurrence = list.lastIndexOf("a");

        var subList = list.subList(0, 2); // from is inclusive, to is exclusive

        System.out.println(list);


    }
}
