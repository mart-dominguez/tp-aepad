package isi.aepad.tp.services;

import java.util.List;

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

import com.codahale.metrics.annotation.Timed;

import isi.aepad.tp.modelo.Usuario;
import isi.aepad.tp.util.InterceptorAcceso;

@Singleton
@Path("usuario")
@Dependent
@Interceptors(InterceptorAcceso.class)
@Timed
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
	@Path("lista")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Usuario> loginXml() {
		List<Usuario> usrs=null;
		try {
			System.out.println("VIENE");
			usrs =em.createQuery("SELECT u FROM Usuario u")
					//.setFirstResult(30)
					//.setMaxResults(20)
					.getResultList();
			System.out.println(usrs.toString());

		}catch(Exception e) {
			e.printStackTrace();
		}
		return usrs;
	}
}
