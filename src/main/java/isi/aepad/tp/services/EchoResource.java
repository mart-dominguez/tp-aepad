package isi.aepad.tp.services;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import isi.aepad.tp.util.InterceptorAcceso;

@Singleton
@Path("echo")
@Dependent
@Interceptors(InterceptorAcceso.class)
public class EchoResource {

    @Context
    private UriInfo context;


    /**
     * Retrieves representation of an instance of Echo
     * @return an instance of String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
    	return "MILLIS: "+System.currentTimeMillis();
    }


}