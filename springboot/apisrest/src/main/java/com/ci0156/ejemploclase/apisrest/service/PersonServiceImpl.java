package com.ci0156.ejemploclase.apisrest.service;

import com.ci0156.ejemploclase.apisrest.dto.PersonDto;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService{

  @Override
  public PersonDto getPersonById(long id) {
    log.info("Me pidieron la persona con id: " + id);

    return this.generateRandomPerson(id);
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
