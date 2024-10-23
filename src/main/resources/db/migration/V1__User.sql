CREATE TABLE users
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL,
    password VARCHAR NOT NULL,
    user_role VARCHAR DEFAULT 'USER'
);

CREATE INDEX idx_users_username ON users(username);