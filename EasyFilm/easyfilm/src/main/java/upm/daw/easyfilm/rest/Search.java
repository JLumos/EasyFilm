package upm.daw.easyfilm.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import upm.daw.easyfilm.model.Pelicula;

import java.util.List;

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
