package upm.daw.easyfilm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import upm.daw.easyfilm.model.Usuario;

import javax.transaction.Transactional;
import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Usuario findByUser(String user);
    Usuario findByEmail(String email);


    @Transactional
    Long deleteByUser(String user);


}
