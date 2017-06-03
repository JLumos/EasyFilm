package upm.daw.easyfilm.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Created by Jesus on 19/05/2017.
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

    @OneToOne
    private Pelicula pelicula;


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
