package streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StreamOperation {
    public static void main(String[] args) {
        //Operaciones intermedias: Resumen
        //1. Filter
        List<String> nombres = Arrays.asList("Ana", "Juan", "Pedro", "Maria");

        Stream<String> intermedio = nombres.stream()
                .filter(n -> n.length() > 3)
                .map(String::toUpperCase);

        //Operaciones finales: Resumen
        List<String> resultado = nombres.stream()
                .filter(n -> n.length() > 3)
                .map(String::toUpperCase)
                .toList(); // operación final → ejecuta todo

        //Map vs flatMap
        List<String> frases = Arrays.asList("Hola mundo", "Programación en Java");

        List<String> palabras = frases.stream()
                .flatMap(frase -> Arrays.stream(frase.split(" ")))
                .toList();

        //Collectors
        List<String> lista = nombres.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        //joining

        String unidos = nombres.stream()
                .collect(Collectors.joining(", "));

        //groupingBy
        Map<Integer, List<String>> agrupados = nombres.stream()
                .collect(Collectors.groupingBy(String::length));

        //averagingInt
        double promedio = nombres.stream()
                .collect(Collectors.averagingInt(String::length));

        //Combinaciones
        //List<String> nombres = Arrays.asList("Ana", "Juan", "Pedro", "Ana");
        nombres.stream()
                .filter(n -> n.length() > 3)
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);

    }
}
