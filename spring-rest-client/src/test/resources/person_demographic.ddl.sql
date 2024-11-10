CREATE TABLE PERSON_DEMOGRAPHIC
(
    id              NUMBER(19) GENERATED ALWAYS as IDENTITY (START with 1 INCREMENT by 1),
    record_id       VARCHAR2(30),
    admission_date  VARCHAR2(10),
    organization    NVARCHAR2(100),
    medical_center  NVARCHAR2(512),
    first_name      NVARCHAR2(50),
    last_name       NVARCHAR2(50),
    father_name     NVARCHAR2(50),
    gender          VARCHAR2(10),
    person_id_type  VARCHAR2(20),
    person_id_no    VARCHAR2(20),
    birth_date      VARCHAR2(10),
    marital_status  VARCHAR2(20),
    children        NUMBER(5),
    education_level NVARCHAR2(80),
    mobile          VARCHAR2(15)
);
