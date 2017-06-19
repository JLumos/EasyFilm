package upm.daw.easyfilm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import upm.daw.easyfilm.model.Pelicula;
import upm.daw.easyfilm.model.Usuario;
import upm.daw.easyfilm.repository.PeliculaRepository;
import upm.daw.easyfilm.repository.UsuarioRepository;

/**
 * <b>ComunOperations</b>
 * Esta clase contiene operaciones comunes a
 * varios controllers
 */
@Service
public class ComunOperations {

    @Autowired
    UsuarioRepository usuarios;
    @Autowired
    PeliculaRepository peliculas;

    ComunOperations () {}

    /**
     * <b>Metodo currentPrincipalName</b>
     * @return Nombre de usuario del usuario registrado actualmente
     */
    protected String currentPrincipalName() {return SecurityContextHolder.getContext().getAuthentication().getName();}

    /**
     * <b>Metodo addControlAtributes</b>
     * Inserta en las vistas los parametros que indican
     * quien es el usuario y si es o no administrador
     * El metodo distingue si recibe un Model o un ModelAndView
     * @param model
     * @param user
     */
    protected void addControlAtributesToMV(Object model, String user) {
        Usuario actual = usuarios.findByUser(user);
        if (model instanceof Model) {
            ((Model) model).addAttribute("nombreUsuario", actual.getIsAdmin().equals("yes") ? "admin" : "user");
            ((Model) model).addAttribute("active", user);
        }
        else if (model instanceof ModelAndView) {
            ((ModelAndView) model).addObject("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
            ((ModelAndView) model).addObject("active",user);
        }
    }

    /**
     * <b>Metodo addMovieAttributesToMV</b>
     * Inserta en las vistas los parametros que indican
     * los datos de las peliculas
     * @param model
     * @param name
     */
    protected void addAllMovieAtributesToMV(Object model, String name) {
        Pelicula peli = peliculas.findByIdPelicula(Long.parseLong(name));
        addAllMovieAtributesToMV(model,peli);
    }

    /**
     * <b>Metodo addMovieAttributesToMV</b>
     * Inserta en las vistas los parametros que indican
     * los datos de las peliculas
     * El metodo distingue si recibe un Model o un ModelAndView
     * @param model
     * @param peli
     */
    protected void addAllMovieAtributesToMV(Object model, Pelicula peli) {
        if (model instanceof Model) {
            ((Model) model).addAttribute("id", peli.getIdPelicula());
            ((Model) model).addAttribute("nombrePelicula", peli.getNombrePelicula());
            ((Model) model).addAttribute("plot", peli.getDescripcion());
            ((Model) model).addAttribute("year", peli.getYear());
            ((Model) model).addAttribute("director", peli.getDirector());
            ((Model) model).addAttribute("actors", peli.getActors());
            ((Model) model).addAttribute("trailer", peli.getUrl_trailer());
            ((Model) model).addAttribute("url_portada", peli.getUrl_portada());
        }
        else if (model instanceof ModelAndView) {
            ((ModelAndView) model).addObject("id", peli.getIdPelicula());
            ((ModelAndView) model).addObject("nombrePelicula", peli.getNombrePelicula());
            ((ModelAndView) model).addObject("plot", peli.getDescripcion());
            ((ModelAndView) model).addObject("year", peli.getYear());
            ((ModelAndView) model).addObject("director", peli.getDirector());
            ((ModelAndView) model).addObject("actors", peli.getActors());
            ((ModelAndView) model).addObject("trailer", peli.getUrl_trailer());
            ((ModelAndView) model).addObject("url_portada", peli.getUrl_portada());
        }
    }


    /**
     * <b>Metodo addSomeMovieAttributesToMV</b>
     * Inserta en las vistas los parametros que indican
     * los datos de las peliculas, pero no todos.
     * @param model
     * @param peli
     */
    protected void addSomeMovieAttributesToMV(Object model, Pelicula peli) {
        if (model instanceof ModelAndView) {
            ((ModelAndView) model).addObject("plot", peli.getDescripcion());
            ((ModelAndView) model).addObject("year", peli.getYear());
            ((ModelAndView) model).addObject("director", peli.getDirector());
            ((ModelAndView) model).addObject("actors", peli.getActors());
            ((ModelAndView) model).addObject("rated",peli.getValoracion());
            ((ModelAndView) model).addObject("comentarios",peli.getOpiniones());
            ((ModelAndView) model).addObject("trailer",peli.getUrl_trailer());
        }
    }
    /**
     * <b>Metodo addUserAttributesToMV</b>
     * Inserta en las vistas los parametros que indican
     * los datos de los usuarios
     * El metodo distingue si recibe un Model o un ModelAndView
     * @param model
     * @param user
     */
    protected void addUserAtributesToMV(Object model, Usuario user) {
        if (model instanceof Model) {
            ((Model) model).addAttribute("user", user.getUser());
            ((Model) model).addAttribute("email", user.getEmail());
            ((Model) model).addAttribute("nombre", user.getNombre());
            ((Model) model).addAttribute("isAdmin",user.getIsAdmin());
            ((Model) model).addAttribute("estado",user.getEstado());
        }
        else if (model instanceof ModelAndView) {
            ((ModelAndView) model).addObject("user", user.getUser());
            ((ModelAndView) model).addObject("email", user.getEmail());
            ((ModelAndView) model).addObject("nombre", user.getNombre());
            ((ModelAndView) model).addObject("isAdmin",user.getIsAdmin());
            ((ModelAndView) model).addObject("estado",user.getEstado());
        }
    }


}
