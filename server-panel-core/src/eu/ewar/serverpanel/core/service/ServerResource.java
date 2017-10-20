package eu.ewar.serverpanel.core.service;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/server")
public class ServerResource {
    
    @GET
    @Path("status")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRestfulStatus() {
        return "RESTful Service is running" + new Date().toString();
    }
}
