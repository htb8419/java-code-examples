--liquibase formatted sql
--changeset hr.khajavi:4 labels:example-label context:example-context
--comment: example comment
insert into public.person (id, name, address1, address2, city,country,father_name)
VALUES  (1,'hadi','address-1',null,'behbahan','iran','mohammad');

insert into public.person (id, name, address1, address2, city,country,father_name)
VALUES  (2,'ali','address-1',null,'isfahan','iran','ahmad');