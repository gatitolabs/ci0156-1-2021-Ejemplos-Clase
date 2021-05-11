package com.ci0156.ejemploclase.apisrest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {
  private String email;
  private String refreshToken;
}
