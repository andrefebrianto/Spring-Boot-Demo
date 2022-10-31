CREATE TABLE demo_user_role (
    user_id BIGINT NOT NULL REFERENCES demo_user,
    role_id BIGINT NOT NULL REFERENCES demo_role,
    PRIMARY KEY (user_id, role_id)
);

