package org.jboss.resteasy.reactive.server.providers.serialisers.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.server.spi.ResteasyReactiveResourceInfo;
import org.jboss.resteasy.reactive.server.spi.ServerMessageBodyReader;

public abstract class AbstractJsonMessageBodyReader implements ServerMessageBodyReader<Object> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isReadable(mediaType, type);
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, ResteasyReactiveResourceInfo lazyMethod, MediaType mediaType) {
        return isReadable(mediaType, type);
    }

    private boolean isReadable(MediaType mediaType, Class<?> type) {
        if (mediaType == null) {
            return false;
        }
        if (String.class.equals(type)) { // don't attempt to read plain strings
            return false;
        }
        String subtype = mediaType.getSubtype();
        boolean isApplicationMediaType = "application".equals(mediaType.getType());
        return (isApplicationMediaType && "json".equalsIgnoreCase(subtype) || subtype.endsWith("+json"))
                || (mediaType.isWildcardSubtype() && (mediaType.isWildcardType() || isApplicationMediaType));
    }
}
