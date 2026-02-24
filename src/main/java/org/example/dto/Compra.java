package org.example.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor


public class Compra {
    private String nombreEvento;
    private String nombreComprador;
    private double precio;
    private MetodoPago metodoPago;
    private EstadoCompra estadoCompra;
    private LocalDateTime fechaReserva;
    private String numeroComprobante;
    private Map<Zona, Integer> boletasPorZona;
}
