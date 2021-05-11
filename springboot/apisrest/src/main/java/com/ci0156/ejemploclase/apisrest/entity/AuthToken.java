package com.ci0156.ejemploclase.apisrest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Table(name = "auth_tokens")
@Data
public class AuthToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long  id;

  @Column(name = "token")
  private String token;

  @Column(name = "refresh")
  private String refresh;

  @Column(name = "valid")
  private Boolean valid;

  @Column(name = "refresh_expiration")
  private Date refreshExpirationDate;

  @Column(name = "created", insertable = false, updatable = false)
  private Date createDate;

  @Column(name = "updated", insertable = false, updatable = false)
  private Date lastUpdateDate;

  @ManyToOne
  @JoinColumn(name="user_id", nullable=false)
  private Person person;
}
