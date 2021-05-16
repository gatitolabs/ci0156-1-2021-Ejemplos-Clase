package com.ci0156.ejemploclase.apisrest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
  info = @Info(
    title = "APIs REST CI-0156",
    description = "APIs REST del Ejemplo en Clase",
    version = "1"
  )
)

@SecurityScheme(
  name = "userToken",
  scheme = "bearer",
  bearerFormat = "JWT",
  type = SecuritySchemeType.HTTP)

@SpringBootApplication
public class ApisrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApisrestApplication.class, args);
	}

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
          .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
          .allowedOrigins("*");
      }
    };
  }

}
