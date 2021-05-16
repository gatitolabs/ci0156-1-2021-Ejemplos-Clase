package com.ci0156.ejemploclase.apisrest.controller;

import com.ci0156.ejemploclase.apisrest.dto.PersonDto;
import com.ci0156.ejemploclase.apisrest.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
  @Operation(security = {
    @SecurityRequirement(name = "userToken") // Este nombre sale del @SecurityScheme en ApisrestApplication
  })
  public List<PersonDto> getAll(){
    return this.personService.getAll();
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  @Operation(security = {
    @SecurityRequirement(name = "userToken") // Este nombre sale del @SecurityScheme en ApisrestApplication
  })
  public PersonDto save(@RequestBody PersonDto p){
    return this.personService.savePerson(p);
  }

  @GetMapping("{id}")
  @Operation(security = {
    @SecurityRequirement(name = "userToken") // Este nombre sale del @SecurityScheme en ApisrestApplication
  })
  public PersonDto getPersonById(@PathVariable long id,
                                 @RequestParam(value="uppercase", defaultValue = "false") boolean uppercase){

    var result = this.personService.getPersonById(id);

    if(uppercase){
      result.setFirstName(result.getFirstName().toUpperCase());
      result.setLastName(result.getLastName().toUpperCase());
    }

    return result;
  }


  @GetMapping("email/{email}")
  @Operation(security = {
    @SecurityRequirement(name = "userToken") // Este nombre sale del @SecurityScheme en ApisrestApplication
  })
  public PersonDto getPersonByEmail(@PathVariable String email){
    // ! Puede pasar que el email haya que decodearlo
    // URLDecoder.decode(email, "UTF-8");
    return this.personService.getByEmail(email);
  }


  @GetMapping("me")
  @Operation(security = {
    @SecurityRequirement(name = "userToken") // Este nombre sale del @SecurityScheme en ApisrestApplication
  })
  public PersonDto getCurrentUser(Principal principal){
    String email = principal.getName();
    return this.personService.getByEmail(email);
  }


  @GetMapping("supersecreto")
  @Operation(security = {
    @SecurityRequirement(name = "userToken") // Este nombre sale del @SecurityScheme en ApisrestApplication
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  public String superSecreto(Principal principal){
    return "super secreto!!!";
  }
}
