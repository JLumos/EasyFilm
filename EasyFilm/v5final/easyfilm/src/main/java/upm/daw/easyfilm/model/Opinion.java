package upm.daw.easyfilm.model;

import javax.persistence.*;

/**
 * <b>Clase Opinion</b>
 * Contiene la informacion de interes de las opiniones de las peliculas
 * del sistema. Estan relacionadas con peliculas y con usuarios, de modo
 * que cada usuario pueda tener varias opiniones (de la misma o distintas
 * peliculas) y cada pelicula pueda tener varias opiniones (del mismo o
 * distintos usuarios) Esta entidad es por tanto asociativa ya que nace
 * de una relacion M:N entre las (tablas) clases Pelicula y Usuario.
 */
@Entity
public class Opinion {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOpinion;

    @Lob
    private String texto;

    @OneToOne
    private Usuario autor;

    @OneToOne
    private Pelicula pelicula;

    public Long getIdOpinion() {
        return idOpinion;
    }

    public void setIdOpinion(Long idOpinion) {
        this.idOpinion = idOpinion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
}
