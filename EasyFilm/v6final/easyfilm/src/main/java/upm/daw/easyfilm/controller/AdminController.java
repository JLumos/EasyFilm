package upm.daw.easyfilm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

import java.util.List;

/**
 * <b>AdminController</b>
 * Este controller se encarga de gestionar la logica correspondiente
 * a las operaciones especificas que pueden realizar los usuarios
 * administradores del sistema.
 */
@Controller
public class AdminController {

    @Autowired
    UsuarioRepository usuarios;
    @Autowired
    PeliculaRepository peliculas;
    @Autowired
    private ComunOperations operations;

    /**
     * <b>Metodo userControlPanel</b>
     * Para acceder a la vista de control de usuarios
     * Esta vista muestra una lista de todos los usuarios
     * que estan registrados en el sistema, junto con su
     * informacion asociada.
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/users")
    public ModelAndView userControlPanel() {

        ModelAndView model = new ModelAndView();
        model.setViewName("userControlPanel");
        List<Usuario> allUsers = usuarios.selectAll();
        String currentPrincipalName = operations.currentPrincipalName();
        model.addObject("usuarios",allUsers);
        model.addObject("pelicula", new Pelicula());
        model.addObject("user",currentPrincipalName);
        operations.addControlAtributesToMV(model,currentPrincipalName);
        return model;
    }


    /**
     * <b>Metodo movieControlPanel</b>
     * Para acceder a la vista de control de peliculas
     * Esta vista muestra una lista de todas las peliculas
     * de las que dispone el sistema, junto con su informacion
     * asociada
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/movies")
    public ModelAndView movieControlPanel() {

        ModelAndView model = new ModelAndView();
        model.setViewName("movieControlPanel");
        List<Pelicula> allMovies = peliculas.selectAll();
        String currentPrincipalName = operations.currentPrincipalName();
        model.addObject("peliculas",allMovies);
        model.addObject("modals",allMovies);
        model.addObject("pelicula", new Pelicula());
        model.addObject("user",currentPrincipalName);
        operations.addControlAtributesToMV(model,currentPrincipalName);
        return model;
    }


    /**
     * <b>Metodo create</b>
     * Para acceder a la vista de creacion de usuarios y peliculas
     * la vista recibe objetos en funcion de lo que se quiera crear.
     * @param type - Usuario o Multimedia
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/create")
    public String create(@RequestParam("type") String type, Model model) {

        if (type.equals("user"))
            model.addAttribute("usuario", new Usuario());
        else if (type.equals("multimedia"))
            model.addAttribute("pelicula", new Pelicula());
        String currentPrincipalName = operations.currentPrincipalName();
        model.addAttribute("type",type);
        model.addAttribute("user",currentPrincipalName);
        operations.addControlAtributesToMV(model,currentPrincipalName);
        return "adOpsCreate";
    }


    /**
     * <b>Metodo modify</b>
     * Para accer a la vista de modificacion de usuarios y peliculas.
     * La vista recibe objetos en funcion de lo que se quiera modificar.
     * @param type - Usuario o Multimedia
     * @param name - ID o nombre de usuario
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/modify")
    public String modify(@RequestParam("type") String type, @RequestParam("name") String name, Model model) {

        Usuario user = usuarios.findByUser(name);
        if (type.equals("user"))
        {
            model.addAttribute("usuario",new Usuario());
            operations.addUserAtributesToMV(model,user);
        }
        else if (type.equals("multimedia"))
        {
            model.addAttribute("pelicula",new Pelicula());
            operations.addAllMovieAtributesToMV(model,name);
        }
        String currentPrincipalName = operations.currentPrincipalName();
        operations.addControlAtributesToMV(model,currentPrincipalName);
        model.addAttribute("type",type);
        return "adOpsModify";
    }


    /**
     * <b>Metodo delete</b>
     * Para acceder a la vista de eliminacion de usuarios y peliculas.
     * La vista recibe objetos en funcion de lo que se quiera eliminar.
     * @param type - Usuario o Multimedia
     * @param name - ID o nombre de usuario
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/delete")
    public String delete(@RequestParam("type") String type,@RequestParam("name") String name,Model model) {

        String currentPrincipalName = operations.currentPrincipalName();
        if (type.equals("user"))
        {
            Usuario user = usuarios.findByUser(name);
            operations.addUserAtributesToMV(model,user);
            operations.addControlAtributesToMV(model,currentPrincipalName);
        }
        else if (type.equals("multimedia"))
        {
            operations.addAllMovieAtributesToMV(model,name);
            operations.addControlAtributesToMV(model,currentPrincipalName);
        }
        model.addAttribute("type",type);
        return "adOpsDelete";
    }


    /**
     * <b>Metodo createSomething</b>
     * Se encarga de realizar la validacion de la creacion de usuarios o peliculas
     * Si dicha validacion es correcta, se muestra una vista simple con un mensaje
     * de aceptacion.
     * Si la validacion falla, se muestra la misma vista de la que se vino, pero con
     * mensajes de error que ayuden al usuario a comprender que ha fallado.
     * @param type - Usuario o Multimedia
     * @param user - Datos del Usuario a crear
     * @param peli - Datos de la Pelicula a crear
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/create", method= RequestMethod.POST)
    public ModelAndView createSomething(@RequestParam("type") String type, Usuario user, Pelicula peli) {

        ModelAndView model = new ModelAndView();
        //Se prepara la vista por si hubiera fallos
        model.setViewName("adOpsCreate");
        model.addObject("type",type);
        String currentPrincipalName = operations.currentPrincipalName();
        operations.addControlAtributesToMV(model,currentPrincipalName);

        //Variable de control para saber si hay errores de algun tipo
        boolean errors = false;
        //Validacion de usuarios
        if (type.equals("user"))
        {
            //Si el nombre de usuario no existe o es vacio, o su longitud es menor a 3 - ERROR
            if (user.getUser() == null
                    || user.getUser().equals("")
                    || user.getUser().length() < 3
                    || user.getUser().contains(" ")) {
                model.addObject("wrongUsername", true);
                errors = true;
            }
            //Si el nombre de usuario esta OK pero ya existe previamente - ERROR
            if (!errors && usuarios.findByUser(user.getUser()) != null) {
                model.addObject("wrongUsername", true);
            }

            //Comprobacion del resto de argumentos
            else {
                //Si la password es null o vacia - ERROR
                if (user.getPassword() == null || user.getPassword().equals(""))
                {
                    model.addObject("wrongPwd", true);
                    errors = true;
                }
                //Si el mail ya esta en uso, o no se ha insertado ninguno, o no es lo suficientemente largo - ERROR
                if (usuarios.findByEmail(user.getEmail()) != null
                        || user.getEmail() == null
                        || user.getEmail().length() < 5) {
                    model.addObject("wrongMail", true);
                    errors = true;
                }
                //Si se desean privilegios de admin, se otorgan
                if (user.getIsAdmin().equals("yes"))
                    user.setRolesAdmin();
                //Si se desean privilegios de user, se otorgan
                else if (user.getIsAdmin().equals("no"))
                    user.setRolesUser();
                //Por defecto (para evitar admin indeseado) se otorgan roles de user
                else user.setRolesUser();

                //Si la validacion tuvo exito, se guarda el usuario y se muestra vista de OK
                if (!errors) {
                    usuarios.save(user);
                    model.setViewName("Ready");
                    model.addObject("type",type);
                }
            }
        }
        //Validacion de peliculas
        else if (type.equals("multimedia"))
        {
            //Si el nombre de la pelicula es null o vacio, o demasiado corto - ERROR
            if (peli.getNombrePelicula() == null || peli.getNombrePelicula().equals("") || peli.getNombrePelicula().length() < 1)
            {
                model.addObject("wrongTitle", true);
                errors = true;
            }
            //Si el nombre es correcto pero ya existe - ERROR
            //Por tanto, por simplicidad, no habra peliculas con nombres iguales (aunque sean de distinto año)
            if (!errors && peliculas.findByNombrePelicula(peli.getNombrePelicula()) != null)
            {
                model.addObject("wrongTitle", true);
                errors = true;
            }
            //Comprobacion del resto de argumentos
            else {
                //Si el trailer es null o vacio - ERROR
                if (peli.getUrl_trailer() == null || peli.getUrl_trailer().equals("")) {
                    model.addObject("wrongTrailer", true);
                    errors = true;
                }
                //Si no hubo errores en la validacion, se guarda la peli y se muestra una vista de OK
                if (!errors) {
                    peliculas.save(peli);
                    model.setViewName("Ready");
                    model.addObject("type",type);
                }
            }
        }
        return model;
    }


    /**
     * <b>Metodo modifySomething</b>
     * Se encarga de realizar la validacion de la modifacion de usuarios o peliculas
     * Si dicha validacion es correcta, se muestra una vista simple con un mensaje
     * de aceptacion.
     * Si la validacion falla, se muestra la misma vista de la que se vino, pero con
     * mensajes de error que ayuden al usuario a comprender que ha fallado.
     * @param id - ID o nombre de usuario
     * @param type - Usuario o Multimedia
     * @param nueva - Datos de pelicula para sobrescribir los antiguos
     * @param nuevo - Datos de usuario para sobrescribir los antiguos
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/modify")
    public ModelAndView modifySomething(@RequestParam("name")String id, @RequestParam("type") String type, Pelicula nueva, Usuario nuevo) {

        ModelAndView model = new ModelAndView();
        boolean mod = false, duplicate = false, duplicateMail = false, adminError = false;

        //Para modificar peliculas
        if (type.equals("multimedia"))
        {
            Pelicula antigua = peliculas.findByIdPelicula(Long.parseLong(id));
            //Si se introdujo un nuevo nombre para la peli, no debe estar repetido. Si lo esta -ERROR
            if (nueva.getNombrePelicula() != null && !nueva.getNombrePelicula().equals("")  &&
                    (peliculas.findByNombrePelicula(nueva.getNombrePelicula()) != null
                    || nueva.getNombrePelicula().length() < 1))
            {
                operations.addAllMovieAtributesToMV(model,antigua);
                duplicate = true;
            }
            //Se intenta modificar la pelicula. Si algo se modifica, se guarda
            else {
                mod = actualizarPelicula(antigua, nueva);
                if (mod) peliculas.save(antigua);
                operations.addAllMovieAtributesToMV(model,antigua);
            }
        }
        //Para modificar usuarios
        else if (type.equals("user"))
        {
            Usuario antiguo = usuarios.findByUser(id);
            //Si el nuevo username esta pillado - ERROR
            if (nuevo.getUser() != null && !nuevo.getUser().equals("") &&
                    (usuarios.findByUser(nuevo.getUser()) == null
                    || nuevo.getUser().length() < 3
                    || nuevo.getUser().contains(" ")))
            {
                operations.addUserAtributesToMV(model,antiguo);
                duplicate = true;
            }
            //Si el nuevo mail esta pillado - ERROR
            if (nuevo.getEmail()!= null && !nuevo.getEmail().equals("") &&
                    (usuarios.findByEmail(nuevo.getUser()) != null
                    || nuevo.getEmail().length() < 5))
            {
                operations.addUserAtributesToMV(model,antiguo);
                duplicateMail = true;
            }

            //Al menos debe haber un administrador en el sistema
            if (nuevo.getIsAdmin().equals("no") && antiguo.getIsAdmin().equals("yes"))
            {
                List<Usuario> admins = usuarios.selectAdmins();
                if (admins.size() < 2)
                    adminError = true;
            }

            //Se intenta modificar el usuario. Si hay algo modificado, se guarda
            if (!duplicate && !adminError) {
                mod = actualizarUsuario(antiguo, nuevo);
                if (mod) usuarios.save(antiguo);
                operations.addUserAtributesToMV(model,antiguo);
            }
            model.addObject("user",antiguo.getUser());
        }

        //Si ha habido modificacion, se lanza una vista de OK
        if(mod) {
            model.setViewName("Modified");
            model.addObject("type",type);
        }

        //Si no hubo modificacion, se lanzan errores o se indica la no modificacion.
        if(!mod) {
            model.setViewName("adOpsModify");
            model.addObject("type",type);
            String currentPrincipalName = operations.currentPrincipalName();
            operations.addControlAtributesToMV(model,currentPrincipalName);

            if (duplicate)
                model.addObject("duplicate",true);
            if (duplicateMail)
                model.addObject("wrongMail",true);
            if (adminError)
                model.addObject("adminError",true);

            if (!duplicate && !duplicateMail && !adminError) model.addObject("noMod", true);
        }
        return model;
    }


    /**
     * <b>Metodo actualizarUsuario</b>
     * Se encarga de actualizar los datos de los usuarios
     * @param antiguo - Datos antiguos
     * @param nuevo - Datos nuevos
     * @return
     */
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

        //En caso de que se quiera cambio de privilegios, se realiza
        //Pero solo si se da un cambio de estado
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


    /**
     * <b>Metodo actualizarPelicula</b>
     * Se encarga de actualizar los datos de las peliculas
     * @param antigua - Datos antiguos
     * @param updated - Datos nuevos
     * @return
     */
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
        if (!updated.getDirector().equals("")) {
            antigua.setDirector(updated.getDirector());
            mod = true;
        }
        if (!updated.getYear().equals("")) {
            antigua.setYear(updated.getYear());
            mod = true;
        }
        if (!updated.getActors().equals("")) {
            antigua.setActors(updated.getActors());
            mod = true;
        }
        if (!updated.getUrl_portada().equals("")) {
            antigua.setUrl_portada(updated.getUrl_portada());
            mod = true;
        }
        if (!updated.getValoracion().equals("")) {
            antigua.setValoracion(updated.getValoracion());
            mod = true;
        }

        return mod;
    }


    /**
     * <b>Metodo deleteSomething</b>
     * Se encarga de eliminar usuarios o peliculas del sistema
     * @param id - ID o username
     * @param type - Usuario o multimedia
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/delete")
    public ModelAndView deleteSomething(@RequestParam("name")String id, @RequestParam("type") String type) {
        ModelAndView model = new ModelAndView();
        if (type.equals("multimedia"))
            peliculas.deleteByIdPelicula(Long.parseLong(id));

        else if (type.equals("user"))
            usuarios.deleteByUser(id);

        model.setViewName("Removed");
        model.addObject("type",type);
        return model;
    }

    /**
     * <b>Metodo disable</b>
     * Este metodo da de baja a un usuario
     * Esto es, seguira registrado en el sistema
     * pero no podra iniciar sesion
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/disable")
    public  ModelAndView disable(@RequestParam("name")String name) {

        Usuario user = usuarios.findByUser(name);
        //Se puede dar de baja un usuario si 1) esta dado de alta, 2a)es usuario normal o 2b)es admin y hay mas admins
        if (user.getEstado() == 0 &&
                ((user.getIsAdmin().equals("yes")
                && usuarios.selectAdmins().size() > 1)
                || user.getIsAdmin().equals("no")))
            user.setEstado(1);
        usuarios.save(user);
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/admin/users");
        String currentPrincipalName = operations.currentPrincipalName();
        operations.addControlAtributesToMV(model,currentPrincipalName);
        List<Usuario> allUsers = usuarios.selectAll();
        model.addObject("usuarios",allUsers);
        model.addObject("pelicula", new Pelicula());
        model.addObject("user",currentPrincipalName);
        return model;
    }

    /**
     * <b>Metodo enable</b>
     * Este metodo da de alta a un usuario
     * Esto es, estaba registrado en el sistema
     * y ahora podra logearse
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/enable")
    public ModelAndView enable (@RequestParam("name")String name) {

        Usuario user = usuarios.findByUser(name);
        if (user.getEstado() == 1)
            user.setEstado(0);
        usuarios.save(user);
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/admin/users");
        String currentPrincipalName = operations.currentPrincipalName();
        operations.addControlAtributesToMV(model,currentPrincipalName);
        List<Usuario> allUsers = usuarios.selectAll();
        model.addObject("usuarios",allUsers);
        model.addObject("pelicula", new Pelicula());
        model.addObject("user",currentPrincipalName);
        return model;
    }
}