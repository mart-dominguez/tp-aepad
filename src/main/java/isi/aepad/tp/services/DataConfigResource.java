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

	private List<Categoria> entidadesCategorias = new ArrayList<>();

	@PersistenceContext(unitName = "AEPAD_PU")
	private EntityManager emOrig;

	@PersistenceContext(unitName = "AEPAD_BACKUP_PU")
	private EntityManager emBk;

	@GET
	@Path("setup")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response inicializar1() {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		long millisInicio = System.currentTimeMillis();
		try {
			this.crearCategorias(this.emOrig);
			obj.add("T_CATEGORIAS", System.currentTimeMillis() - millisInicio);

			millisInicio = System.currentTimeMillis();
			this.crearProductos(10000, this.emOrig);
			obj.add("T_PPRODUCTOS", System.currentTimeMillis() - millisInicio);

			millisInicio = System.currentTimeMillis();
			this.crearUsuarios(5000, this.emOrig);
			obj.add("T_USUARIOS", System.currentTimeMillis() - millisInicio);

			millisInicio = System.currentTimeMillis();
			this.crearOrdenCompra(35000, this.emOrig);
			obj.add("T_ORDENES", System.currentTimeMillis() - millisInicio);

		} catch (Exception e) {
			obj.add("T_ERROR", System.currentTimeMillis() - millisInicio);
			obj.add("MSG_ERROR", e.getMessage());
			e.printStackTrace();
		}
		return Response.ok(obj.build().toString()).build();
	}

	@GET
	@Path("backup")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response backup() {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		long millisInicio = System.currentTimeMillis();
		try {
			this.emBk.createNativeQuery("INSERT INTO `USUARIO`(`ID`, `MAIL`) SELECT ID,MAIL FROM `aepad-tp`.USUARIO;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `CATEGORIA`(`ID`, `NOMBRE`) SELECT ID,NOMBRE FROM `aepad-tp`.CATEGORIA;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `FACTURA`(`ID`, `FECHA`, `ID_CLIENTE`) SELECT ID,FECHA,ID_CLIENTE FROM `aepad-tp`.FACTURA;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `FACTURADETALLE`(`ID`, `CANTIDAD`, `ID_FACTURA`, `ID_PRODUCTO`, `PRECIOUNITARIOFACTURADO`) SELECT `ID`, `CANTIDAD`, `ID_FACTURA`, `ID_PRODUCTO`, `PRECIOUNITARIOFACTURADO` FROM `aepad-tp`.FACTURADETALLE;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `LOG_ACCESO`(`ID`, `CLASE`, `DURACION`, `METODO`, `MILLISFIN`, `MILLISINICIO`, `PARAMETROS`) SELECT`ID`, `CLASE`, `DURACION`, `METODO`, `MILLISFIN`, `MILLISINICIO`, `PARAMETROS` FROM `aepad-tp`.LOG_ACCESO;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `ORDENCOMPRA`(`ID`, `FECHA`) SELECT `ID`, `FECHA`  FROM `aepad-tp`.ORDENCOMPRA;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `ORDENCOMPRADETALLE`(`ID`, `CANTIDAD`, `PRECIOUNITARIOCOMPRA`, `ID_ORDEN`, `ID_PRODUCTO`) SELECT `ID`, `CANTIDAD`, `PRECIOUNITARIOCOMPRA`, `ID_ORDEN`, `ID_PRODUCTO` FROM `aepad-tp`.ORDENCOMPRADETALLE;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `PAGO`(`ID`, `FECHA`, `MONTO`, `ID_CLIENTE`, `ID_FACTURA`) SELECT   `ID`, `FECHA`, `MONTO`, `ID_CLIENTE`, `ID_FACTURA` FROM `aepad-tp`.PAGO;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `PRODUCTO`(`ID`, `DESCRIPCION`, `PRECIO`) SELECT `ID`, `DESCRIPCION`, `PRECIO` FROM `aepad-tp`.PRODUCTO;").executeUpdate();
			this.emBk.createNativeQuery("INSERT INTO `REPORTE_CLIENTE`(`ID_CLIENTE`, `COMPRAS`, `COMPRA_PROMEDIO`, `COMPRAS_TOTAL`, `PRODCUTOS_COMPRADOS`, `PAGOS`, `PAGO_PROMEDIO`, `PAGOS_TOTALES`, `SALDO`, `FECHA_CALCULO`) SELECT `ID_CLIENTE`, `COMPRAS`, `COMPRA_PROMEDIO`, `COMPRAS_TOTAL`, `PRODCUTOS_COMPRADOS`, `PAGOS`, `PAGO_PROMEDIO`, `PAGOS_TOTALES`, `SALDO`, `FECHA_CALCULO` FROM `aepad-tp`.REPORTE_CLIENTE;").executeUpdate();
			millisInicio = System.currentTimeMillis();
			obj.add("T_BACKUP", System.currentTimeMillis() - millisInicio);

		} catch (Exception e) {
			obj.add("T_ERROR", System.currentTimeMillis() - millisInicio);
			obj.add("MSG_ERROR", e.getMessage());
			e.printStackTrace();
		}
		return Response.ok(obj.build().toString()).build();
	}

	@GET
	@Path("limpiarbk")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response destruir() {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		long millisInicio = System.currentTimeMillis();
		try {
			this.emBk.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.PAGO; ").executeUpdate();
			obj.add("pago", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.ORDENCOMPRADETALLE;").executeUpdate();
			obj.add("ordencompradetalle", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.FACTURADETALLE;").executeUpdate();
			obj.add("facturadetalle", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.ORDENCOMPRA;").executeUpdate();
			obj.add("ordencompra", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.FACTURA;").executeUpdate();
			obj.add("factura", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.PRODUCTO;").executeUpdate();
			obj.add("producto", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.USUARIO;").executeUpdate();
			obj.add("usuario", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.CATEGORIA;").executeUpdate();
			obj.add("categoria", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("TRUNCATE table `aepad-bk`.LOG_ACCESO;").executeUpdate();
			obj.add("log_acceso", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emBk.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
		} catch (Exception e) {
			obj.add("T_ERROR", System.currentTimeMillis() - millisInicio);
			obj.add("MSG_ERROR", e.getMessage());
			e.printStackTrace();
		}
		return Response.ok(obj.build().toString()).build();
	}

	@GET
	@Path("teardown")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response terminarTest() {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		long millisInicio = System.currentTimeMillis();
		try {
			this.emOrig.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
			this.emOrig.createNativeQuery("TRUNCATE table PAGO; ").executeUpdate();
			obj.add("pago", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("TRUNCATE table ORDENCOMPRADETALLE;").executeUpdate();
			obj.add("ordencompradetalle", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("TRUNCATE table FACTURADETALLE;").executeUpdate();
			obj.add("facturadetalle", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("TRUNCATE table ORDENCOMPRA;").executeUpdate();
			obj.add("ordencompra", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("TRUNCATE table FACTURA;").executeUpdate();
			obj.add("factura", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("TRUNCATE table PRODUCTO;").executeUpdate();
			obj.add("producto", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("TRUNCATE table USUARIO;").executeUpdate();
			obj.add("usuario", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("TRUNCATE table CATEGORIA;").executeUpdate();
			obj.add("categoria", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("TRUNCATE table LOG_ACCESO;").executeUpdate();
			obj.add("log_acceso", System.currentTimeMillis() - millisInicio);
			millisInicio = System.currentTimeMillis();
			this.emOrig.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
		} catch (Exception e) {
			obj.add("T_ERROR", System.currentTimeMillis() - millisInicio);
			obj.add("MSG_ERROR", e.getMessage());
			e.printStackTrace();
		}
		return Response.ok(obj.build().toString()).build();
	}

	private void crearCategorias(EntityManager em) {
		try {
			for (String catName : CATEGORIAS) {
				Categoria cat = new Categoria();
				cat.setNombre(catName);
				em.persist(cat);
				entidadesCategorias.add(cat);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Random r = new Random();
		// int indice = r.nextInt(categorias.length);
	}

	private void crearProductos(Integer n, EntityManager em) {
		Random r = new Random();
		int maxCat = 1 + r.nextInt(3);
		for (int i = 0; i < n; i++) {
			HashSet<Integer> catGeneradas = new HashSet<>();
			while (catGeneradas.size() < maxCat) {
				catGeneradas.add(r.nextInt(entidadesCategorias.size()));
			}
			Producto p = new Producto();
			p.setPrecio(10.0);
			p.setDescripcion(generador.generateRandomWords(3));
			List<Categoria> aux = new ArrayList<>();
			for (Integer idxcat : catGeneradas) {
				aux.add(this.entidadesCategorias.get(idxcat));
			}
			p.setCategoria(aux);
			em.persist(p);
			em.flush();
			em.refresh(p);
			productosCreados.add(p);
		}
	}

	private void crearOrdenCompra(Integer n, EntityManager em) {
		Random r = new Random();
		for (int i = 0; i < n; i++) {

			int productosPorOrden = 1 + r.nextInt(6);
			OrdenCompra ordenCompra = new OrdenCompra();
			ordenCompra.setFecha(new Date());
			em.persist(ordenCompra);
			for (int j = 0; j < productosPorOrden; j++) {
				Integer indice = r.nextInt(this.productosCreados.size());
				Double precioCompra = r.nextDouble() * 1000;
				Producto p = em.merge(this.productosCreados.get(indice));
				p.setPrecio(precioCompra * 1.25);
				OrdenCompraDetalle detalle = new OrdenCompraDetalle();
				detalle.setCantidad(r.nextInt(20));
				detalle.setPrecioUnitarioCompra(precioCompra);
				detalle.setOrden(ordenCompra);
				detalle.setProducto(p);
				em.persist(detalle);
			}
		}
	}

	private void crearUsuarios(Integer n, EntityManager em) {
		for (int i = 0; i < n; i++) {
			Usuario usr = new Usuario();
			usr.setMail("usuario_" + i + "@mail.com");
			em.persist(usr);
		}
	}

	
}
