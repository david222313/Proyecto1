package Proyecto.Sistema.de.boletas.Servicio;

import Proyecto.Sistema.de.boletas.Entidades.Evento;
import java.util.List;

public interface EventoServicio {

    Evento guardar(Evento evento);

    List<Evento> listarEventos();
}