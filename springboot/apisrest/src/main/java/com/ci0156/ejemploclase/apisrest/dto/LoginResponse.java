package com.ci0156.ejemploclase.apisrest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
  private long userId;
  private String token;
  private String refresh;
  private long expire;
}
