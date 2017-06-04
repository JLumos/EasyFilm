package upm.daw.easyfilm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import upm.daw.easyfilm.model.Pelicula;
import upm.daw.easyfilm.model.Usuario;
import upm.daw.easyfilm.repository.PeliculaRepository;
import upm.daw.easyfilm.repository.UsuarioRepository;

import java.util.List;

@Controller
public class EFController {

    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("login");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }


    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/home")
    public ModelAndView home() {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        String currentprincipalname = auth.getName();
        ModelAndView model = new ModelAndView("home")
                .addObject("name", currentprincipalname);

        Usuario actual = usuarioRepository.findByUser(currentprincipalname);
        model.addObject("nombreUsuario",actual.getIsAdmin().equals("yes")?"admin":"user");
        model.addObject("active",currentprincipalname);
        model.addObject("user",currentprincipalname);
        model.addObject("pelicula", new Pelicula());

        List<Pelicula> recomendadas = peliculaRepository.selectAll();
       for (int j = 0; j < recomendadas.size(); j++)
       {
           String p = "p"+(j+1);
           String v = "v"+(j+1);
           String i = "i"+(j+1);
           String d = "d"+(j+1);
           String r = "r"+(j+1);
           String y = "y"+(j+1);


           model.addObject(p,recomendadas.get(j).getNombrePelicula());
           model.addObject(v,recomendadas.get(j).getDescripcion());
           model.addObject(i,recomendadas.get(j).getUrl_portada());
           model.addObject(d,recomendadas.get(j).getDirector());
           model.addObject(r,recomendadas.get(j).getValoracion());
           model.addObject(y,recomendadas.get(j).getYear());

       }
        return model;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/root")
    public ModelAndView root() {
        return new ModelAndView("root");

    }
}

