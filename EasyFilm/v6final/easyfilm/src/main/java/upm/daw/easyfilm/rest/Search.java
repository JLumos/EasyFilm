package upm.daw.easyfilm.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import upm.daw.easyfilm.model.Pelicula;

import java.util.List;

/**
 * <b>Clase Search</b>
 *
 * Se utiliza para mapear el JSON resultado de una consulta a una de las APIs
 * utilizadas en este proyecto. Se compone de una lista de Peliculas.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {
    @JsonProperty("Search")
    private List<Pelicula> search;

    public List<Pelicula> getSearch() {
        return search;
    }

    public void setSearch(List<Pelicula> search) {
        this.search = search;
    }
}
