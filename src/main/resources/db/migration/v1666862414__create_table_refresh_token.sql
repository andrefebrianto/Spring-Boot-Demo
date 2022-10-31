CREATE TABLE demo_refresh_token (
    id UUID,
    user_id BIGINT NOT NULL REFERENCES demo_user,
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX unique_refresh_token_on_token ON demo_refresh_token (token);