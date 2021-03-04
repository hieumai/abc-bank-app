DROP TABLE IF EXISTS vouchers;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  phone_number VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(100)
);

CREATE TABLE vouchers (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(40) NOT NULL,
  user_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (phone_number, password) VALUES
('09332229321', '$2a$10$uFBzkOSrD8bJAY.n0L8tdO4kIF.P1.jUpBDMDQJBsNFRBhoxmTfCa'),
('05922132212', null),
('21312545125', '$2a$10$uFBzkOSrD8bJAY.n0L8tdO4kIF.P1.jUpBDMDQJBsNFRBhoxmTfCa');
INSERT INTO vouchers (code, user_id) VALUES
('fa810ba5-2ec7-44d9-be92-fd99799bcb10', 1), ('834d979a-0ddc-4214-b1f4-e0ce39fce330', 1),
('df5eabe2-2e6f-42ad-835e-7bad1a8d136c', 2)