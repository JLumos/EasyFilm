package upm.daw.easyfilm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import upm.daw.easyfilm.model.Usuario;
import upm.daw.easyfilm.repository.UsuarioRepository;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Usuario user = usuarioRepository.findByUser(username);

        if (user == null) {throw new BadCredentialsException("Usuario not found");}
        //Para no permitir el paso a los que estan dados de baja
        if (user.getEstado() != 0) {throw new BadCredentialsException("Usuario disabled");}
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {throw new BadCredentialsException("Wrong password");}

        List<GrantedAuthority> roles = user.getRoles();

        return new UsernamePasswordAuthenticationToken(username, password, roles);
    }

    @Override
    /* Solo permitiremos que se acceda mediante usuario y contraseña, ya que no se ha desarrollado
    ningun otro procedimiento y no queremos exponermos a un ataque DOS. */
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

