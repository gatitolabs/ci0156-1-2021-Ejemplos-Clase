package com.ci0156.ejemploclase.apisrest.dao;

import com.ci0156.ejemploclase.apisrest.entity.Role;
import com.ci0156.ejemploclase.apisrest.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleEnum name);
}
