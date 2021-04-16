package com.ci0156.ejemploclase.apisrest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDto {
  private long id;
  private String firstName;
  private String lastName;
  private String email;
}
