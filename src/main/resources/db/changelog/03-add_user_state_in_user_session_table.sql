-- liquibase formatted sql

-- changeset Sergalas:1771061815953-1
ALTER TABLE user_session
    ADD user_state VARCHAR(255);

