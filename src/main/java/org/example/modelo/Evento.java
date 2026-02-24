package org.example.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private Map<Zona, Integer> boletasPorZona;

}
