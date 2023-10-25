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
VALUES ('2023-10-22 10:00:00'::timestamp without time zone, 'завтрак usera'::text, '300'::integer, '100000'::integer),
       ('2023-10-22 13:00:00'::timestamp without time zone, 'обед usera '::text, '1000'::integer, '100000'::integer),
       ('2023-10-22 19:00:00'::timestamp without time zone, 'ужин usera '::text, '700'::integer, '100000'::integer),
       ('2023-10-23 11:00:00'::timestamp without time zone, 'завтрак usera '::text, '400'::integer, '100000'::integer),
       ('2023-10-23 14:00:00'::timestamp without time zone, 'обед usera '::text, '1000'::integer, '100000'::integer),
       ('2023-10-23 20:00:00'::timestamp without time zone, 'ужин usera '::text, '500'::integer, '100000'::integer),
       ('2023-10-22 10:00:00'::timestamp without time zone, 'завтрак admina'::text, '301'::integer, '100001'::integer),
       ('2023-10-22 13:00:00'::timestamp without time zone, 'обед admina '::text, '1001'::integer, '100001'::integer),
       ('2023-10-22 19:00:00'::timestamp without time zone, 'ужин admina '::text, '701'::integer, '100001'::integer),
       ('2023-10-23 11:00:00'::timestamp without time zone, 'завтрак admina '::text, '401'::integer, '100001'::integer),
       ('2023-10-23 14:00:00'::timestamp without time zone, 'обед admina '::text, '1000'::integer, '100001'::integer),
       ('2023-10-23 20:00:00'::timestamp without time zone, 'ужин admina '::text, '500'::integer, '100001'::integer);