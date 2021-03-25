import collections.*;
import exceptions.Account;
import exceptions.AccountException;
import exceptions.ExceptionsDemo;
import generics.GenericList;
import lambdas.LambdasDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        LambdasDemo.show();

    }

    public static void ComparableAndComparatorExample(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("b", "e3"));
        customers.add(new Customer("a", "e2"));
        customers.add(new Customer("c", "e1"));
        Collections.sort(customers); // sorting comparable
        Collections.sort(customers, new EmailComparator()); // sort using a comparator class
        System.out.println(customers);
    }

    public static void TextingIteratorExample(){
        var list = new GenericList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        for (var item :list) {
            System.out.println(item);
        }

    }

    public static void GenericExample1(){
        var list = new GenericList<Integer>();
        list.add(1);
        var num = list.get(0);
    }

    public static void TestingForExceptions(){
        var account = new Account();
        try{
            account.withdraw(10);
        } catch (AccountException e) {
            var cause = e.getCause();
            System.out.println(cause.getMessage());
        }

        try {
            ExceptionsDemo.example2();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
