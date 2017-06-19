package upm.daw.easyfilm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import upm.daw.easyfilm.model.Opinion;
import upm.daw.easyfilm.model.Pelicula;
import upm.daw.easyfilm.model.Usuario;
import upm.daw.easyfilm.repository.OpinionRepository;
import upm.daw.easyfilm.repository.PeliculaRepository;
import upm.daw.easyfilm.repository.UsuarioRepository;
import upm.daw.easyfilm.rest.RestClient;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * <b>MovieController</b>
 * Este controlador gestiona la logica correspondiente a las operaciones en
 * las que intervengan las busquedas de peliculas, gestion de comentarios,
 * adicion de favoritos...
 */
@RestController
public class MovieController {

    @Autowired
    private RestClient restClient;
    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private ComunOperations operations;

    /**
     * <b>getPeliculas</b>
     * POST de getPeliculas
     * @param pelic
     * @param result
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/movies", method = RequestMethod.POST)
    public ModelAndView getPeliculas (@Valid Pelicula pelic, BindingResult result) throws IOException {

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:movies?search="+pelic.getNombrePelicula());
        model.addObject("pelicula", new Pelicula());
        String currentPrincipalName = operations.currentPrincipalName();
        operations.addControlAtributesToMV(model,currentPrincipalName);
        return model;
    }


    /**
     * <b>getPeliculas</b>
     * Este metodo se encarga de buscar varias peliculas que se ajusten al nombre dado
     * como argumento. Este metodo utiliza un cliente rest contra la API de OMDB para
     * obtener peliculas cuyo nombre se ajuste al proporcionado. Se devuelve una lista
     * de peliculas al cliente.
     * @param nombre
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/movies", method = RequestMethod.GET)
    public ModelAndView getPeliculas (@RequestParam("search") String nombre) throws IOException {

        ModelAndView model = new ModelAndView();

        if (nombre.length()==0) {
            String currentPrincipalName = operations.currentPrincipalName();
            operations.addControlAtributesToMV(model,currentPrincipalName);
            model.setViewName("home");
            model.addObject("pelicula", new Pelicula());
        }
        else {

            //En el cliente las peliculas se muestran de dos en dos
            //Por lo que lo que se devuelve es una lista de listas de dos peliculas
            List<List<Pelicula>> total = new LinkedList<List<Pelicula>>();
            List<Pelicula> pelis = restClient.getPeliculas(nombre);
            //Se utiliza un mapa para no agregar a la lista peliculas con mismo nombre.
            Map<String, String> map = new HashMap<String, String>();

            if (pelis != null)
            {
                model.addObject("hayPelis", true);
                Iterator<Pelicula> it = pelis.iterator();
                Pelicula peli;
                List<Pelicula> pelis2 = new LinkedList<Pelicula>();
                int count = 0;
                while (it.hasNext()) {
                    peli = it.next();
                    //Se descartan todas aquellas peliculas que no tengan portada o que esten repetidas
                    if (!peli.getUrl_portada().equals("N/A") && !map.containsKey(peli.getNombrePelicula())) {
                        pelis2.add(peli);
                        map.put(peli.getNombrePelicula(), null);
                        count++;
                        if (count == 2 || !it.hasNext()) {
                            count = 0;
                            total.add(pelis2);
                            pelis2 = new LinkedList<Pelicula>();
                        }
                    }
                }
                String currentPrincipalName = operations.currentPrincipalName();
                operations.addControlAtributesToMV(model,currentPrincipalName);

                if (total.size() > 0) {
                    model.addObject("total", total);
                } else model.addObject("hayPelis", false);
                model.addObject("pelicula", new Pelicula());
            }
            else {
                model.addObject("pelicula", new Pelicula());
                model.addObject("hayPelis", false);
            }
        }
        return model;
    }

    /**
     * <b>getPelicula</b>
     * Obtiene una pelicula utilizando el cliente REST (que internamente busca
     * primero en la base de datos, y si no encuentra nada, usa la API)
     * @param movie - Nombre la pelicula que se desea
     * @return
     * @throws IOException
     */
    @RequestMapping("/movie")
    public ModelAndView getPelicula (@RequestParam("movie")String movie) throws IOException {

        ModelAndView model = new ModelAndView();
        model.setViewName("movie");
        String currentPrincipalName = operations.currentPrincipalName();
        operations.addControlAtributesToMV(model,currentPrincipalName);

        Pelicula peli =  restClient.getPelicula(movie);
        operations.addSomeMovieAttributesToMV(model,peli);
        model.addObject("title",peli.getNombrePelicula());

        if (peli.getUrl_trailer() == null || peli.getUrl_trailer().length()==0)
            model.addObject("hayTrailer",false);
        else model.addObject("hayTrailer",true);

        //Se mira si la pelicula es una de las favoritas del usuario
        if(usuarioRepository.findByUser(currentPrincipalName).contiene(usuarioRepository.findByUser(currentPrincipalName).getFavoritas(),peli))
            model.addObject("isFav",true);
        else model.addObject("isFav",false);

        model.addObject("pelicula",new Pelicula());
        model.addObject("comentario", new Opinion());
        return model;
    }


    /**
     * <b>Metodo fav</b>
     * Este metodo guarda como favorita una pelicula
     * @param name
     * @return
     */
    @RequestMapping("/fav")
    public ModelAndView setFav (@RequestParam("name") String name) {

        String currentPrincipalName = operations.currentPrincipalName();
        Pelicula fav = peliculaRepository.findByNombrePelicula(name);
        Usuario user = usuarioRepository.findByUser(currentPrincipalName);
        ModelAndView model = new ModelAndView();

        if(!user.contiene(user.getFavoritas(),fav)) {
            user.addFavorita(fav);
            usuarioRepository.save(user);
            model.addObject("isFav",true);
        }

        model.setViewName("redirect:/movie?movie="+name);
        operations.addControlAtributesToMV(model,currentPrincipalName);

        model.addObject("title",fav.getNombrePelicula());
        operations.addSomeMovieAttributesToMV(model,fav);

        if (fav.getUrl_trailer() == null || fav.getUrl_trailer().length()==0)
            model.addObject("hayTrailer",false);
        else model.addObject("hayTrailer",true);

        model.addObject("pelicula",new Pelicula());
        model.addObject("comentario", new Opinion());
        return model;
    }


    /**
     * <b>Metodo unFav</b>
     * Para eliminar una pelicula de la lista de favoritos de alguien
     * @param name
     * @return
     */
    @RequestMapping("/unfav")
    public ModelAndView unFav (@RequestParam("name") String name, @RequestParam("src") String src) {

        String currentPrincipalName = operations.currentPrincipalName();
        Pelicula fav = peliculaRepository.findByNombrePelicula(name);
        Usuario user = usuarioRepository.findByUser(currentPrincipalName);

        ModelAndView model = new ModelAndView();
        if (src.equals("movie"))
            model.setViewName("redirect:/movie?movie="+name);
        else model.setViewName("redirect:/favs");

        operations.addControlAtributesToMV(model,currentPrincipalName);

        if (user.contiene(user.getFavoritas(),fav)) {
            user.removeFavorita(fav);
            usuarioRepository.save(user);
        }

        model.addObject("pelicula", new Pelicula());
        return model;
    }


    /**
     * <b>Metodo showFavs</b>
     * Para mostrar todos los favoritos activos del usuario
     * @return
     */
    @RequestMapping("/favs")
    public ModelAndView showFavs() {

        String currentPrincipalName = operations.currentPrincipalName();
        ModelAndView model = new ModelAndView();
        model.setViewName("favs");
        model.addObject("pelicula",new Pelicula());
        Usuario user = usuarioRepository.findByUser(currentPrincipalName);
        operations.addControlAtributesToMV(model,currentPrincipalName);
        despliegueFavs(user,model);
        return model;
    }

    /**
     * <b>Metodo despliegueFavs</b>
     * Despliega los favoritos del usuario
     * En listas de 2, que es como se ve en el
     * cliente.
     * @param user - Usuario cuyos favoritos se quieren mostrar
     * @param model
     */
    private void despliegueFavs (Usuario user, ModelAndView model) {

        if (user.getFavoritas().size() > 0)
        {
            model.addObject("hayPelis",true);
            model.addObject("pelis",user.getFavoritas());
            Iterator<Pelicula> it = user.getFavoritas().iterator();
            List<Pelicula> pelis2 = new LinkedList<Pelicula>();
            List<List<Pelicula>> total = new LinkedList<List<Pelicula>>();
            int count = 0;
            Pelicula peli;
            while (it.hasNext())
            {
                peli = it.next();
                pelis2.add(peli);
                count++;
                if (count == 2 || !it.hasNext())
                {
                    count = 0;
                    total.add(pelis2);
                    pelis2 = new LinkedList<Pelicula>();
                }
            }
            model.addObject("total",total);
        }
        else model.addObject("hayPelis",false);
    }


    /**
     * <b>Metodo postComment</b>
     * Postea un comentario en una pelicula
     * @param name - Nombre de la pelicula
     * @param opinion - Objeto comentario
     * @return
     */
    @RequestMapping("/comment")
    public ModelAndView postComment(@RequestParam("name")String name, Opinion opinion) {

        String currentPrincipalName = operations.currentPrincipalName();
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/movie?movie="+name);
        model.addObject("pelicula",new Pelicula());
        operations.addControlAtributesToMV(model,currentPrincipalName);

        Pelicula peli = peliculaRepository.findByNombrePelicula(name);
        List<Opinion> opinions = peli.getOpiniones();
        //Por simplicidad no se permiten opiniones iguales
        if (!peli.contiene(opinions,opinion))
        {
            opinion.setAutor(usuarioRepository.findByUser(currentPrincipalName));
            opinion.setPelicula(peli);
            peli.addOpinion(opinion);
            opinionRepository.save(opinion);
            peliculaRepository.save(peli);
        }
        model.addObject("comentarios",peli.getOpiniones());
        return model;
    }
}