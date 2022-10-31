CREATE TABLE demo_user (
    id BIGSERIAL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(128) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by BIGINT NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX unique_user_on_email ON demo_user (email);
