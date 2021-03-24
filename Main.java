import exceptions.Account;
import exceptions.AccountException;
import exceptions.ExceptionsDemo;
import exceptions.InsufficientFundsException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello world");

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
