CREATE TABLE patients (
  "id"           INT AUTO_INCREMENT PRIMARY KEY,
  "name"         VARCHAR(255) NOT NULL,
  "ismale"       BOOLEAN      NOT NULL,
  "birthdate"    DATE         NOT NULL,
  "birthPlace"   VARCHAR(100),
  "phone"        VARCHAR(20)  NOT NULL,
  "email"        VARCHAR(50),
  "city"         VARCHAR(50),
  "street"       VARCHAR(150),
  "lastmodified" TIMESTAMP
);

CREATE INDEX IDX_PATIENT_NAME
  ON patients ("name");