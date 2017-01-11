package hu.mora.web.jaxrs;

import hu.mora.web.model.novadb.beap.Ergebnis;
import hu.mora.web.model.novadb.beap.Person;
import hu.mora.web.service.novadb.NovaBeapService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/nova")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NovaController {

    @Inject
    NovaBeapService beapService;

    @GET
    @Path("/allNovaPatient")
    public List<Person> listAllPerson() {
        return beapService.allPatients();
    }

    @GET
    @Path("/patient/{id}/result")
    public List<Ergebnis> resultOfPatient(@PathParam("id") Integer id) {
        return beapService.resultsOfPatient(id);
    }
}
