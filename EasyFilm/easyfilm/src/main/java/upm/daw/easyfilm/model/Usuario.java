package upm.daw.easyfilm.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;
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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Pelicula> favoritas;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> roles;

    public Usuario() {
        GrantedAuthority[] userRoles = { new SimpleGrantedAuthority("ROLE_USER") };
        this.roles = Arrays.asList(userRoles);
    }

    public Usuario(String user, String password, String email, List<GrantedAuthority> roles) {
        this.user = user;
        this.password = password.length() > 1 ? new BCryptPasswordEncoder().encode(password) : null;
        this.roles = roles;
        this.email = email;
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

