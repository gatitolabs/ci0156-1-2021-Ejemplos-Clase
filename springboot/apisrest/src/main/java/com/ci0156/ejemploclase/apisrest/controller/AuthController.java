package com.ci0156.ejemploclase.apisrest.controller;

import com.ci0156.ejemploclase.apisrest.dto.*;
import com.ci0156.ejemploclase.apisrest.service.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final PersonService personService;

  public AuthController(PersonService personService){
    this.personService = personService;
  }

  @PostMapping("signup")
  public ApiResponse signUp(@RequestBody SignupDto signupDto){
    return this.personService.createUser(signupDto);
  }

  @PostMapping("login")
  public LoginResponse signIn(@RequestBody LoginCredentials loginCredentials){
    return this.personService.signIn(loginCredentials);
  }

  @PostMapping("refresh")
  public LoginResponse refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequest){
    return this.personService.refreshToken(refreshTokenRequest);
  }

}
