package upm.daw.easyfilm.repository;

import org.springframework.data.repository.CrudRepository;
import upm.daw.easyfilm.model.Opinion;
import upm.daw.easyfilm.model.Usuario;

import javax.transaction.Transactional;

/**
 * <b>Interfaz OpinionRepository</b>
 * Interfaz que se utiliza para realizar consultas y operaciones
 * CRUD sobre Opinion
 */
public interface OpinionRepository extends CrudRepository<Opinion, Long> {


}
