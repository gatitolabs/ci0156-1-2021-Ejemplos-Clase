package com.ci0156.ejemploclase.apisrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
  private boolean success;
  private String msg;
}
