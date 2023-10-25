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
    id integer PRIMARY KEY DEFAULT nextval('global_seq'),
    datetime timestamp without time zone NOT NULL,
    description text COLLATE pg_catalog."default",
    calories integer NOT NULL,
    user_id integer,
    CONSTRAINT "meal_datetime_per_userId" UNIQUE (datetime, user_id),
    CONSTRAINT user_id_meal_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS meals
    OWNER to "user";
-- Index: id_and_datetime_index


DROP INDEX IF EXISTS id_and_datetime_index;

CREATE UNIQUE INDEX IF NOT EXISTS id_and_datetime_index
    ON meals (id ASC NULLS LAST, datetime ASC NULLS LAST);