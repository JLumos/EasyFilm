package upm.daw.easyfilm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import upm.daw.easyfilm.model.Pelicula;

import javax.transaction.Transactional;
import java.util.List;


/**
 * <b>Interfaz PeliculaRepository</b>
 * Interfaz que se utiliza para realizar consultas y operaciones
 * CRUD sobre Pelicula
 */
public interface PeliculaRepository extends CrudRepository<Pelicula, Long> {

    Pelicula findByNombrePelicula(String nombrePelicula);

    Pelicula findByIdPelicula(Long idPelicula);

    @Query(value = "SELECT * FROM easyfilm.pelicula" , nativeQuery = true)
    List<Pelicula> selectAll();

    @Transactional
    Long deleteByIdPelicula(Long idPelicula);

}
