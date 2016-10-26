package hu.mora.dao;

import hu.mora.exception.MoraException;
import hu.mora.model.LoginTherapist;
import hu.mora.model.Patient;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class PatientDao {

    @PersistenceContext(unitName = "MoraPU")
    private EntityManager em;


    public List<LoginTherapist> getAllTherapists() {
        return em.createQuery("SELECT t FROM LoginTherapist t", LoginTherapist.class).getResultList();
    }

    public Optional<Patient> findPatient(@NotNull Integer id) {
        return Optional.ofNullable(em.find(Patient.class, id));
    }

    public void removePatient(@NotNull Integer id) {
        Optional<Patient> patient = findPatient(id);
        em.remove(patient.orElseThrow(() -> new MoraException("Nem létező beteg. Nem törölhető.")));
    }

    public void savePatient(Patient patient) {
        em.merge(patient);
    }

    public void saveTherapist(LoginTherapist therapist) {
        em.persist(therapist);
    }

    public Optional<LoginTherapist> findTherapistByName(@NotNull String name) {
        TypedQuery<LoginTherapist> q = em.createQuery("SELECT t FROM LoginTherapist t WHERE t.name = :name", LoginTherapist.class);
        q.setParameter("name", name);
        List<LoginTherapist> result = q.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));

    }

    public List<Patient> getAllPatients() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }

    public void removeTherapist(@NotNull Integer id) {
        LoginTherapist therapist = em.find(LoginTherapist.class, id);
        if (therapist != null) {
            em.remove(therapist);
        } else {
            throw new MoraException("Nem létező terapeuta. Nem törölhető.");
        }

    }
}
