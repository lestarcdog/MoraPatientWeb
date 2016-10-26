package hu.mora.jaxrs;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RestExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return Response.serverError().type(MediaType.APPLICATION_JSON).entity(new ExceptionDetail(exception.getMessage())).build();
    }
}
