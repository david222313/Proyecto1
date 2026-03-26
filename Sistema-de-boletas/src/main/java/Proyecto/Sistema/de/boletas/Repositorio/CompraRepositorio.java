package Proyecto.Sistema.de.boletas.Repositorio;

import Proyecto.Sistema.de.boletas.Entidades.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepositorio extends JpaRepository<Compra, Long> {

    List<Compra> findByUsuarioCedulaOrderByFechaCompraDesc(Long cedula);
}