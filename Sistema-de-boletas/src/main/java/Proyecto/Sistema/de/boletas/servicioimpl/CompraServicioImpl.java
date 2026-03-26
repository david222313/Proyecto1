package Proyecto.Sistema.de.boletas.servicioimpl;

import Proyecto.Sistema.de.boletas.Entidades.*;
import Proyecto.Sistema.de.boletas.Repositorio.CompraRepositorio;
import Proyecto.Sistema.de.boletas.Repositorio.DetalleCompraRepositorio;
import Proyecto.Sistema.de.boletas.Repositorio.UsuarioRepositorio;
import Proyecto.Sistema.de.boletas.Servicio.CompraServicio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class CompraServicioImpl implements CompraServicio {

    private final CompraRepositorio compraRepository;

    private final DetalleCompraRepositorio detalleCompraRepository;

    private final UsuarioRepositorio usuarioRepository;


    @Value("${reserva.tiempo-horas:24}")
    private int horasReserva;


    @Override
    public Compra reservarCompra(Long cedula, String nombre, Evento evento, MetodoPago metodoPago, Map<Zona, Integer> zonas) {

        int totalBoletas = zonas.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (totalBoletas > 10)
            throw new RuntimeException("Máximo permitido 10 boletas");


        Usuario usuario = usuarioRepository.findByCedula(cedula);

        if (usuario == null) {

            usuario = Usuario.builder()
                    .cedula(cedula)
                    .nombre(nombre)
                    .build();

            usuario = usuarioRepository.save(usuario);
        }


        double total = zonas.entrySet()
                .stream()
                .mapToDouble(z ->
                        z.getKey().getPrecio() * z.getValue())
                .sum();


        Compra compra = Compra.builder()
                .usuario(usuario)
                .evento(evento)
                .estado(EstadoCompra.RESERVADA)
                .metodoPago(metodoPago)
                .valorTotal(total)
                .fechaCompra(LocalDateTime.now())
                .fechaLimitePago(
                        LocalDateTime.now()
                                .plusHours(horasReserva))
                .build();


        compra = compraRepository.save(compra);


        Compra finalCompra = compra;

        zonas.forEach((zona, cantidad) -> {

            if (cantidad > 0) {

                detalleCompraRepository.save(

                        DetalleCompra.builder()
                                .compra(finalCompra)
                                .zona(zona)
                                .cantidad(cantidad)
                                .precioUnitario((double) zona.getPrecio())
                                .subtotal((double) (zona.getPrecio() * cantidad))
                                .build()
                );
            }
        });

        return compra;
    }


    @Override
    public Compra registrarPago(Long cedula,
                                String comprobante) {

        List<Compra> compras =
                compraRepository
                        .findByUsuarioCedulaOrderByFechaCompraDesc(
                                cedula);

        if (compras.isEmpty())
            throw new RuntimeException("No existen compras");


        Compra compra = compras.get(0);


        if (compra.getEstado() == EstadoCompra.PAGADA)
            throw new RuntimeException("Compra ya pagada");


        if (LocalDateTime.now()
                .isAfter(compra.getFechaLimitePago())) {

            compra.setEstado(EstadoCompra.CANCELADA);

            compraRepository.save(compra);

            throw new RuntimeException("Reserva vencida");
        }


        compra.setEstado(EstadoCompra.PAGADA);

        compra.setComprobantePago(comprobante);


        return compraRepository.save(compra);
    }


    @Override
    public List<Compra> listarComprasUsuario(Long cedula) {

        return compraRepository
                .findByUsuarioCedulaOrderByFechaCompraDesc(
                        cedula);
    }
}