package hu.mora.web.jaxrs;

import hu.mora.web.dao.ConfigDao;
import hu.mora.web.model.novadb.NovaResult;
import hu.mora.web.model.novadb.beap.Ergebnis;
import hu.mora.web.model.novadb.beap.Person;
import hu.mora.web.service.novadb.NovaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/nova")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NovaController {

    @Inject
    NovaService novaService;

    @Inject
    ConfigDao dao;

    @GET
    @Path("/patients")
    public List<Person> listAllPerson() {
        return novaService.allPatients();
    }

    @GET
    @Path("/patient/{id}/results")
    public List<Ergebnis> resultOfPatient(@PathParam("id") Integer id) {
        return novaService.resultsOfPatient(id);
    }

    @GET
    @Path("/patient/{id}/result/{resultId}")
    public NovaResult patientResult(@PathParam("id") Integer id, @PathParam("resultId") Integer resultId) {
        return novaService.patientResult(id, resultId);
    }

    @POST
    @Path("/patient/{novaId}")
    public void joinMoraAndNovaPatient(@PathParam("novaId") Integer id, Integer moraPatientId) {

    }


}
