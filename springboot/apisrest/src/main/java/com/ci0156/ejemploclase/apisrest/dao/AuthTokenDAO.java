package com.ci0156.ejemploclase.apisrest.dao;

import com.ci0156.ejemploclase.apisrest.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenDAO extends JpaRepository<AuthToken, Long> {
  Optional<AuthToken> findByToken(String token);
  Optional<AuthToken> findByRefresh(String refresh);
}
