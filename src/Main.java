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

        //To-do: Filtrar empleados por un atributo: departamento
        Predicate<Empleado> itDept = e -> "Informática".equalsIgnoreCase(e.getDepartamento());

        //To-do: Ordenar empleados por un atributo: Nombre
        Comparator<Empleado> porNombre = Comparator.comparing(Empleado::getNombre);

        //To-do: Generar un mapa que me permita tener como clave los departamentos y como valor el total de empleados por departamento
        Function<List<Empleado>, Map<String, Integer>> deptCount = list -> {
            Map<String, Integer> map = new HashMap<>();
            for (Empleado e : list) {
                map.merge(e.getDepartamento(), 1, Integer::sum);
            }
            return map;
        };
        //Con stream
        Function<List<Empleado>, Map<String, Integer>> deptCountS = list ->
                list.stream()
                        .collect(Collectors.groupingBy(
                                Empleado::getDepartamento,
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        Long::intValue
                                )
                ));


        //To-do: Mostrar empleados por un consumer: Contratados en determinado mes
        Consumer<Empleado> showIfJanuary = e -> {
            if (e.getFechaIng().getMonth() == Month.JANUARY) {
                System.out.println(e);
            }
        };

        //Con stream
        Consumer<Empleado> show = System.out::println;


        //Uso de las funciones
        //1. Predicate - Sin stream
        System.out.println("Predicate result");
        List<Empleado> itEmployees = new ArrayList<>();
        for (Empleado e : empleados) {
            if (itDept.test(e)) {
                itEmployees.add(e);
            }
        }
        System.out.println(itEmployees);
        //1. Predicate - Con stream
        List<Empleado> itEmpleados = empleados.stream()
                .filter(itDept)
                .toList();
        System.out.println(itEmpleados);


        //2. Comparator - Sin stream
        System.out.println("Comparator result");
        List<Empleado> orderEmpleados = new ArrayList<>(List.copyOf(empleados));
        orderEmpleados.sort(porNombre);
        System.out.println(orderEmpleados);
        //2. Comparator - Con stream
        List<Empleado> sorted = empleados.stream()
                .sorted(porNombre)
                .toList();
        System.out.println(sorted);
        //sorted.forEach(System.out::println);

        //3. Function - Sin stream
        System.out.println("Function result");
        Map<String, Integer> totalPorDept = deptCount.apply(empleados);
        //3. Function - Con stream
        Map<String, Integer> totalPorDeptS = deptCountS.apply(empleados);
        System.out.println(totalPorDept);

        //4. Consumer - Sin stream
        System.out.println("Consumer result");
        for (Empleado e : empleados) {
            showIfJanuary.accept(e);
        }
        //4. Consumer - Con stream
        empleados.stream()
                .filter(e -> e.getFechaIng().getMonth() ==  Month.JANUARY)
                .forEach(show);

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