package dev.amauryortega.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class Logging implements ContainerRequestFilter {
    private static final Logger LOG = Logger.getLogger(Logging.class);

    @Context
    UriInfo uriInfo;

    @Context
    HttpServletRequest httpServletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        final String method = requestContext.getMethod();
        final String path = uriInfo.getPath();
        final String address = httpServletRequest.getRemoteAddr();
        LOG.infof("Request %s %s from IP %s", method, path, address);
    }
}