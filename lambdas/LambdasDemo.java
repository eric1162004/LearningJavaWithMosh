package lambdas;

import java.util.List;
import java.util.Locale;
import java.util.function.*;

public class LambdasDemo {
    public String prefix = "-";

    public LambdasDemo(String message) {
        System.out.println("this is ctor");
    }

    public static void show(){
    }

    public static void UnaryOperatorExample(){
        UnaryOperator<Integer> square = n -> n * n;
        UnaryOperator<Integer> increment = n -> n + 1;

        var result = square.andThen(increment).apply(2);
    }

    public static void BinaryOperatorExample(){
        BinaryOperator<Integer> add = (a,b) -> a + b;
        Function<Integer, Integer> square = a -> a * a;

        var result = add.andThen(square).apply(1,2);
    }

    public static void CombiningPredicateExample(){
        Predicate<String> isLongerThen5 = str -> str.length() > 5;
        var  result = isLongerThen5.test("sky");

        Predicate<String> hasLeftBrace = str-> str.startsWith("{");
        Predicate<String> hasRightBrace = str-> str.startsWith("}");

        Predicate<String> hasLeftAndRightBraces = hasLeftBrace.and(hasRightBrace);
        var result2 = hasLeftAndRightBraces.test("{key:value}");
    }

    public static void ChainingFunctionInterfaceExample(){
        // "key:value"
        // first: "key=value"
        // second: "{key=value}"
        Function<String, String> replaceColon = str -> str.replace(":", "=");
        Function<String, String> addBraces = str -> "{" + str + "}";

        // Declarative Programming
        String result;
        result = replaceColon
                .andThen(addBraces)
                .apply("key:value");
    }

    public static void FunctionInterfaceExample(){
        Function<String, Integer> map = str -> str.length();
        var length = map.apply("Sky");
    }

    public static void SupplierExample(){
        Supplier<Double> getRandom = () -> Math.random(); // Generic Supplier
        DoubleSupplier getRandom2 = () -> Math.random(); // Supplier for return double
        getRandom.get(); // to execute supplier
    }

    public static void CustomerExample(){
        List<Integer> list = List.of(1,2,3);

        // Imperative Programming (for, if/else, switch/case)
        for (var item: list)
            System.out.println(item);

        // Declarative Programming
        list.forEach(number -> System.out.println(number));

        List<String> list2 = List.of("a", "b", "c");
        // Consumer Methods
        Consumer<String> print = item -> System.out.println(item);
        Consumer<String> printUpperCase = item -> System.out.println(item.toUpperCase());

        list2.forEach(print.andThen(printUpperCase));
    }

    public static void MethodReferenceExample(){
        var demo = new LambdasDemo("message");

        greet(LambdasDemo::new); // Passing Ctor as Method Reference
        greet(demo::print2); // Passing Instance Method Reference
        greet(LambdasDemo::print1); // Passing Static Method Reference
    }

    // Instance method to pass
    public void print2(String message) {
        System.out.println(message);
    }
    // Static method to pass
    public static void print1(String message){
        System.out.println(message);
    }

    public static void greet(Printer printer){
        printer.print("Hello world!");
    }

    public static void AnonymousInnerClassExample(){
        // do not need to create an implementation class
        greet(new Printer() {
            @Override
            public void print(String message) {
                System.out.println(message);
            }
        });
    }

}
