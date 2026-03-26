package Proyecto.Sistema.de.boletas.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private LocalDate fecha;

    private LocalTime hora;

    private String lugar;

    private String patrocinador;

    private Integer totalBoletas;

    private Integer boletasZonaA;

    private Integer boletasZonaB;

    private Integer boletasZonaC;
}