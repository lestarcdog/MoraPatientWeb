package hu.mora.dao;

import hu.mora.exception.MoraException;
import hu.mora.model.HunCity;
import hu.mora.model.Patient;
import hu.mora.model.Therapist;
import hu.mora.model.Therapy;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class MoraDao {

    @PersistenceContext(unitName = "MoraPU")
    private EntityManager em;


    public List<Therapist> getAllTherapists() {
        return em.createQuery("SELECT t FROM Therapist t WHERE t.visible = TRUE", Therapist.class).getResultList();
    }

    public Optional<Patient> findPatient(@NotNull Integer id) {
        return Optional.ofNullable(em.find(Patient.class, id));
    }

    public Optional<Therapy> findTherapy(@NotNull Integer therapyId) {
        return Optional.ofNullable(em.find(Therapy.class, therapyId));
    }

    public void removePatient(@NotNull Integer id) {
        Optional<Patient> patient = findPatient(id);
        em.remove(patient.orElseThrow(() -> new MoraException("Nem létező beteg. Nem törölhető.")));
    }

    public void savePatient(Patient patient) {
        em.merge(patient);
    }

    public void saveTherapist(Therapist therapist) {
        em.persist(therapist);
    }

    public Optional<Therapist> findTherapistByName(@NotNull String name) {
        TypedQuery<Therapist> q = em.createQuery("SELECT t FROM Therapist t WHERE t.name = :name", Therapist.class);
        q.setParameter("name", name);
        List<Therapist> result = q.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public Optional<Therapist> findTherapist(@NotNull Integer therapistId) {
        return Optional.ofNullable(em.find(Therapist.class, therapistId));
    }

    public List<Patient> getAllPatients() {
        return em.createQuery("SELECT p FROM Patient p ORDER BY p.name", Patient.class).getResultList();
    }

    public void removeTherapist(@NotNull Integer id) {
        Therapist therapist = em.find(Therapist.class, id);
        if (therapist != null) {
            therapist.setVisible(false);
        } else {
            throw new MoraException("Nem létező terapeuta. Nem törölhető.");
        }

    }

    public List<HunCity> hunCities() {
        TypedQuery<HunCity> query = em.createQuery("SELECT c FROM HunCity c ORDER BY c.name", HunCity.class);
        return query.getResultList();
    }
}
