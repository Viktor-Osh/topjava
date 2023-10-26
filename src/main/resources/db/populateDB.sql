DELETE
FROM user_role;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2023-10-22 10:00:00', 'завтрак usera', 300, 100000),
       ('2023-10-22 13:00:00', 'обед usera', 1000, 100000),
       ('2023-10-22 19:00:00', 'ужин usera', 700, 100000),
       ('2023-10-23 11:00:00', 'завтрак usera', 400, 100000),
       ('2023-10-23 14:00:00', 'обед usera', 1000, 100000),
       ('2023-10-23 20:00:00', 'ужин usera', 500, 100000),
       ('2023-10-22 10:00:00', 'завтрак admina', 301, 100001),
       ('2023-10-22 13:00:00', 'обед admina', 1001, 100001),
       ('2023-10-22 19:00:00', 'ужин admina', 701, 100001),
       ('2023-10-23 11:00:00', 'завтрак admina', 401, 100001),
       ('2023-10-23 14:00:00', 'обед admina', 1000, 100001),
       ('2023-10-23 20:00:00', 'ужин admina', 500, 100001);