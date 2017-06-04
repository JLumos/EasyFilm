package upm.daw.easyfilm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import upm.daw.easyfilm.model.Pelicula;
import upm.daw.easyfilm.model.Usuario;
import upm.daw.easyfilm.repository.PeliculaRepository;
import upm.daw.easyfilm.repository.UsuarioRepository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jesus on 19/05/2017.
 */
@Controller
public class AdOpsController {

    @Autowired
    UsuarioRepository usuarios;
    @Autowired
    PeliculaRepository peliculas;

    @Secured("ROLE_ADMIN")
    @RequestMapping("/userControlPanel")
    public ModelAndView userControlPanel() {

        ModelAndView model = new ModelAndView();
        model.setViewName("userControlPanel");
        Iterable<Usuario> it = usuarios.findAll();
        List<Usuario> allUsers = new LinkedList<Usuario>();
        Iterator<Usuario> iter = it.iterator();
        while (iter.hasNext()) allUsers.add(iter.next());

        model.addObject("usuarios",allUsers);
        model.addObject("pelicula", new Pelicula());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addObject("user",currentPrincipalName);
        Usuario actual = usuarios.findByUser(currentPrincipalName);
        model.addObject("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
        model.addObject("active",currentPrincipalName);


        return model;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/movieControlPanel")
    public ModelAndView movieControlPanel() {

        ModelAndView model = new ModelAndView();
        model.setViewName("movieControlPanel");
        Iterable<Pelicula> it = peliculas.findAll();
        List<Pelicula> allMovies = new LinkedList<Pelicula>();
        Iterator<Pelicula> iter = it.iterator();
        while (iter.hasNext()) allMovies.add(iter.next());

        model.addObject("peliculas",allMovies);
        model.addObject("modals",allMovies);
        model.addObject("pelicula", new Pelicula());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addObject("user",currentPrincipalName);
        Usuario actual = usuarios.findByUser(currentPrincipalName);
        model.addObject("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
        model.addObject("active",currentPrincipalName);

        return model;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/adOpsCreate")
    public String create(@RequestParam("type") String type, Model model) {
        if (type.equals("user"))
            model.addAttribute("usuario", new Usuario());

        else if (type.equals("multimedia"))
            model.addAttribute("pelicula", new Pelicula());

        model.addAttribute("type",type);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("user",currentPrincipalName);
        Usuario actual = usuarios.findByUser(currentPrincipalName);
        model.addAttribute("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
        model.addAttribute("active",currentPrincipalName);

        return "adOpsCreate";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/adOpsModify")
    public String modify(@RequestParam("type") String type, @RequestParam("name") String name, Model model) {

        Usuario user = usuarios.findByUser(name);

        if (type.equals("user"))
        {
            model.addAttribute("usuario",new Usuario());
            model.addAttribute("user",user.getUser());
            model.addAttribute("email",user.getEmail());
            model.addAttribute("nombre",user.getNombre());
            model.addAttribute("isAdmin",user.getIsAdmin());

        }
        else if (type.equals("multimedia"))
        {
            model.addAttribute("pelicula",new Pelicula());
            Pelicula peli = peliculas.findByIdPelicula(Long.parseLong(name));
            model.addAttribute("id",peli.getIdPelicula());
            model.addAttribute("nombrePelicula",peli.getNombrePelicula());
            model.addAttribute("url_portada",peli.getUrl_portada());
            model.addAttribute("plot",peli.getDescripcion());
            model.addAttribute("year",peli.getYear());
            model.addAttribute("trailer",peli.getUrl_trailer());
            model.addAttribute("director",peli.getDirector());
            model.addAttribute("actors",peli.getActors());

        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Usuario actual = usuarios.findByUser(currentPrincipalName);
        model.addAttribute("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
        model.addAttribute("active",currentPrincipalName);

        model.addAttribute("type",type);
        return "adOpsModify";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/adOpsDelete")
    public String delete(@RequestParam("type") String type,@RequestParam("name") String name,Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if (type.equals("user"))
        {
            Usuario user = usuarios.findByUser(name);
            model.addAttribute("user",user.getUser());
            model.addAttribute("email",user.getEmail());
            Usuario actual = usuarios.findByUser(currentPrincipalName);
            model.addAttribute("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
            model.addAttribute("active",currentPrincipalName);
        }
        else if (type.equals("multimedia"))
        {
            Pelicula peli = peliculas.findByIdPelicula(Long.parseLong(name));
            model.addAttribute("id",peli.getIdPelicula());
            model.addAttribute("nombrePelicula",peli.getNombrePelicula());
            model.addAttribute("plot",peli.getDescripcion());
            model.addAttribute("year",peli.getYear());
            model.addAttribute("director",peli.getDirector());
            model.addAttribute("actors",peli.getActors());
            model.addAttribute("trailer",peli.getUrl_trailer());
            model.addAttribute("url_portada",peli.getUrl_portada());

        }


        model.addAttribute("type",type);
        return "adOpsDelete";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/create", method= RequestMethod.POST)
    public ModelAndView createUser(@RequestParam("type") String type, Usuario user, Pelicula peli) {

        ModelAndView model = new ModelAndView();
        model.setViewName("adOpsCreate");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addObject("type",type);
        Usuario actual = usuarios.findByUser(currentPrincipalName);
        model.addObject("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
        model.addObject("active",currentPrincipalName);

        boolean errors = false;

        if (type.equals("user"))
        {
            if (user.getUser() == null || user.getUser().equals("") || user.getUser().length() < 3) {
                model.addObject("wrongUsername", true);
                errors = true;
            }
            if (!errors && usuarios.findByUser(user.getUser()) != null) {
                model.addObject("wrongUsername", true);
            }

            else {
                if (user.getPassword() == null || user.getPassword().equals(""))
                {
                    model.addObject("wrongPwd", true);
                    errors = true;
                }
                if (usuarios.findByEmail(user.getEmail()) != null || user.getEmail() == null || user.getEmail().length() < 5) {
                    model.addObject("wrongMail", true);
                    errors = true;
                }
                if (user.getIsAdmin().equals("yes"))
                    user.setRolesAdmin();
                else if (user.getIsAdmin().equals("no"))
                    user.setRolesUser();
                else user.setRolesUser();

                if (!errors) {
                    usuarios.save(user);
                    model.setViewName("Ready");
                    model.addObject("type",type);
                }
            }
        }
        else if (type.equals("multimedia"))
        {
            if (peli.getNombrePelicula() == null || peli.getNombrePelicula().equals("") || peli.getNombrePelicula().length() < 1)
            {
                model.addObject("wrongTitle", true);
                errors = true;
            }
            if (!errors && peliculas.findByNombrePelicula(peli.getNombrePelicula()) != null)
            {
                model.addObject("wrongTitle", true);
            }
            else {
                if (peli.getUrl_trailer() == null || peli.getUrl_trailer().equals("")) {
                    model.addObject("wrongTrailer", true);
                    errors = true;
                }
                if (!errors) {
                    peliculas.save(peli);
                    model.setViewName("Ready");
                    model.addObject("type",type);
                }
            }
        }
        return model;

    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/modify")
    public ModelAndView modifyUser(@RequestParam("name")String id, @RequestParam("type") String type, Pelicula nueva, Usuario nuevo) {

        ModelAndView model = new ModelAndView();
        boolean mod = false, duplicate = false;
        Usuario antiguo = usuarios.findByUser(id);

        if (type.equals("multimedia"))
        {
            Pelicula antigua = peliculas.findByIdPelicula(Long.parseLong(id));
            if (peliculas.findByNombrePelicula(nueva.getNombrePelicula()) != null)
            {
                model.addObject("id", antigua.getIdPelicula());
                model.addObject("nombrePelicula", antigua.getNombrePelicula());
                model.addObject("url_portada", antigua.getUrl_portada());
                model.addObject("plot", antigua.getDescripcion());
                model.addObject("year", antigua.getYear());
                model.addObject("trailer", antigua.getUrl_trailer());
                model.addObject("director", antigua.getDirector());
                model.addObject("actors", antigua.getActors());
                duplicate = true;
            }
            else {
                mod = actualizarPelicula(antigua, nueva);
                if (mod) peliculas.save(antigua);
                model.addObject("id", antigua.getIdPelicula());
                model.addObject("nombrePelicula", antigua.getNombrePelicula());
                model.addObject("url_portada", antigua.getUrl_portada());
                model.addObject("plot", antigua.getDescripcion());
                model.addObject("year", antigua.getYear());
                model.addObject("trailer", antigua.getUrl_trailer());
                model.addObject("director", antigua.getDirector());
                model.addObject("actors", antigua.getActors());
            }


        }
        else if (type.equals("user"))
        {
            if (usuarios.findByUser(nuevo.getUser()) != null)
            {
                model.addObject("user", antiguo.getUser());
                model.addObject("email", antiguo.getEmail());
                model.addObject("nombre", antiguo.getNombre());
                model.addObject("isAdmin",antiguo.getIsAdmin());
                duplicate = true;
            }
            else {
                mod = actualizarUsuario(antiguo, nuevo);
                if (mod) usuarios.save(antiguo);
                model.addObject("user", antiguo.getUser());
                model.addObject("email", antiguo.getEmail());
                model.addObject("nombre", antiguo.getNombre());
                model.addObject("isAdmin",antiguo.getIsAdmin());
            }
            model.addObject("user",antiguo.getUser());
        }

        if(mod) {
            model.setViewName("Modified");
            model.addObject("type",type);
        }

        if(!mod) {
            model.setViewName("adOpsModify");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            model.addObject("type",type);
            Usuario actual = usuarios.findByUser(currentPrincipalName);
            model.addObject("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
            model.addObject("active",currentPrincipalName);

            if (duplicate)
                model.addObject("duplicate",true);
            else
            {
                model.addObject("noMod", true);
            }
        }
        return model;
    }

    private Pelicula existe(Pelicula nueva) {

        return peliculas.findByNombrePelicula(nueva.getNombrePelicula());
    }

    private boolean actualizarUsuario(Usuario antiguo, Usuario nuevo) {

        boolean mod = false;
        if (!nuevo.getUser().equals("")) {
            antiguo.setUser(nuevo.getUser());
            mod = true;
        }
        if (!nuevo.getEmail().equals("")) {
            antiguo.setEmail(nuevo.getEmail());
            mod = true;
        }
        if (nuevo.getPassword()!=null && !nuevo.getPassword().equals("")) {
            antiguo.setPasswordHashed(nuevo.getPassword());
            mod = true;
        }
        if (!nuevo.getNombre().equals("")) {
            antiguo.setNombre(nuevo.getNombre());
            mod = true;
        }

        if (!nuevo.getIsAdmin().equals(antiguo.getIsAdmin()))
        {
            antiguo.setIsAdmin(nuevo.getIsAdmin());
            if (nuevo.getIsAdmin().equals("yes"))
                antiguo.darAdmin();
            else antiguo.quitarAdmin();
            mod = true;
        }
        return mod;
    }

    private boolean actualizarPelicula(Pelicula antigua, Pelicula updated) {

        boolean mod = false;

        if (!updated.getNombrePelicula().equals("")) {
            antigua.setNombrePelicula(updated.getNombrePelicula());
            mod = true;
        }
        if (!updated.getDescripcion().equals("")) {
            antigua.setDescripcion(updated.getDescripcion());
            mod = true;
        }
        if (!updated.getUrl_trailer().equals("")) {
            antigua.setUrl_trailer(updated.getUrl_trailer());
            mod = true;
        }

        return mod;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/delete")
    public ModelAndView deleteUser(@RequestParam("name")String id, @RequestParam("type") String type) {
        ModelAndView model = new ModelAndView();
        if (type.equals("multimedia"))
        {
            peliculas.deleteByIdPelicula(Long.parseLong(id));
        }
        else if (type.equals("user"))
        {
            usuarios.deleteByUser(id);
        }
        model.setViewName("Removed");
        model.addObject("type",type);

        return model;
    }

}
