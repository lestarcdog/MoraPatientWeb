package hu.mora.jaxrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import javax.ws.rs.ext.ContextResolver;

public class JacksonContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper MAPPER;

    public JacksonContextResolver() {
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new Jdk8Module());
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return MAPPER;
    }

}
