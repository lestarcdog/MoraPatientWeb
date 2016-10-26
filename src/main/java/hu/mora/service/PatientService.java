package hu.mora.service;

import hu.mora.dao.PatientDao;
import hu.mora.exception.MoraException;
import hu.mora.model.LoginTherapist;
import hu.mora.model.Patient;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

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

    public Patient findPatient(Integer id) {
        Optional<Patient> patient = patientDao.findPatient(id);
        return patient.orElseThrow(() -> new MoraException("Nem létező beteg"));
    }

    public void saveTherapist(LoginTherapist therapist) {
        Optional<LoginTherapist> existingTherapist = patientDao.findTherapistByName(therapist.getName());
        if (!existingTherapist.isPresent()) {
            patientDao.saveTherapist(therapist);
        } else {
            throw new MoraException(format("Már létezik terapeuta a %s névvel", therapist.getName()));
        }

    }

    public void savePatient(@NotNull Patient patient) {
        patientDao.savePatient(patient);
    }

    public void removeTherapist(Integer id) {
        patientDao.removeTherapist(id);
    }

    public void removePatient(Integer id) {
        patientDao.removePatient(id);
    }
}
