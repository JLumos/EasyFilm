package upm.daw.easyfilm.repository;

import org.springframework.data.repository.CrudRepository;
import upm.daw.easyfilm.model.Opinion;
import upm.daw.easyfilm.model.Usuario;

import javax.transaction.Transactional;

/**
 * Created by Jesus on 02/06/2017.
 */
public interface OpinionRepository extends CrudRepository<Opinion, Long> {


}
