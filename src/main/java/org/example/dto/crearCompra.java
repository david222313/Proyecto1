package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor


public class crearCompra {
    private String nombreEvento;
    private String nombreComprador;
    private MetodoPago metodoPago;
}
