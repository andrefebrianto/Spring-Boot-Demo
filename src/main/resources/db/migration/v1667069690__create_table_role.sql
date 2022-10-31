CREATE TABLE demo_role (
    id BIGSERIAL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NULL,
    updated_by BIGINT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX unique_role_on_name ON demo_role (name);