package hu.mora.web.jaxrs;

import hu.mora.web.exception.MoraException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof MoraException) {
            LOG.warn(exception.getMessage(), exception);
        } else {
            LOG.error(exception.getMessage(), exception);
        }
        return Response.serverError().type(MediaType.APPLICATION_JSON).entity(new ExceptionDetail(exception.getMessage())).build();
    }
}
