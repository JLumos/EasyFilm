package upm.daw.easyfilm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import upm.daw.easyfilm.model.Pelicula;

import javax.transaction.Transactional;
import java.util.List;


public interface PeliculaRepository extends CrudRepository<Pelicula, Long> {

    Pelicula findByNombrePelicula(String nombrePelicula);
    Pelicula findByIdPelicula(Long idPelicula);

    @Query(value = "SELECT * FROM Pelicula" , nativeQuery = true)
    List<Pelicula> selectAll();

    @Transactional
    Long deleteByIdPelicula(Long idPelicula);

}
