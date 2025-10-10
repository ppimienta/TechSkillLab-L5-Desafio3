package streams;

import resources.Empleado;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AdvancedStream {
    public static void main(String[] args) {
        //Analítica avanzada de empleados
        List<Empleado> empleados = new ArrayList<>();
        loadEmpleados(empleados);

        //Top 5 empleados activos con mayor salario
        Comparator<Empleado> porSalario = Comparator.comparing(empleado -> empleado.getSalario());

        System.out.println("Resultado Top 5");
        empleados.stream()
                .filter(empleado -> empleado.getActive())
                .sorted(porSalario.reversed())
                .limit(5)
                .forEach(System.out::println);
        //System.out.println(empleados);

        //Promedio de salario por departamento (usando groupingBy)
        System.out.println("Resultado prom x Dept");
        var promXDept = empleados.stream()
                .collect(Collectors.groupingBy(
                        Empleado::getDepartamento,
                        Collectors.averagingDouble(empleado -> empleado.getSalario().doubleValue())
                ));
        System.out.println(promXDept);

        System.out.println("Estadísticas del salario");
        //Estadísticas generales de salario (usando summarizingDouble)
        var stats = empleados.stream()
                .collect(Collectors.summarizingDouble(emp -> emp.getSalario().doubleValue()));
        System.out.println(stats);

        System.out.println("Empleados activos e inactivos");

        //Partición de empleados activos e inactivos
        var classif = empleados.stream()
                .collect(Collectors.partitioningBy(Empleado::getActive));

        var classif2 = empleados.stream()
                .collect(Collectors.partitioningBy(empleado -> empleado.getSalario().doubleValue() > 600));

        System.out.println(classif);
        //ParallelStream

        System.out.println("Composiciones avanzadas");
        //Encadenamiento avanzado: Composiciones avanzadas
        empleados.stream()
                .filter(empleado -> empleado.getGenero().equals("M"))
                .peek(empleado -> System.out.println("Procesando empleado: " + empleado.getNombre() + empleado.getApellido()))
                .sorted(Comparator.comparing(Empleado::getFechaIng))
                .map(empleado -> empleado.getNombre() + " - " + empleado.getDepartamento())
                .forEach(System.out::println);

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
