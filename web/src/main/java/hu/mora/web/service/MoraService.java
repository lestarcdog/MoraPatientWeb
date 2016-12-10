package hu.mora.web.service;

import hu.mora.web.dao.MoraDao;
import hu.mora.web.exception.MoraException;
import hu.mora.web.model.HunCity;
import hu.mora.web.model.Patient;
import hu.mora.web.model.Therapist;
import hu.mora.web.model.Therapy;
import hu.mora.web.model.view.ListPatientDto;
import hu.mora.web.model.view.TherapiesSaveDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Stateless
public class MoraService {

    private static final Logger LOG = LoggerFactory.getLogger(MoraService.class);

    @Inject
    MoraDao moraDao;

    public List<ListPatientDto> allPatients() {
        return moraDao.getAllPatients().stream()
                .map(ListPatientDto::new).collect(Collectors.toList());
    }

    public List<Therapist> allTherapists() {
        return moraDao.getAllTherapists();
    }

    public Patient findPatient(Integer id) {
        Optional<Patient> patient = moraDao.findPatient(id);
        return patient.orElseThrow(() -> new MoraException("Nem létező beteg"));
    }

    public void saveTherapist(Therapist therapist) {
        LOG.info("Saving new therapist {}", therapist);
        Optional<Therapist> existingTherapist = moraDao.findTherapistByName(therapist.getName());
        if (existingTherapist.isPresent()) {
            //maybe it is not visible
            if (existingTherapist.get().getVisible()) {
                throw new MoraException(format("Már létezik terapeuta a %s névvel", therapist.getName()));
            } else {
                //make visible therapist again
                existingTherapist.get().setVisible(true);
            }
        } else {
            moraDao.saveTherapist(therapist);
        }

    }

    public void saveTherapies(Integer patientId, TherapiesSaveDto save) {
        Patient patient = moraDao.findPatient(patientId).orElseThrow(() -> new MoraException("Nem létező beteg."));

        Set<Therapy> therapies = save.getTherapies().stream().map(t -> t.toTherapyEntity(moraDao)).collect(Collectors.toSet());
        patient.setTherapies(therapies);
    }

    public Integer savePatient(@NotNull Patient patient) {
        LOG.info("Saving patient {}", patient);
        return moraDao.savePatient(patient);
    }

    public void removeTherapist(Integer id) {
        LOG.info("Remove therapist by id {}", id);
        moraDao.removeTherapist(id);
    }

    public void removePatient(Integer id) {
        LOG.info("Remove patient by id {}", id);
        moraDao.removePatient(id);
    }

    public List<HunCity> hunCities() {
        return moraDao.hunCities();
    }

    public Patient getPatientById(Integer patientId) {
        return moraDao.findPatient(patientId).orElseThrow(() -> new MoraException("A beteg nem létezik."));
    }
}
