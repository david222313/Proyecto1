package Proyecto.Sistema.de.boletas.Servicio;

import Proyecto.Sistema.de.boletas.Entidades.Compra;

import java.util.List;

public interface ReporteServicio {
    List<Compra> listarComprasPorCedula(Long cedula);
}