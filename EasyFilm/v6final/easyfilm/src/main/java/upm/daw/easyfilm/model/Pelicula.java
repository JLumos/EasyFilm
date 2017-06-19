package upm.daw.easyfilm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <b>Clase Pelicula</b>
 * Esta clase guarda la informacion de interes de las peliculas que hay en el
 * sistema. Ademas es usada para transformar codigos JSON obtenidos en las
 * consultas a APIs a objetos Java.
 * El titulo de una pelicula no debe ser vacio
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pelicula {

    public Pelicula () {}

    public Pelicula (String nombrePelicula, String url_trailer) {

        this.nombrePelicula = nombrePelicula;
        this.url_trailer = url_trailer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPelicula;

    @NotBlank
    @JsonProperty("Title")
    private String nombrePelicula;

    @JsonProperty("key")
    private String url_trailer;

    @JsonProperty("Plot")
    @Lob
    private String descripcion;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Poster")
    private String url_portada;

    @JsonProperty("imdbRating")
    private String valoracion;

    @JsonProperty("imdbID")
    private String imdbId;

    @JsonProperty("tv_results[0].id")
    private String tmdbId;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Actors")
    private String actors;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Opinion> opiniones;

    @ElementCollection(fetch = FetchType.EAGER)
    public List<Opinion> getOpiniones() {
        return opiniones;
    }

    public void setOpiniones(List<Opinion> opiniones) {
        this.opiniones = opiniones;
    }

    public void addOpinion (Opinion op) {
        this.opiniones.add(op);
    }

    /**
     * <b>Metodo contiene</b>
     * Indica si una opinion esta contenida en una lista de opiniones
     * @param list - Lista de opiniones
     * @param p - Opinion a buscar
     * @return true si p esta en List y false e.o.c
     */
    public boolean contiene (List<Opinion> list, Opinion p)
    {
        Iterator<Opinion> it = opiniones.iterator();
        Opinion x;
        while (it.hasNext())
        {
            x = it.next();
            if (x.getTexto().equals(p.getTexto()))
                return true;
        }
        return false;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(String tmdbId) {
        this.tmdbId = tmdbId;
    }


    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }


    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public String getUrl_trailer() {
        return url_trailer;
    }

    public void setUrl_trailer(String url_trailer) {
        this.url_trailer = url_trailer;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getUrl_portada() {
        return url_portada;
    }

    public void setUrl_portada(String url_portada) {
        this.url_portada = url_portada;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }
}
