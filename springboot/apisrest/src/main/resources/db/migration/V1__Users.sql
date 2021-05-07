CREATE TABLE users(
  id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  email VARCHAR(100),
  created TIMESTAMP DEFAULT now(),
  updated TIMESTAMP DEFAULT now() on UPDATE now()
)

