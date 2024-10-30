create table KEYCLOAK.EMPLOYEE
(
    ID         NUMBER(10) not null
        constraint EMPLOYEE_PK primary key,
    NAME       VARCHAR2(100),
    DEPARTMENT VARCHAR2(100),
    EMAIL      VARCHAR2(100),
    SALARY     NUMBER(8, 5)
)