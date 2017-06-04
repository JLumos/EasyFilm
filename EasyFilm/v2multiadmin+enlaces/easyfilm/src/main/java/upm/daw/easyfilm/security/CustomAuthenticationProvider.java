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

        if (user == null) {
            throw new BadCredentialsException("Usuario not found");
        }
        System.out.println(new BCryptPasswordEncoder().encode(password) + " VS " +user.getPassword());
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        List<GrantedAuthority> roles = user.getRoles();
        return new UsernamePasswordAuthenticationToken(username, password, roles);
    }

    @Override
    /* Este metodo lo usa Spring para preguntarle si se utiliza un metodo de autorizacion concreta
    que no sea user y pwd y en el getCredentials se cogeran los credenciales q sean.
    Si se pone un true y no tengo el codigo preparado para admitir otras credenciales te expones
    a una DOS
     */
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

