package org.example.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class Evento {
    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;
    private String lugar;
    private String patrocinador;

    // Este es el mapa que guarda las boletas por zona
    private Map<Zona, Integer> boletasPorZona;

    // Constructor que SÍ inicializa el mapa con las cantidades
    public Evento(String nombre, LocalDate fecha, LocalTime hora, String lugar, String patrocinador, int cantA, int cantB, int cantC) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.patrocinador = patrocinador;

        // Inicializamos el mapa aquí
        this.boletasPorZona = new HashMap<>();
        this.boletasPorZona.put(Zona.A, cantA);
        this.boletasPorZona.put(Zona.B, cantB);
        this.boletasPorZona.put(Zona.C, cantC);
    }

    // Método para saber cuántas boletas quedan en una zona
    public int getBoletasDisponibles(Zona zona) {
        return boletasPorZona.getOrDefault(zona, 0);
    }

    // Método para reservar boletas (reduce el stock si hay suficientes)
    public boolean reservarBoletas(Zona zona, int cantidad) {
        int disponibles = getBoletasDisponibles(zona);

        if (cantidad <= 0 || cantidad > disponibles) {
            return false; // No se puede reservar
        }

        // Reduce el número de boletas
        boletasPorZona.put(zona, disponibles - cantidad);
        return true; // Reserva exitosa
    }
}