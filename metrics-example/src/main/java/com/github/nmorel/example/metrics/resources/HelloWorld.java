package com.github.nmorel.example.metrics.resources;

/**
 * @author Nicolas Morel
 */

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Timed
    public String hello(@QueryParam("name") String name) {
        return "Hello " + name + "!";
    }

}
