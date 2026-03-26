package Proyecto.Sistema.de.boletas.Repositorio;

import Proyecto.Sistema.de.boletas.Entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepositorio extends JpaRepository<Evento, Long> {
}