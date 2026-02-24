package org.example.dto;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.example.modelo.EstadoCompra;
import org.example.modelo.MetodoPago;
import org.example.modelo.Zona;

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
