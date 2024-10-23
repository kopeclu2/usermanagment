INSERT INTO users (id, name, username, password, user_role)
VALUES
    (123123, 'Lukas Kopecky', 'kopeclu2', '$2a$16$aeUvsuCA9.rGWzvvJN3Px.M8ANfKbschI0LB6e6an0oLAuhnNBJ2G', 'ADMIN')
on conflict (id) do nothing;

INSERT INTO users (id, name, username, password, user_role)
VALUES
    (2342342, 'Tomas Kopecky', 'tomas1', '$2a$16$aeUvsuCA9.rGWzvvJN3Px.M8ANfKbschI0LB6e6an0oLAuhnNBJ2G', 'USER')
on conflict (id) do nothing;

