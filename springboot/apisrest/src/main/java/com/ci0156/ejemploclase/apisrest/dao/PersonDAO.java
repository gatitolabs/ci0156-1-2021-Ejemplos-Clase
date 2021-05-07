package com.ci0156.ejemploclase.apisrest.dao;

import com.ci0156.ejemploclase.apisrest.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDAO extends JpaRepository<Person, Long> {
  Person findPersonByEmail(String email);
}
