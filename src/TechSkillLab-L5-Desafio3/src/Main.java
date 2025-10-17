import resources.Empleado;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Empleado> empleados = new ArrayList<>();
        loadEmpleados(empleados);

        // 1. Calcular estadísticas de salario (mínimo, máximo, promedio)
        DoubleSummaryStatistics stats = empleados.stream()
                .mapToDouble(e -> e.getSalario().doubleValue()).summaryStatistics();
        System.out.println("\nCalculo de estadísticas del salario:");
        System.out.println("Salario mínimo: " + stats.getMin());
        System.out.println("Salario máximo: " + stats.getMax());
        System.out.println("Salario promedio: " + stats.getAverage());

        //2. Agrupar por género y departamento
        Map<String, Map<String, List<Empleado>>> agrupar = empleados.stream()
                .collect(Collectors.groupingBy(Empleado::getGenero,
                        Collectors.groupingBy(Empleado::getDepartamento)));
        System.out.println("\nAgrupados por Género y Depertamento:");
        agrupar.forEach((genero, departamento) -> {
            System.out.println("Género: " + genero);
            departamento.forEach((dept, lista) -> System.out.println("  Departamento: " + dept + " -> " + lista.size() + " empleados"));
        });

        //3. Top 3 empleados con más antigüedad
        List<Empleado> masAntiguos = empleados.stream()
                .filter(e -> e.getActive())
                .sorted(Comparator.comparing(Empleado::getFechaIng))
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("\nTop 3 empleados con más antigüedad:");
        masAntiguos.forEach(System.out::println);

        //4. Mapa con porcentaje de empleados activos por departamento
        Map<String, Double> porcentajeActivos = empleados.stream()
                .collect(Collectors.groupingBy(Empleado::getDepartamento,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                temporal -> {
                                    long total = temporal.size();
                                    long activos = temporal.stream().filter(Empleado::getActive).count();
                                    return (activos * 100.0) / total;
                                }
                        )
                ));
        System.out.println("\nMapa con porcentaje de empleados activos por departamento:");
        porcentajeActivos.forEach((depto, porcentaje) -> System.out.println("Departamento: " + depto + " -> " + porcentaje + "% activos"));

        // 5. Comparar rendimiento entre .stream() y .parallelStream()
        System.out.println("\nComparación de rendimiento entre .stream() y .parallelStream():");
        List<Empleado> empleados5M = new ArrayList<>();
        String[] departamentos = {"Contabilidad", "Informática", "Talento Humano"};
        for (int i = 0; i < 5_000_000; i++) {
            String nombre = "Empleado" + i;
            String apellido = "Apellido" + i;
            String genero = i % 2 == 0 ? "M" : "F";
            String departamento = departamentos[i % departamentos.length];
            BigDecimal salario = BigDecimal.valueOf(500 + (Math.random() * 1500));
            LocalDate fechaIng = LocalDate.of(2020, 1, 1).plusDays(i % 365);
            empleados5M.add(new Empleado(nombre, apellido, genero, departamento, "Cargo", salario, fechaIng));
        }

        // a. Salario promedio usando stream()
        long inicioStream = System.currentTimeMillis();
        double promedioStream = empleados5M.stream()
                .mapToDouble(e -> e.getSalario().doubleValue())
                .average()
                .orElse(0.0);
        long finStream = System.currentTimeMillis();
        System.out.println("Salario promedio con stream(): " + promedioStream + " - Tiempo: " + (finStream - inicioStream) + " ms");

        // a. Salario promedio usando parallelStream()
        long inicioParallel = System.currentTimeMillis();
        double promedioParallel = empleados5M.parallelStream()
                .mapToDouble(e -> e.getSalario().doubleValue())
                .average()
                .orElse(0.0);
        long finParallel = System.currentTimeMillis();
        System.out.println("Salario promedio con parallelStream(): " + promedioParallel + " - Tiempo: " + (finParallel - inicioParallel) + " ms");

        // b. Cantidad de empleados por departamento usando stream()
        long inicioGroupStream = System.currentTimeMillis();
        Map<String, Long> cantidadPorDeptoStream = empleados5M.stream()
                .collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.counting()));
        long finGroupStream = System.currentTimeMillis();
        System.out.println("\nCantidad por departamento con stream():");
        cantidadPorDeptoStream.forEach((depto, cantidad) -> System.out.println(depto + ": " + cantidad));
        System.out.println("Tiempo: " + (finGroupStream - inicioGroupStream) + " ms");

        // b. Cantidad de empleados por departamento usando parallelStream()
        long inicioGroupParallel = System.currentTimeMillis();
        Map<String, Long> cantidadPorDeptoParallel = empleados5M.parallelStream()
                .collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.counting()));
        long finGroupParallel = System.currentTimeMillis();
        System.out.println("\nCantidad por departamento con parallelStream():");
        cantidadPorDeptoParallel.forEach((depto, cantidad) -> System.out.println(depto + ": " + cantidad));
        System.out.println("Tiempo: " + (finGroupParallel - inicioGroupParallel) + " ms");

    }

    public static void loadEmpleados(List<Empleado> empleadoList){
        empleadoList.add(new Empleado("María", "Rodríguez", "F", "Contabilidad", "Asistente Contable", new BigDecimal(700), LocalDate.parse("2021-04-01")));
        empleadoList.add(new Empleado("Juan", "Gutierrez", "M", "Talento Humano", "Reclutador", new BigDecimal(500), LocalDate.parse("2023-03-11"), LocalDate.parse("2024-04-01"), false));
        empleadoList.add(new Empleado("José", "Albornoz", "M","Contabilidad", "Asistente Contable", new BigDecimal(800), LocalDate.parse("2020-08-15"), LocalDate.parse("2023-05-01"), false));
        empleadoList.add(new Empleado("Julián", "Flores", "M", "Informática", "Soporte TI", new BigDecimal(800), LocalDate.parse("2023-11-01")));
        empleadoList.add(new Empleado("Camila", "Mendoza","F", "Informática", "Desarrollador UI/UX", new BigDecimal(1000), LocalDate.parse("2021-07-08")));
        empleadoList.add(new Empleado("Camilo", "López", "M", "Contabilidad", "Supervisor Contable", new BigDecimal(1500), LocalDate.parse("2020-04-11")));
        empleadoList.add(new Empleado("Manuel", "Játiva", "M", "Contabilidad", "Asistente Contable", new BigDecimal(850), LocalDate.parse("2023-06-03")));
        empleadoList.add(new Empleado("Carlos", "Franco", "M", "Talento Humano", "Reclutador", new BigDecimal(650), LocalDate.parse("2023-01-07"), LocalDate.parse("2024-12-09"), false));
        empleadoList.add(new Empleado("Raúl", "Echeverría", "M", "Informática", "Infraestructura TI", new BigDecimal(950), LocalDate.parse("2020-02-14")));
        empleadoList.add(new Empleado("Estefanía", "Mendoza", "F", "Talento Humano", "Supervisora TH", new BigDecimal(1600), LocalDate.parse("2021-09-21")));
        empleadoList.add(new Empleado("Julie", "Flores", "F", "Informática", "Desarrollador", new BigDecimal(1200), LocalDate.parse("2021-12-10")));
        empleadoList.add(new Empleado("Melissa", "Morocho", "F","Contabilidad", "Asistente Contable", new BigDecimal(820), LocalDate.parse("2022-05-22"), LocalDate.parse("2023-07-09"), false));
        empleadoList.add(new Empleado("Camila", "Mendez", "F", "Contabilidad", "Asistente Cuentas", new BigDecimal(860), LocalDate.parse("2020-10-01")));
        empleadoList.add(new Empleado("José", "Rodríguez", "M","Informática", "Tester QA", new BigDecimal(1100), LocalDate.parse("2021-10-01")));
        empleadoList.add(new Empleado("Esteban", "Gutierrez","M", "Talento Humano", "Reclutador", new BigDecimal(700), LocalDate.parse("2023-04-01")));
        empleadoList.add(new Empleado("María", "López","F", "Contabilidad", "Asistente Contable", new BigDecimal(840), LocalDate.parse("2020-02-20"), LocalDate.parse("2024-07-15"), false));
        empleadoList.add(new Empleado("Cecilia", "Marín","F", "Informática", "Supervisora TI", new BigDecimal(2000), LocalDate.parse("2020-04-21")));
        empleadoList.add(new Empleado("Edison", "Cáceres","M", "Informática", "Desarrollador TI", new BigDecimal(1300), LocalDate.parse("2023-07-07")));
        empleadoList.add(new Empleado("María", "Silva", "F","Contabilidad", "Asistente Contable", new BigDecimal(900), LocalDate.parse("2021-11-15"), LocalDate.parse("2022-08-09"), false));

    }



}