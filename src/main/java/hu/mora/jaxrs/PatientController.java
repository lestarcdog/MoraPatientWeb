package hu.mora.jaxrs;

import hu.mora.model.LoginTherapist;
import hu.mora.model.Patient;
import hu.mora.service.PatientService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class PatientController {

    @Inject
    PatientService patientService;

    @GET
    @Path("/therapists")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LoginTherapist> allTherapist() {
        return patientService.allTherapists();
    }

    @GET
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> allPatients() {
        return patientService.allPatients();
    }


    @POST
    @Path("/therapist")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveLoginTherapist(LoginTherapist therapist) {
        patientService.saveTherapist(therapist);
    }

    @POST
    @Path("/patient")
    @Consumes(MediaType.APPLICATION_JSON)
    public void savePatient(Patient patient) {
        patientService.savePatient(patient);
    }

    @DELETE
    @Path("/therapist/{id}")
    public void deleteTherapist(@PathParam("id") Integer id) {
        patientService.removeTherapist(id);
    }

    @DELETE
    @Path("/patient/{id}")
    public void deletePatient(@PathParam("id") Integer id) {
        patientService.removePatient(id);
    }
}
