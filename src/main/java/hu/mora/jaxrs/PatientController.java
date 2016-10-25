package hu.mora.jaxrs;

import hu.mora.model.LoginTherapist;
import hu.mora.model.Patient;
import hu.mora.service.PatientService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
