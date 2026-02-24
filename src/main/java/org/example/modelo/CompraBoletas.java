package org.example.modelo;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor


public class CompraBoletas {
    private Evento evento;
    private Comprador comprador;
    private Map<Zona, Integer> boletasPorZona;
    private double valorTotal;
    private MetodoPago metodoPago;
    private EstadoCompra estadoCompra;
    private LocalDateTime fechaReserva;
    private String numeroComprobante;
}
