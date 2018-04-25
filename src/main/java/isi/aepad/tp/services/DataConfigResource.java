package isi.aepad.tp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

import java.util.Map;

import isi.aepad.tp.modelo.Categoria;
import isi.aepad.tp.modelo.OrdenCompra;
import isi.aepad.tp.modelo.OrdenCompraDetalle;
import isi.aepad.tp.modelo.Producto;
import isi.aepad.tp.modelo.Usuario;
import isi.aepad.tp.util.GeneradorDatos;
import isi.aepad.tp.util.InterceptorAcceso;

@Singleton
@Path("dataconfig")
@Dependent
@Interceptors(InterceptorAcceso.class)

public class DataConfigResource {

	private ArrayList<Producto> productosCreados = new ArrayList<>();
	
	@Inject
	private GeneradorDatos generador;
	
	@EJB
	private ProductoResource productoEJB;
	
	public static final String[] CATEGORIAS = { "autos", "electronica", "hogar", "tecnologia", "indumentaria",
			"deportes", "computacion", "audio", "camping", "pesca", "respuestos", "libros", "peliculas", "videos",
			"entretenimiento", "blanco", "ferreteria", "vinos", "bebidas", "video", "tv", "internet", "redes" };

	private List<Categoria> entidadesCategorias= new ArrayList<>();
	
	@PersistenceContext(unitName="AEPAD_PU")
	private EntityManager em;

	@GET
	@Path("inicializar1")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response inicializar1() {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		long millisInicio = System.currentTimeMillis();
		try {
			this.crearCategorias();
			obj.add("T_CATEGORIAS",System.currentTimeMillis()-millisInicio);
			
			millisInicio = System.currentTimeMillis();
			this.crearProductos(2500);
			obj.add("T_PPRODUCTOS",System.currentTimeMillis()-millisInicio);
			
			millisInicio = System.currentTimeMillis();
			this.crearUsuarios(3000);
			obj.add("T_USUARIOS",System.currentTimeMillis()-millisInicio);
			
			millisInicio = System.currentTimeMillis();
			this.crearOrdenCompra(5000);
			obj.add("T_ORDENES",System.currentTimeMillis()-millisInicio);
			
		}catch(Exception e) {
			obj.add("T_ERROR",System.currentTimeMillis()-millisInicio);
			obj.add("MSG_ERROR",e.getMessage());
			e.printStackTrace();
		}				
		return Response.ok(obj.build().toString()).build();
	}

	@GET
	@Path("inicializar2")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response inicializar2() {
		long millisInicio = System.currentTimeMillis();
		List<Categoria> cats= em.createQuery("SELECT c FROM Categoria c").getResultList();
		
		JsonObjectBuilder builderObj= Json.createObjectBuilder();
		builderObj.add("MILLIS_INICIO", millisInicio);
		
		JsonArrayBuilder builderArr = Json.createArrayBuilder();
		for(int i = 0;i<500;i++) {
			long millisAntes = System.currentTimeMillis();
			productoEJB.crearProductoRandom(cats);
			long millisDespues= System.currentTimeMillis();
			builderArr.add((millisDespues-millisAntes));
		}		
		long millisFin= System.currentTimeMillis();
		
		builderObj.add("MILLIS_FIN", millisFin);
		builderObj.add("Duracion", (millisFin-millisInicio));
		builderObj.add("detalle", builderArr.build());				   
		return Response.ok(builderObj.build().toString()).build();
	}

	@GET
	@Path("drop")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response destruir() {
		int pagosBorrados = em.createQuery("DELETE FROM Pago p").executeUpdate();
		int ordendetalleBorrada = em.createQuery("DELETE FROM OrdenCompraDetalle f").executeUpdate();
		int ordenBorrada = em.createQuery("DELETE FROM OrdenCompra f").executeUpdate();
		int facturaDetalleBorrada = em.createQuery("DELETE FROM FacturaDetalle f").executeUpdate();
		int facturasBorradas = em.createQuery("DELETE FROM Factura f").executeUpdate();
		int usuariosBorrados = em.createQuery("DELETE FROM Usuario u").executeUpdate();
		int productosBorrados = em.createQuery("DELETE FROM Producto p").executeUpdate();
		int categoriasBorradas = em.createQuery("DELETE FROM Categoria c").executeUpdate();


		JsonObject model = Json.createObjectBuilder()
				   .add("productosBorrados", productosBorrados)
				   .add("categoriasBorradas", categoriasBorradas)
				   .add("pagosBorrados", pagosBorrados)	
				   .add("ordendetalleBorrada", ordendetalleBorrada)	
				   .add("ordenBorrada", ordenBorrada)	
				   .add("facturaDetalleBorrada", facturaDetalleBorrada)	
				   .add("facturasBorradas", facturasBorradas)	
				   .add("usuariosBorrados", usuariosBorrados)	
				   .build();
		return Response.ok(model.toString()).build();
	}

	public Response backup() {
		return Response.ok().build();
	}

	private void crearCategorias() {		
//		Random r = new Random();
//		int indice = r.nextInt(categorias.length);
		for(String catName : CATEGORIAS) {
			Categoria cat = new Categoria();
			cat.setNombre(catName);
			em.persist(cat);
			entidadesCategorias.add(cat);
		}
	}

	private void crearProductos(Integer n) {
		Random r = new Random();
		int maxCat = 1+r.nextInt(3);
		for(int i=0;i<n;i++) {
			HashSet<Integer> catGeneradas = new HashSet<>();
			while(catGeneradas.size()<maxCat) {
				catGeneradas.add(r.nextInt(entidadesCategorias.size()));
			}
			Producto p = new Producto();
			p.setPrecio(10.0);
			p.setDescripcion(generador.generateRandomWords(3));
			List<Categoria> aux = new ArrayList<>();
			for(Integer idxcat:catGeneradas) {
				aux.add(this.entidadesCategorias.get(idxcat));
			}
			p.setCategoria(aux);
			em.persist(p);
			em.flush();
			em.refresh(p);
			productosCreados.add(p);
		}
	}
	
	private void crearOrdenCompra(Integer n) {		
		Random r = new Random();
		for(int i=0;i<n;i++) {

			int productosPorOrden  = 1+r.nextInt(6);
			OrdenCompra ordenCompra = new OrdenCompra();
			ordenCompra.setFecha(new Date());
			em.persist(ordenCompra);
			for(int j=0;j<productosPorOrden;j++) {
				Integer indice = r.nextInt(this.productosCreados.size());
				Double precioCompra = r.nextDouble()*1000;
				Producto p = em.merge(this.productosCreados.get(indice));
				p.setPrecio(precioCompra*1.25);
				OrdenCompraDetalle detalle = new OrdenCompraDetalle();
				detalle.setCantidad(r.nextInt(20));
				detalle.setPrecioUnitarioCompra(precioCompra);
				detalle.setOrden(ordenCompra);
				detalle.setProducto(p);
				em.persist(detalle);
			}
		}
	}

	private void crearUsuarios(Integer n) {		
		for(int i=0;i<n;i++) {
			Usuario usr = new Usuario();
			usr.setMail("usuario_"+i+"@mail.com");
			em.persist(usr);
		}
	}
	
}
