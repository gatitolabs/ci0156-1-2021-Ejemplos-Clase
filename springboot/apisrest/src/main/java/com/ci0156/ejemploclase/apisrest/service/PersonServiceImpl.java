package com.ci0156.ejemploclase.apisrest.service;

import com.ci0156.ejemploclase.apisrest.dao.PersonDAO;
import com.ci0156.ejemploclase.apisrest.dto.PersonDto;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService{

  private final PersonDAO personDAO;

  public PersonServiceImpl(PersonDAO personDAO){
    this.personDAO = personDAO;
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
}
