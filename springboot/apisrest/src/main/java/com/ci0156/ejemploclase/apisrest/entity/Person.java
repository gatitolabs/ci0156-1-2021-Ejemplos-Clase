package com.ci0156.ejemploclase.apisrest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  @Column(name = "password")
  private String password;

  // ? truco para usar los valores default definidos en la base de datos
  @Column(name = "created", insertable = false, updatable = false)
  private Date createDate;

  @Column(name = "updated", insertable = false, updatable = false)
  private Date lastUpdateDate;

  @OneToMany(mappedBy="person")
  private List<AuthToken> authTokens;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(	name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
}
