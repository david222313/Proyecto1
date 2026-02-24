package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Evento {

    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;
    private String lugar;
    private String patrocinador;
    private Map<Zona, Integer> boletasDisponibles = new HashMap<>();

    public Evento(String nombre, LocalDate fecha, LocalTime hora, String lugar,
                  String patrocinador, int cantA, int cantB, int cantC) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.patrocinador = patrocinador;
        boletasDisponibles.put(Zona.A, cantA);
        boletasDisponibles.put(Zona.B, cantB);
        boletasDisponibles.put(Zona.C, cantC);
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getLugar() {
        return lugar;
    }

    public String getPatrocinador() {
        return patrocinador;
    }

    public int getBoletasDisponibles(Zona zona) {
        return boletasDisponibles.getOrDefault(zona, 0);
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }

    // Métodos de negocio
    public boolean reservarBoletas(Zona zona, int cantidad) {
        int disponibles = getBoletasDisponibles(zona);
        if (cantidad <= 0 || cantidad > disponibles) {
            return false;
        }
        boletasDisponibles.put(zona, disponibles - cantidad);
        return true;
    }

    public void liberarBoletas(Zona zona, int cantidad) {
        int actuales = getBoletasDisponibles(zona);
        boletasDisponibles.put(zona, actuales + cantidad);
    }

    @Override
    public String toString() {
        return "Evento: " + nombre + "\n" +
                "Fecha: " + fecha + " " + hora + "\n" +
                "Lugar: " + lugar + "\n" +
                "Patrocinador: " + patrocinador + "\n" +
                "Boletas restantes: A=" + getBoletasDisponibles(Zona.A) +
                ", B=" + getBoletasDisponibles(Zona.B) +
                ", C=" + getBoletasDisponibles(Zona.C);
    }
}
