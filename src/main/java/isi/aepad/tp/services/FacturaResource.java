package isi.aepad.tp.services;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import isi.aepad.tp.modelo.Factura;
import isi.aepad.tp.util.InterceptorAcceso;

@Singleton
@Path("factura")
@Dependent
@Interceptors(InterceptorAcceso.class)
public class FacturaResource {

	@POST
	public Response facturar(String f) {
		try {
			System.out.println("FACTURA ::: "+ f);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok("factura creada").build();
	}
	
	
}
