package com.ci0156.ejemploclase.apisrest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Table(name = "users")
@Data
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  // ? truco para usar los valores default definidos en la base de datos
  @Column(name = "created", insertable = false, updatable = false)
  private Date createDate;

  @Column(name = "updated", insertable = false, updatable = false)
  private Date lastUpdateDate;


}
