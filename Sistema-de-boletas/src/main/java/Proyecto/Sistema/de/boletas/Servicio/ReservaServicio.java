package Proyecto.Sistema.de.boletas.Servicio;

import Proyecto.Sistema.de.boletas.Entidades.Compra;
import Proyecto.Sistema.de.boletas.Entidades.Evento;
import Proyecto.Sistema.de.boletas.Entidades.MetodoPago;
import Proyecto.Sistema.de.boletas.Entidades.Zona;

import java.util.Map;

public interface ReservaServicio {
    Compra reservar(Long cedula, String nombre, Evento evento, MetodoPago metodoPago, Map<Zona,Integer> zonas);
}