package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor


public class ConfirmarPago {
    private String idCompra;
    private String numeroComprobante;

}
