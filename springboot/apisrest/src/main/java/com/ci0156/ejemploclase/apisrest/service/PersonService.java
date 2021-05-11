package com.ci0156.ejemploclase.apisrest.service;

import com.ci0156.ejemploclase.apisrest.dto.*;

import java.util.List;

public interface PersonService {
  PersonDto getPersonById(long id);
  PersonDto getByEmail(String email);
  PersonDto savePerson(PersonDto p);
  List<PersonDto> getAll();

  ApiResponse createUser(SignupDto signupDto);
  LoginResponse signIn(LoginCredentials loginCredentials);
  LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
