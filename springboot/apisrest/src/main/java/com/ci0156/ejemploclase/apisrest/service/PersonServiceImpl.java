package com.ci0156.ejemploclase.apisrest.service;

import com.ci0156.ejemploclase.apisrest.dao.AuthTokenDAO;
import com.ci0156.ejemploclase.apisrest.dao.PersonDAO;
import com.ci0156.ejemploclase.apisrest.dao.RoleDAO;
import com.ci0156.ejemploclase.apisrest.dto.*;
import com.ci0156.ejemploclase.apisrest.entity.AuthToken;
import com.ci0156.ejemploclase.apisrest.entity.Person;
import com.ci0156.ejemploclase.apisrest.entity.Role;
import com.ci0156.ejemploclase.apisrest.entity.RoleEnum;
import com.ci0156.ejemploclase.apisrest.exception.BadRequestException;
import com.ci0156.ejemploclase.apisrest.security.JwtTokenProvider;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService{

  private final PersonDAO personDAO;
  private final RoleDAO roleDAO;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthTokenDAO authTokenDAO;

  public PersonServiceImpl(PersonDAO personDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                           AuthTokenDAO authTokenDAO){
    this.personDAO = personDAO;
    this.roleDAO = roleDAO;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authTokenDAO = authTokenDAO;
  }

  @Override
  public PersonDto getPersonById(long id) {
    log.info("Me pidieron la persona con id: " + id);

    var personInDatabase = this.personDAO.findById(id);

    if(personInDatabase.isPresent()){
      // se encontro el person en la DB
      return PersonDto.builder()
        .firstName(personInDatabase.get().getFirstName())
        .lastName(personInDatabase.get().getLastName())
        .email(personInDatabase.get().getEmail())
        .id(personInDatabase.get().getId()).build();

    }
    else{
      // si no lo encuentra, regreso un PersonDto vacio
      return PersonDto.builder().build();
    }

  }

  @Override
  public PersonDto getByEmail(String email){
    log.info("Buscando person con email: " + email);
    var personInDatabase = this.personDAO.findPersonByEmail(email);
    if(null != personInDatabase){
      return PersonDto.builder()
        .firstName(personInDatabase.getFirstName())
        .lastName(personInDatabase.getLastName())
        .email(personInDatabase.getEmail())
        .id(personInDatabase.getId()).build();
    }

    return PersonDto.builder().build();
  }

  @Override
  public PersonDto savePerson(PersonDto p) {
    Faker faker = new Faker();
    p.setId(faker.number().randomNumber());
    log.info("Salvando Person: " + p.toString());
    return p;
  }

  @Override
  public List<PersonDto> getAll() {
    log.info("Lista de personas");
    List<PersonDto> persons = new ArrayList<>();

    for(long i = 1; i<=10; i++){
      persons.add(this.generateRandomPerson(i));
    }

    return persons;
  }

  private PersonDto generateRandomPerson(long id){
    Faker faker = new Faker();

    return PersonDto.builder()
      .firstName(faker.name().firstName())
      .lastName(faker.name().lastName())
      .email(faker.internet().emailAddress())
      .id(id)
      .build();
  }

  @Override
  public ApiResponse createUser(SignupDto signupDto){

    // Todos los usuarios se crean con role = USER
    var userRole = this.roleDAO.findByName(RoleEnum.USER);
    Set<Role> userRoles = new HashSet<Role>(Collections.singletonList(userRole.get()));

    Person newPerson = new Person();
    newPerson.setEmail(signupDto.getUsername());
    newPerson.setFirstName(signupDto.getFirstName());
    newPerson.setLastName(signupDto.getLastName());
    newPerson.setRoles(userRoles);

    // Encriptamos la contraseña que nos mando el usuario
    var encryptedPassword = this.passwordEncoder.encode(signupDto.getPassword());
    newPerson.setPassword(encryptedPassword);

    this.personDAO.save(newPerson);

    return new ApiResponse(true, "Usuario creado");

  }

  @Override
  public LoginResponse signIn(LoginCredentials loginCredentials){
    // ! AQUI SPRING SE ENCARGA DE VALIDAR USUARIO Y PASSWORD
    // ! NOSOTROS NUNCA COMPARAMOS CONTRASEÑAS
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword()));


    // Si llega a esta linea, la autenticacion fue exitosa (Spring se encarga de todo)
    var p = this.personDAO.findPersonByEmail(authentication.getName());

    var token = this.jwtTokenProvider.createToken(p.getEmail(), p.getId());
    var refresh = this.jwtTokenProvider.buildRefreshToken();
    var refreshExpiration = this.jwtTokenProvider.getRefreshExpirationDate();
    var expire = this.jwtTokenProvider.getValidity();

    AuthToken newAuthToken = new AuthToken();
    newAuthToken.setPerson(p);
    newAuthToken.setToken(token);
    newAuthToken.setRefresh(refresh);
    newAuthToken.setRefreshExpirationDate(refreshExpiration);
    newAuthToken.setValid(true);

    this.authTokenDAO.save(newAuthToken);


    return LoginResponse.builder()
      .expire(expire)
      .refresh(refresh)
      .token(token)
      .userId(p.getId())
      .build();


  }

  @Override
  public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
    var authToken = this.authTokenDAO.findByRefresh(refreshTokenRequest.getRefreshToken());
    if(authToken.isPresent()){
      if(!authToken.get().getValid()){
        throw new BadRequestException("Token invalido. Debe autenticarse de nuevo");
      }

      Date now = new Date();
      if(now.compareTo(authToken.get().getRefreshExpirationDate()) > 0){
        throw new BadRequestException("Token vencido. Debe autenticarse de nuevo");
      }

      var p = authToken.get().getPerson();

      if(!p.getEmail().equalsIgnoreCase(refreshTokenRequest.getEmail())){
        throw new BadRequestException("Token no pertenece a este usuario");
      }

      var oldToken = authToken.get();
      oldToken.setValid(false);
      this.authTokenDAO.save(oldToken);


      var token = this.jwtTokenProvider.createToken(p.getEmail(), p.getId());
      var refresh = this.jwtTokenProvider.buildRefreshToken();
      var refreshExpiration = this.jwtTokenProvider.getRefreshExpirationDate();
      var expire = this.jwtTokenProvider.getValidity();

      AuthToken newAuthToken = new AuthToken();
      newAuthToken.setPerson(p);
      newAuthToken.setToken(token);
      newAuthToken.setRefresh(refresh);
      newAuthToken.setRefreshExpirationDate(refreshExpiration);
      newAuthToken.setValid(true);

      this.authTokenDAO.save(newAuthToken);


      return LoginResponse.builder()
        .expire(expire)
        .refresh(refresh)
        .token(token)
        .userId(p.getId())
        .build();



    }
    throw new BadRequestException("Token incorrecto");
  }
}
