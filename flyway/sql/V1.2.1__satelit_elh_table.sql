CREATE TABLE SATELIT_ELHELEMENT (
  ID              INT PRIMARY KEY,
  NAME_ENG        VARCHAR(255) NOT NULL,
  PARENT_ID       INT,

  FOREIGN KEY (PARENT_ID) REFERENCES SATELIT_ELHELEMENT (ID)
);

