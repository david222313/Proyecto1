package Proyecto.Sistema.de.boletas.servicioimpl;

import Proyecto.Sistema.de.boletas.Entidades.Compra;
import Proyecto.Sistema.de.boletas.Entidades.EstadoCompra;
import Proyecto.Sistema.de.boletas.Repositorio.CompraRepositorio;
import Proyecto.Sistema.de.boletas.Servicio.PagoServicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoServicioImpl implements PagoServicio {

    private final CompraRepositorio compraRepository;

    @Override
    public Compra registrarPago(Long cedula,
                                String comprobante) {

        List<Compra> compras =
                compraRepository
                        .findByUsuarioCedulaOrderByFechaCompraDesc(
                                cedula);

        if(compras.isEmpty())
            throw new RuntimeException("Compra no encontrada");

        Compra compra = compras.get(0);

        if(LocalDateTime.now()
                .isAfter(compra.getFechaLimitePago())) {

            compra.setEstado(EstadoCompra.CANCELADA);

            return compraRepository.save(compra);
        }

        compra.setEstado(EstadoCompra.PAGADA);

        compra.setComprobantePago(comprobante);

        return compraRepository.save(compra);
    }
}