package com.ci0156.ejemploclase.apisrest.entity;

import lombok.Data;

import javax.persistence.*;

@Entity()
@Table(name = "roles")
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long  id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name")
  private RoleEnum name;
}
