package com.ci0156.ejemploclase.apisrest.controller;

import com.ci0156.ejemploclase.apisrest.dto.PersonDto;
import com.ci0156.ejemploclase.apisrest.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Comentario

@RestController
@RequestMapping("/v1/persons")
public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService){
    this.personService = personService;
  }

  @GetMapping
  public List<PersonDto> getAll(){
    return this.personService.getAll();
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public PersonDto save(@RequestBody PersonDto p){
    return this.personService.savePerson(p);
  }

  @GetMapping("{id}")
  public PersonDto getPersonById(@PathVariable long id,
                                 @RequestParam(value="uppercase", defaultValue = "false") boolean uppercase){

    var result = this.personService.getPersonById(id);

    if(uppercase){
      result.setFirstName(result.getFirstName().toUpperCase());
      result.setLastName(result.getLastName().toUpperCase());
    }

    return result;
  }
}
