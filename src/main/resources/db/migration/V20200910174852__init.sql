CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR (200) NOT NULL,
    building VARCHAR (10) NOT NULL
);

INSERT INTO clients(
    id,
    name
)

VALUES (
    1, 'Alex'
),
(
    2, 'Sergei'
),
(
    3, 'Vladimir'
),
(
    4, 'Oleg'
),
(
    5, 'Ivan'
),
(
    6, 'Dmitriy'
);



INSERT INTO addresses (
    id,
    client_id,
    city,
    street,
    building
)

VALUES (
    1, 1, 'Moscow', 'Tverskaya', '1'
),
(
    2, 2, 'Moscow', 'Mohovaya', '4'
),
(
    3, 3, 'St.Petersburg', 'Nevskiy', '23'
),
(
    4, 4, 'St.Petersburg', 'Dvortsovaya', '17'
),
(
    5, 5, 'Tver', 'Lenina', '43'
),
(
    6, 6, 'Stavropol', 'Pobedy', '7'
);

