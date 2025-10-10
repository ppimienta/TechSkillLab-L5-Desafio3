package streams;

import resources.Empleado;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public class StreamPerfDemo {
    public static void main(String[] args) {
        List<Empleado> empleados = IntStream.range(0, 1_000_000)
                .mapToObj(i -> new Empleado("Nombre" + i, "Apellido" + i, "Genero"+i, "TI", "Desarrollador",
                        new BigDecimal("2500"), LocalDate.now(), null, true))
                .toList();

// SECUENCIAL
        Instant startSeq = Instant.now();
        double avgSeq = empleados.stream()
                .map(Empleado::getSalario)
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0);
        Instant endSeq = Instant.now();
        System.out.println("Promedio secuencial: " + avgSeq + " en " +
                Duration.between(startSeq, endSeq).toMillis() + " ms");

// PARALELO
        Instant startPar = Instant.now();
        double avgPar = empleados.parallelStream()
                .map(Empleado::getSalario)
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0);
        Instant endPar = Instant.now();
        System.out.println("Promedio paralelo: " + avgPar + " en " +
                Duration.between(startPar, endPar).toMillis() + " ms");
    }


}
