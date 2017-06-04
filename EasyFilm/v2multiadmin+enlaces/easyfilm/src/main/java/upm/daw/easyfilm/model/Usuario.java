package upm.daw.easyfilm.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuario;

    @Column(unique = true)
    private String user;

    private String password;

    private String email;

    private String nombre;

    private String isAdmin;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Pelicula> favoritas;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> roles;

    public Usuario() {
        GrantedAuthority[] userRoles = { new SimpleGrantedAuthority("ROLE_USER") };
        this.roles = Arrays.asList(userRoles);
        this.isAdmin = "no";
    }

    public Usuario(String user, String password, String email, List<GrantedAuthority> roles) {
        this.user = user;
        this.password = password.length() > 1 ? new BCryptPasswordEncoder().encode(password) : null;
        this.roles = roles;
        this.email = email;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setRolesUser () {
        GrantedAuthority[] userRoles = { new SimpleGrantedAuthority("ROLE_USER") };
        this.roles = Arrays.asList(userRoles);
    }

    public void setRolesAdmin () {
        GrantedAuthority[] adminRoles = { new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER") };
        this.roles = Arrays.asList(adminRoles);
    }

    public void quitarAdmin () {
        Iterator<GrantedAuthority> roles = this.roles.iterator();
        while (roles.hasNext())
        {
            GrantedAuthority rol = roles.next();
            if (rol.getAuthority().equals("ROLE_ADMIN"))
                roles.remove();
        }
    }

    public void darAdmin () {
        GrantedAuthority rol = new SimpleGrantedAuthority("ROLE_ADMIN");
        this.roles.add(rol);
    }

    public List<Pelicula> getFavoritas() {
        return favoritas;
    }

    public void setFavoritas(List<Pelicula> favoritas) {
        this.favoritas = favoritas;
    }

    public void addFavorita (Pelicula fav) {
        this.favoritas.add(fav);
    }

    public boolean removeFavorita (Pelicula fav) {
        Iterator<Pelicula> it = favoritas.iterator();
        Pelicula x;
        while (it.hasNext())
        {
            x = it.next();
            if (x.getNombrePelicula().equals(fav.getNombrePelicula())) {
                favoritas.remove(x);
                return true;
            }
        }
        return false;
    }

    public boolean contiene (List<Pelicula> list, Pelicula p)
    {
        Iterator<Pelicula> it = favoritas.iterator();
        Pelicula x;
        while (it.hasNext())
        {
            x = it.next();
            if (x.getNombrePelicula().equals(p.getNombrePelicula()))
                return true;
        }
        return false;
    }

    public void setPassword(String password) {
        if (!password.equals(""))
            this.password = new BCryptPasswordEncoder().encode(password);
    }

    public void setPasswordHashed (String passwordHashed) {
        this.password = passwordHashed;
    }

    public String getPassword() { return password; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(List<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public List<GrantedAuthority> getRoles() {
        return roles;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long id) {
        this.idUsuario = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

}

