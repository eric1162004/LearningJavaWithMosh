package collections;

import java.util.ArrayDeque;
import java.util.Queue;

public class QueueDemo {
    public static void show(){
        Queue<String> queue = new ArrayDeque<>();
        queue.add("c");
        queue.add("a");
        queue.add("b");

        // b->a->c
        queue.offer("d"); // also add an item to the queue, but return false if the queue is full

        var front = queue.peek(); // return null if the queue is empty
        System.out.println(front);

        queue.remove(); //return the removed item, throw exception if the queue is empty
        queue.poll(); // also remove an item; return null if the queue is empty





    }

}
