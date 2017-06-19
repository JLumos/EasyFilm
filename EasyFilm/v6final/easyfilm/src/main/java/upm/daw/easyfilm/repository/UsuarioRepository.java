package upm.daw.easyfilm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import upm.daw.easyfilm.model.Pelicula;
import upm.daw.easyfilm.model.Usuario;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <b>Interfaz UsuarioRepository</b>
 * Interfaz que se utiliza para realizar consultas y operaciones
 * CRUD sobre Usuario
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Usuario findByUser(String user);

    Usuario findByEmail(String email);

    @Query(value = "SELECT * FROM easyfilm.usuario" , nativeQuery = true)
    List<Usuario> selectAll();

    @Query(value = "SELECT * FROM easyfilm.usuario WHERE easyfilm.usuario.is_admin = 'yes'", nativeQuery = true)
    List<Usuario> selectAdmins();

    @Transactional
    Long deleteByUser(String user);


}
