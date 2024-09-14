--liquibase formatted sql
--changeset h.tayebi:2 labels:example-label context:example-context
--comment: example comment
alter table public.person add father_name varchar(60);