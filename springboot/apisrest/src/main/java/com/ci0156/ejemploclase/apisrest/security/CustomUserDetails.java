package com.ci0156.ejemploclase.apisrest.security;

import com.ci0156.ejemploclase.apisrest.dao.PersonDAO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetailsService {

  private final PersonDAO personDAO;

  public CustomUserDetails(PersonDAO personDAO){
    this.personDAO = personDAO;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    var storedPerson = personDAO.findPersonByEmail(s);

    if(null != storedPerson){
      return User.withUsername(storedPerson.getEmail())
        .password(storedPerson.getPassword())
        .authorities(storedPerson.getRoles().stream()
          .map(role -> new SimpleGrantedAuthority(role.getName().name()))
          .collect(Collectors.toList()))
        .accountExpired(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
    }

    throw new UsernameNotFoundException("Usuario '" + s + "' no encontrado");
  }
}
