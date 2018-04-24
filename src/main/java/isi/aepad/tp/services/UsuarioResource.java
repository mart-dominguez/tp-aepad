package isi.aepad.tp.services;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import isi.aepad.tp.modelo.Usuario;
import isi.aepad.tp.util.InterceptorAcceso;

@Singleton
@Path("usuario")
@Dependent
@Interceptors(InterceptorAcceso.class)
public class UsuarioResource {

	
	@PersistenceContext(unitName="AEPAD_PU")
	private EntityManager em;
	
	@GET
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario login(@QueryParam("id") Integer idUsr) {
		return em.find(Usuario.class, idUsr);
	}
	
	@GET
	@Path("login2")
	@Produces(MediaType.APPLICATION_XML)
	public Usuario loginXml(@QueryParam("id") Integer idUsr) {
		Usuario u =new Usuario();
		try {
			u =em.find(Usuario.class, idUsr);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return u;
	}
}
