package Proyecto.Sistema.de.boletas.servicioimpl;

import Proyecto.Sistema.de.boletas.Entidades.Evento;
import Proyecto.Sistema.de.boletas.Repositorio.EventoRepositorio;
import Proyecto.Sistema.de.boletas.Servicio.EventoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoServicioImpl implements EventoServicio {

    private final EventoRepositorio eventoRepository;

    @Override
    public Evento guardar(Evento evento) {

        int sumaZonas = evento.getBoletasZonaA()
                + evento.getBoletasZonaB()
                + evento.getBoletasZonaC();

        if (sumaZonas > evento.getTotalBoletas()) {
            throw new RuntimeException("Error: zonas exceden total boletas");
        }

        return eventoRepository.save(evento);
    }

    @Override
    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }
}
