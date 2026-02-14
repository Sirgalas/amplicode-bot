-- liquibase formatted sql

-- changeset Sergalas:1771044243950-1
ALTER TABLE user_session
    ADD is_promo_activated BOOLEAN;
ALTER TABLE user_session
    ADD selected_expert_id VARCHAR(255);

