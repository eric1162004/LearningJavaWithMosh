package exceptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExceptionsDemo {

    public static void example2() throws IOException {
        var account = new Account();
        try {
            account.deposit(1);
        } catch (IOException e) {
            System.out.println("Logging");
            e.printStackTrace();
            throw e;
        }
    }

    public static void example1(){

        try (
                var reader = new FileReader("file.txt");
                var writer = new FileWriter(("..."));
        ) {
            var value = reader.read();
            new SimpleDateFormat().parse("");
            System.out.println("File opened");
        }
        catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        }
        catch (IOException | ParseException e){
            System.out.println("Could not read data");
        }


    }

    public static void sayHello(String name){
        System.out.println(name.toUpperCase());
    }
}
