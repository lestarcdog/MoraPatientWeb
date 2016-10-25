package hu.mora.dao;

import hu.mora.model.LoginTherapist;
import hu.mora.model.Patient;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PatientDao {

    @PersistenceContext(unitName = "MoraPU")
    private EntityManager em;


    public List<LoginTherapist> getAllTherapists() {
        return em.createQuery("SELECT t FROM LoginTherapist t", LoginTherapist.class).getResultList();
    }

    public List<Patient> getAllPatients() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }
}
