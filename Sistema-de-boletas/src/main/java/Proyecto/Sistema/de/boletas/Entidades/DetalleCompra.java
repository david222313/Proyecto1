package Proyecto.Sistema.de.boletas.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Compra compra;

    @Enumerated(EnumType.STRING)
    private Zona zona;

    private Integer cantidad;

    private Double precioUnitario;

    private Double subtotal;
}