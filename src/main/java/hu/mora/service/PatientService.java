package hu.mora.service;

import hu.mora.dao.PatientDao;
import hu.mora.model.LoginTherapist;
import hu.mora.model.Patient;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PatientService {

    @Inject
    PatientDao patientDao;

    public List<Patient> allPatients() {
        return patientDao.getAllPatients();
    }

    public List<LoginTherapist> allTherapists() {
        return patientDao.getAllTherapists();
    }
}
