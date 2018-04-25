package isi.aepad.tp.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

import isi.aepad.tp.modelo.Categoria;
import isi.aepad.tp.modelo.Producto;
import isi.aepad.tp.util.GeneradorDatos;
import isi.aepad.tp.util.InterceptorAcceso;

/**
 * Session Bean implementation class ProductoResource
 */
@Stateless
@Path("producto")
@Dependent
@Interceptors(InterceptorAcceso.class)
@Timed
public class ProductoResource {

	@Inject
	private GeneradorDatos generador;

	@PersistenceContext(unitName = "AEPAD_PU")
	private EntityManager em;

	@PostConstruct
	public void init() {

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void crearProductoRandom(List<Categoria> cats) {
		Random r = new Random();
		int maxCat = 1 + r.nextInt(3);
		HashSet<Integer> catGeneradas = new HashSet<>();
		while (catGeneradas.size() < maxCat) {
			catGeneradas.add(r.nextInt(cats.size()));
		}
		Producto p = new Producto();
		p.setPrecio(0.0);
		p.setDescripcion(generador.generateRandomWords(3));
		List<Categoria> aux = new ArrayList<>();
		for (Integer idxcat : catGeneradas) {
			aux.add(cats.get(idxcat));
		}
		p.setCategoria(aux);
		em.persist(p);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> buscar(@DefaultValue("0.0") @QueryParam("precioMinimo") double minimo,
			@DefaultValue("44") @QueryParam("precioMaximo") int maximo){
		return em.createQuery("SELECT p FROM Producto p WHERE p.precio > :P_MIN AND p.precio < :P_MAX")
				.setParameter("P_MIN", minimo)
				.setParameter("P_MAX", maximo)
				.getResultList();
	}
	
	@Path("lista")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Producto> buscar2( ){
		return em.createQuery("SELECT p FROM Producto p WHERE p.id < 31").getResultList();
	}

}
