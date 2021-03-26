package streams;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* Streams - To process a collection of data in a declarative way */

public class StreamsDemo {

    public static void show(){
    }

    public static void primitiveTypeStreamExample(){
        IntStream.rangeClosed(1, 5)
                .forEach(System.out::println);
    }

    public static void partitioningElements(){
        List<Movie> movies = List.of(
                new Movie("b", 10, Genre.THRILLER),
                new Movie("a", 10, Genre.ACTION),
                new Movie("c", 20, Genre.ACTION),
                new Movie("d", 30, Genre.ACTION)
        );

        var result = movies.stream()
                .collect(Collectors
                        .partitioningBy(
                                m->m.getLikes()>20,
                                Collectors.mapping(
                                        Movie::getTitle,
                                        Collectors.joining(", "))
                        ));

        System.out.println(result);

    }

    public static void groupingElementsExample(){
        List<Movie> movies = List.of(
                new Movie("b", 10, Genre.THRILLER),
                new Movie("a", 10, Genre.ACTION),
                new Movie("c", 20, Genre.ACTION),
                new Movie("d", 30, Genre.ACTION)
        );

        var result = movies.stream()
                .collect(Collectors.groupingBy(
                        Movie::getGenre, Collectors.toSet())); // key= Genre, value = set

        var result2 = movies.stream()
                .collect(Collectors.groupingBy(
                        Movie::getGenre, Collectors.counting()));

        var result3 = movies.stream()
                .collect(Collectors.groupingBy(
                        Movie::getGenre,
                        Collectors.mapping(
                                Movie::getTitle,
                                Collectors.joining(", "))));

        System.out.println(result3);
    }

    public static void CollectorExample(){
        List<Movie> movies = List.of(
                new Movie("b", 10),
                new Movie("a", 10),
                new Movie("c", 20),
                new Movie("d", 30)
        );

        var result = (movies.stream())
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.toList()); // or toSet()

        var result2 = (movies.stream())
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.toMap(Movie::getTitle, Function.identity())); //Key map, Value map

        var result3 = (movies.stream())
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.summingInt(Movie::getLikes)); // like reducer, return the sum of likes of all movies

        var result4 = (movies.stream())
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.summarizingInt(Movie::getLikes));
        // providing statistics of the filtered movies
        // such as count, sum, min, average, max

        var result5 = (movies.stream())
                .map(m -> m.getTitle())
                .collect(Collectors.joining(", ")); // concatenating the results

    }

    public static void ReducerExample2(){
        List<Movie> movies = List.of(
                new Movie("b", 10),
                new Movie("a", 10),
                new Movie("c", 20),
                new Movie("d", 30)
        );

        Optional<Integer> sum = movies.stream()
                .map(m -> m.getLikes())
                .reduce(Integer::sum);

        var result = sum.orElse(0); // provide a default value as param

        // or without using the Optional Class
        Integer sum2 = movies.stream()
                .map(m -> m.getLikes())
                .reduce(0, Integer::sum); // place the default value as the first arg
    }

    public static void ReducerExamples(){
        List<Movie> movies = List.of(
                new Movie("b", 10),
                new Movie("a", 10),
                new Movie("c", 20),
                new Movie("d", 30)
        );

        var result = movies.stream()
                .count();

        // .anyMatch(m ->  m.getLike() > 20) = any movie has > 20 likes?
        // .allMatch(m -> m.getLike() > 20) = all the movies have > 20 likes?
        // .noneMatch( ... )
        // .findFirst( ... ).get() = return first element that match the condition
        // .findAny( ... ).get()
        // .max( Comparator.comparing(Movie::getLikes).get()
    }

    public static void gettingUniqueElements(){
        List<Movie> movies = List.of(
                new Movie("b", 10),
                new Movie("a", 10),
                new Movie("c", 20),
                new Movie("c", 20)
        );

        movies.stream()
                .map(Movie::getLikes)
                .distinct()
                .forEach(System.out::println);
    }

    public static void peekExample(){
        List<Movie> movies = List.of(
                new Movie("b", 10),
                new Movie("a", 10),
                new Movie("c", 20),
                new Movie("d", 30)
        );

        movies.stream()
                .filter(m -> m.getLikes() > 10)
                .peek(m -> System.out.println("filtered: "+ m.getTitle()))
                .map(Movie::getTitle)
                .peek(t -> System.out.println("mapped:" + t))
                .forEach(System.out::println);
    }

    public static void sortingExample(){
        List<Movie> movies = List.of(
                new Movie("b", 15),
                new Movie("a", 10),
                new Movie("c", 20)
        );

        movies.stream()
/*                .sorted( (a, b) -> a.getTitle().compareTo(b.getTitle()))*/
                .sorted(Comparator.comparing(Movie::getTitle))
                .forEach( m -> System.out.println(m.getTitle()));
    }

    public static void slicingStreams(){
        List<Movie> movies = List.of(
                new Movie("a", 10),
                new Movie("b", 15),
                new Movie("c", 20)
        );

        // 1000 movies in db
        // display 10 movies per page = pageSize
        // want to display the 3rd page
        // skip(20) = skip( pageSize * (currentPage-1) )
        // limit(pageSize)

        movies.stream()
                .skip(20)
                .limit(10)
                .forEach(m -> System.out.println(m.getTitle()));

        movies.stream()
                .takeWhile( m -> m.getLikes() > 10);

        movies.stream()
                .dropWhile( m -> m.getLikes() > 10);
    }

    public static void filteringStreams(){
            List<Movie> movies = List.of(
                    new Movie("a", 10),
                    new Movie("b", 15),
                    new Movie("c", 20)
            );

            Predicate<Movie> isPopular = m -> m.getLikes() > 10;

            movies.stream()
                    .filter(isPopular)
                    .forEach(m -> System.out.println(m.getLikes()));
    }

    public static void mappingStreams(){
        List<Movie> movies = List.of(
                new Movie("a", 10),
                new Movie("b", 15),
                new Movie("c", 20)
        );

        movies.stream()
                .map(movie -> movie.getTitle())
                .forEach( name -> System.out.println(name));

        var stream = Stream.of(List.of(1,2,3), List.of(4,5,6));
        stream
                .flatMap(list -> list.stream())  // Stream<List<x>> -> Stream<x>
                .forEach(list -> System.out.println(list));

    }

    public static void CreatingStreams(){
        int[] numbers = {1,2,3};
        Arrays.stream(numbers) // Use Arrays Until Class to get a stream
                .forEach( n -> System.out.println(n));

        // Creating a stream using Stream.of()
        var streamExample = Stream.of(1,2,3,4);

        // Generate infinite stream - this is a lazy evaluation
        var streamOfRand = Stream.generate(() -> Math.random());
        streamOfRand.limit(10)
                .forEach( n -> System.out.println(n));

        // Stream.iterate() takes a seed argument and
        Stream.iterate(1, n -> n + 1)
                .limit(10)
                .forEach( n -> System.out.println(n));

    }

    public static void Example1(){
        List<Movie> movies = List.of(
                new Movie("a", 10),
                new Movie("b", 15),
                new Movie("c", 20)
        );

        // Imperative Programming - has statements specifying how something should be done
        int count = 0;
        for(var movie : movies){
            if(movie.getLikes() > 10)
                count++;
        }

        // Declarative(Functional) Programming
        var count2 = movies.stream()
                .filter(movie -> movie.getLikes() > 10)
                .count();


    }
}
