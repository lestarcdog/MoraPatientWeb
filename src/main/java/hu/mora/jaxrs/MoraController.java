package hu.mora.jaxrs;

import hu.mora.model.HunCity;
import hu.mora.model.Patient;
import hu.mora.model.Therapist;
import hu.mora.model.Therapy;
import hu.mora.model.view.ListPatient;
import hu.mora.service.MoraService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoraController {

    @Inject
    MoraService moraService;

    @GET
    @Path("/therapists")
    public List<Therapist> allTherapist() {
        return moraService.allTherapists();
    }

    @GET
    @Path("/patients")
    public List<ListPatient> allPatients() {
        return moraService.allPatients();
    }

    @POST
    @Path("/therapist")
    public void saveLoginTherapist(Therapist therapist) {
        moraService.saveTherapist(therapist);
    }

    @POST
    @Path("/patient")
    public void savePatient(Patient patient) {
        moraService.savePatient(patient);
    }

    @GET
    @Path("/patient/{id}")
    public Patient patientById(@PathParam("id") Integer patientId) {
        return moraService.getPatientById(patientId);
    }

    @DELETE
    @Path("/therapist/{id}")
    public void deleteTherapist(@PathParam("id") Integer id) {
        moraService.removeTherapist(id);
    }

    @DELETE
    @Path("/patient/{id}")
    public void deletePatient(@PathParam("id") Integer id) {
        moraService.removePatient(id);
    }


    @GET
    @Path("/hunCities")
    public List<HunCity> hunCities() {
        return moraService.hunCities();
    }

    @GET
    @Path("/patient/{id}/therapies")
    public List<Therapy> patientTherapies(@PathParam("id") Integer patientId) {
        return moraService.therapiesForPatient(patientId);
    }
}
