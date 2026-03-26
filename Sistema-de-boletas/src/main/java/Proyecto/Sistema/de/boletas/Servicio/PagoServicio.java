package Proyecto.Sistema.de.boletas.Servicio;

import Proyecto.Sistema.de.boletas.Entidades.Compra;

public interface PagoServicio {
    Compra registrarPago(Long cedula, String comprobante);
}