package upm.daw.easyfilm.repository;

import org.springframework.data.repository.CrudRepository;
import upm.daw.easyfilm.model.Usuario;

import javax.transaction.Transactional;

/**
 * <b>Interfaz UsuarioRepository</b>
 * Interfaz que se utiliza para realizar consultas y operaciones
 * CRUD sobre Usuario
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Usuario findByUser(String user);

    Usuario findByEmail(String email);

    @Transactional
    Long deleteByUser(String user);


}
