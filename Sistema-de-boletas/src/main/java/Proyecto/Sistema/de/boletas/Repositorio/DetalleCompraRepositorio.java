package Proyecto.Sistema.de.boletas.Repositorio;

import Proyecto.Sistema.de.boletas.Entidades.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCompraRepositorio extends JpaRepository<DetalleCompra, Long> {
}