package hu.mora.web.jaxrs;

import hu.mora.web.dao.ConfigDao;
import hu.mora.web.model.config.Config;
import hu.mora.web.service.config.Configs;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SettingsController {

    @Inject
    ConfigDao configDao;

    @GET
    @Path("/{name}")
    public Config getSettings(@PathParam("name") String name) {
        return configDao.getValue(Configs.valueOf(name));
    }

    @POST
    @Path("/{name}")
    public void setSetting(@PathParam("name") String name, String value) {
        configDao.setValue(Configs.valueOf(name), value);
    }
}
