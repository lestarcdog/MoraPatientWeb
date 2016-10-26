package hu.mora.jaxrs;

import hu.mora.model.Patient;
import hu.mora.model.Therapist;
import hu.mora.service.MoraService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class MoraController {

    @Inject
    MoraService moraService;

    @GET
    @Path("/therapists")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Therapist> allTherapist() {
        return moraService.allTherapists();
    }

    @GET
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> allPatients() {
        return moraService.allPatients();
    }


    @POST
    @Path("/therapist")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveLoginTherapist(Therapist therapist) {
        moraService.saveTherapist(therapist);
    }

    @POST
    @Path("/patient")
    @Consumes(MediaType.APPLICATION_JSON)
    public void savePatient(Patient patient) {
        moraService.savePatient(patient);
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
}
