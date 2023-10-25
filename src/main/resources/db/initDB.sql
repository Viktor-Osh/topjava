DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table: public.meals

CREATE TABLE IF NOT EXISTS meals
(
    id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    datetime TIMESTAMP NOT NULL,
    description TEXT NOT NULL ,
    calories INTEGER NOT NULL,
    user_id INTEGER,
    CONSTRAINT meal_datetime_per_user_id UNIQUE (datetime, user_id),
    CONSTRAINT user_id_meal_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE ON DELETE CASCADE
);
-- Index: user_id_and_datetime_index

DROP INDEX IF EXISTS user_id_and_datetime_index;

CREATE UNIQUE INDEX IF NOT EXISTS user_id_and_datetime_index
    ON meals (user_id ASC NULLS LAST, datetime ASC NULLS LAST);