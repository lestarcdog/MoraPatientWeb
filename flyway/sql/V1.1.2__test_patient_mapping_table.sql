CREATE TABLE ELHELEMENTS_PATIENTS (
  ID              INT AUTO_INCREMENT PRIMARY KEY,
  ELHELEMENTS_ID  INT,
  PATIENT_ID     INT,

  FOREIGN KEY (PATIENT_ID) REFERENCES PATIENTS (ID),
  FOREIGN KEY (ELHELEMENTS_ID) REFERENCES ELHELEMENTS (ID),
  UNIQUE (ELHELEMENTS_ID,PATIENT_ID)
);

