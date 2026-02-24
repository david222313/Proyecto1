package org.example.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor


public class Comprador {
    private String nombre;
    private String documento;
    private String correo;
    private String telefono;
}
