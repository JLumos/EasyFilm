package upm.daw.easyfilm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpRequest;
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
import upm.daw.easyfilm.rest.MovieService;

import javax.naming.Binding;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
public class ApiRestController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private OpinionRepository opinionRepository;

    @RequestMapping(value="/movies", method = RequestMethod.POST)
    public ModelAndView getPeliculas (@Valid Pelicula pelic, BindingResult result) throws IOException {

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:movies?search="+pelic.getNombrePelicula());
        model.addObject("pelicula", new Pelicula());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addObject("nombreUsuario",currentPrincipalName);

        return model;
    }


    @RequestMapping(value="/movies", method = RequestMethod.GET)
    public ModelAndView getPeliculas (@RequestParam("search") String nombre) throws IOException {

        ModelAndView model = new ModelAndView();

        if (nombre.length()==0) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            model.addObject("nombreUsuario",currentPrincipalName);
            model.setViewName("home");
            model.addObject("pelicula", new Pelicula());
            return model;
        }

        List<List<Pelicula>> total = new LinkedList<List<Pelicula>>();
        List<Pelicula> pelis =  movieService.getPeliculas(nombre);
        Map<String,String> map = new HashMap<String,String>();

        if (pelis != null) {
            model.addObject("hayPelis",true);
            Iterator<Pelicula> it = pelis.iterator();
            List<Pelicula> pelis2 = new LinkedList<Pelicula>();
            int count = 0;
            while (it.hasNext()) {
                Pelicula peli = it.next();
                if (!peli.getUrl_portada().equals("N/A") && !map.containsKey(peli.getNombrePelicula())) {
                    pelis2.add(peli);
                    map.put(peli.getNombrePelicula(),null);
                    count++;
                    if (count == 2) {
                        count = 0;
                        total.add(pelis2);
                        pelis2 = new LinkedList<Pelicula>();
                    }
                }
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            model.addObject("nombreUsuario",currentPrincipalName);

            if (total.size() > 0) {
                model.addObject("total", total);
                model.addObject("pelicula", new Pelicula());
            }
            else {
                model.addObject("pelicula", new Pelicula());
                model.addObject("hayPelis",false);
            }
            return model;
        }
        else
        {
            model.addObject("pelicula", new Pelicula());
            model.addObject("hayPelis",false);
            return model;
        }
    }

    @RequestMapping("/movie")
    public ModelAndView getPelicula (@RequestParam("movie")String movie) throws IOException {

        ModelAndView model = new ModelAndView();
        model.setViewName("movie");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addObject("nombreUsuario",currentPrincipalName);

        Pelicula peli =  movieService.getPelicula(movie);
        model.addObject("title",peli.getNombrePelicula());
        model.addObject("plot",peli.getDescripcion());
        model.addObject("year",peli.getYear());
        model.addObject("director",peli.getDirector());
        model.addObject("rated",peli.getValoracion());
        model.addObject("pelicula",new Pelicula());
        model.addObject("actors",peli.getActors());
        model.addObject("comentarios",peli.getOpiniones());


        if (peli.getUrl_trailer() == null || peli.getUrl_trailer().length()==0)
            model.addObject("hayTrailer",false);
        else model.addObject("hayTrailer",true);
        model.addObject("trailer",peli.getUrl_trailer());
        if(usuarioRepository.findByUser(currentPrincipalName).contiene(usuarioRepository.findByUser(currentPrincipalName).getFavoritas(),peli))
            model.addObject("isFav",true);
        else model.addObject("isFav",false);
        model.addObject("comentario", new Opinion());
        return model;
    }

    @RequestMapping("/fav")
    public ModelAndView setFav (@RequestParam("name") String name) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Pelicula fav = peliculaRepository.findByNombrePelicula(name);
        Usuario user = usuarioRepository.findByUser(currentPrincipalName);
        ModelAndView model = new ModelAndView();

        if(!user.contiene(user.getFavoritas(),fav)) {
            user.addFavorita(fav);
            usuarioRepository.save(user);
            model.addObject("isFav",true);
        }

        model.setViewName("movie");
        model.addObject("nombreUsuario",currentPrincipalName);
        model.addObject("title",fav.getNombrePelicula());
        model.addObject("plot",fav.getDescripcion());
        model.addObject("year",fav.getYear());
        model.addObject("director",fav.getDirector());
        model.addObject("rated",fav.getValoracion());
        model.addObject("pelicula",new Pelicula());
        model.addObject("actors",fav.getActors());
        model.addObject("comentario", new Opinion());

        if (fav.getUrl_trailer() == null || fav.getUrl_trailer().length()==0)
            model.addObject("hayTrailer",false);
        else model.addObject("hayTrailer",true);
        model.addObject("trailer",fav.getUrl_trailer());

        return model;
    }

    @RequestMapping("/unfav")
    public ModelAndView unFav (@RequestParam("name") String name) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Pelicula fav = peliculaRepository.findByNombrePelicula(name);
        Usuario user = usuarioRepository.findByUser(currentPrincipalName);

        ModelAndView model = new ModelAndView();

        if(user.contiene(user.getFavoritas(),fav)) {
            user.removeFavorita(fav);
            usuarioRepository.save(user);
            model.addObject("isFav",false);

        }
        model.setViewName("movie");
        model.addObject("nombreUsuario",currentPrincipalName);
        model.addObject("title",fav.getNombrePelicula());
        model.addObject("plot",fav.getDescripcion());
        model.addObject("year",fav.getYear());
        model.addObject("director",fav.getDirector());
        model.addObject("rated",fav.getValoracion());
        model.addObject("pelicula",new Pelicula());
        model.addObject("actors",fav.getActors());
        model.addObject("comentario", new Opinion());

        if (fav.getUrl_trailer() == null || fav.getUrl_trailer().length()==0)
            model.addObject("hayTrailer",false);
        else model.addObject("hayTrailer",true);
        model.addObject("trailer",fav.getUrl_trailer());
        model.addObject("isFav",false);

        return model;
    }

    @RequestMapping("/favs")
    public ModelAndView showFavs() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Usuario user = usuarioRepository.findByUser(currentPrincipalName);
        ModelAndView model = new ModelAndView();
        model.setViewName("favs");
        model.addObject("pelicula",new Pelicula());
        model.addObject("nombreUsuario",currentPrincipalName);

        if (user.getFavoritas().size() > 0)
        {
            model.addObject("hayPelis",true);
            model.addObject("pelis",user.getFavoritas());
            Iterator<Pelicula> it = user.getFavoritas().iterator();
            List<Pelicula> pelis2 = new LinkedList<Pelicula>();
            List<List<Pelicula>> total = new LinkedList<List<Pelicula>>();
            int count = 0;
            while (it.hasNext()) {
                Pelicula peli = it.next();
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
        else
        {
            model.addObject("hayPelis",false);
        }

        return model;
    }

    @RequestMapping("/comment")
    public ModelAndView postComment(@RequestParam("name")String name, Opinion opinion) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Usuario user = usuarioRepository.findByUser(currentPrincipalName);
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/movie?movie="+name);
        model.addObject("pelicula",new Pelicula());
        model.addObject("nombreUsuario",currentPrincipalName);

        Pelicula peli = peliculaRepository.findByNombrePelicula(name);
        List<Opinion> opinions = peli.getOpiniones();
        if (!peli.contiene(opinions,opinion))
        {
            opinion.setAutor(user);
            opinion.setPelicula(peli);
            opinionRepository.save(opinion);
            peli.addOpinion(opinion);
            peliculaRepository.save(peli);
        }
        else
        {

        }

        model.addObject("comentarios",peli.getOpiniones());

        return model;
    }

}
