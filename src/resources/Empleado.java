package resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Empleado {
    private String nombre;
    private String apellido;
    private String genero;
    private String departamento;
    private String cargo;
    private BigDecimal salario;
    private LocalDate fechaIng;
    private LocalDate fechaSal;
    private Boolean isActive;

    public Empleado(String nombre,
                    String apellido,
                    String genero,
                    String departamento,
                    String cargo,
                    BigDecimal salario,
                    LocalDate fechaIng) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.departamento = departamento;
        this.cargo = cargo;
        this.salario = salario;
        this.fechaIng = fechaIng;
        this.fechaSal = null;
        this.isActive = true;
    }

    public Empleado(String nombre,
                    String apellido,
                    String genero,
                    String departamento,
                    String cargo,
                    BigDecimal salario,
                    LocalDate fechaIng,
                    LocalDate fechaSal,
                    Boolean isActive) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.departamento = departamento;
        this.cargo = cargo;
        this.salario = salario;
        this.fechaIng = fechaIng;
        this.fechaSal = fechaSal;
        this.isActive = isActive;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getGenero() {
        return genero;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getCargo() {
        return cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public LocalDate getFechaIng() {
        return fechaIng;
    }

    public LocalDate getFechaSal() {
        return fechaSal;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setFechaSal(LocalDate fechaSal) {
        this.fechaSal = fechaSal;
    }

    @Override
    public String toString() {
        return "{" +
                "nombre: '" + nombre + '\'' +
                ", apellido: '" + apellido + '\'' +
                ", genero: '" + genero + '\'' +
                ", departamento: '" + departamento + '\'' +
                ", cargo: '" + cargo + '\'' +
                ", salario: " + salario +
                ", fechaIng: " + fechaIng +
                ", fechaSal: " + fechaSal +
                ", isActive: " + isActive +
                '}'+"\n";
    }
}


