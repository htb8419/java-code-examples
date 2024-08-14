--liquibase formatted sql
--changeset h.tayebi:1 labels:example-label context:example-context
--comment: example comment
alter table public.person add column country varchar(2)
--rollback ALTER TABLE person DROP COLUMN country;