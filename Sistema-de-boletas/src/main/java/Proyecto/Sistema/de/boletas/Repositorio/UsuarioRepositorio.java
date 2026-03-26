package Proyecto.Sistema.de.boletas.Repositorio;

import Proyecto.Sistema.de.boletas.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario findByCedula(Long cedula);
}