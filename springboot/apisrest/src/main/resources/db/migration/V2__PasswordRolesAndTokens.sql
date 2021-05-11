# Se agrega la columna password a la tabla usuario
ALTER TABLE users ADD COLUMN password VARCHAR(100) DEFAULT null AFTER email;

# Tabla donde tendremos almacenados los tokens de autenticacion
CREATE TABLE auth_tokens (
 id INT PRIMARY KEY AUTO_INCREMENT,
 user_id INT,
 token VARCHAR(255),
 refresh VARCHAR(255),
 refresh_expiration TIMESTAMP,
 valid TINYINT(1), # Boolean
 created TIMESTAMP DEFAULT now(),
 updated TIMESTAMP DEFAULT now() on UPDATE now(),
 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

# Roles de usuario
CREATE TABLE roles(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255)
);

# Roles predefinidos
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');


# Table N:M (un usuario puede tener varios roles)
create table user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT user_roles_user foreign key (user_id) references users(id),
    CONSTRAINT user_roles_product foreign key (role_id) references roles(id),
    CONSTRAINT user_roles_unique UNIQUE (user_id, role_id)
);
