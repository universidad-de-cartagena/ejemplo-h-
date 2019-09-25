package dev.amauryortega;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class ExampleResource {

    @ConfigProperty(name = "MI_MENSAJE", defaultValue = "PorDefecto")
    String mensaje;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello" + " " + mensaje;
    }
}
