package Proyecto.Sistema.de.boletas.Servicio;

import Proyecto.Sistema.de.boletas.Entidades.Compra;
import Proyecto.Sistema.de.boletas.Entidades.Evento;
import Proyecto.Sistema.de.boletas.Entidades.MetodoPago;
import Proyecto.Sistema.de.boletas.Entidades.Zona;


import java.util.List; import java.util.Map;

public interface CompraServicio {
    Compra reservarCompra(Long cedula, String nombre, Evento evento, MetodoPago metodoPago, Map<Zona,Integer> zonas);
    Compra registrarPago(Long cedula, String comprobante);
    List<Compra> listarComprasUsuario(Long cedula);
}