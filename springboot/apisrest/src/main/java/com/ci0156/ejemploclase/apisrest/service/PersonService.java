package com.ci0156.ejemploclase.apisrest.service;

import com.ci0156.ejemploclase.apisrest.dto.PersonDto;

import java.util.List;

public interface PersonService {
  PersonDto getPersonById(long id);
  PersonDto getByEmail(String email);
  PersonDto savePerson(PersonDto p);
  List<PersonDto> getAll();
}
