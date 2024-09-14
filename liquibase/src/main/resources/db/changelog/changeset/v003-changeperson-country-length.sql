--liquibase formatted sql
--changeset h.boromand:3 labels:example-label context:example-context
--comment: example comment
alter table public.person
    alter column country type varchar(20) using country::varchar(20);