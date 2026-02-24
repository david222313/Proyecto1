package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.example.modelo.MetodoPago;

@Getter
@Setter
@AllArgsConstructor


public class crearCompra {
    private String nombreEvento;
    private String nombreComprador;
    private MetodoPago metodoPago;
}
