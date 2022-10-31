CREATE TABLE demo_file (
    id UUID,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(128) NOT NULL,
    data BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NULL,
    updated_by BIGINT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);