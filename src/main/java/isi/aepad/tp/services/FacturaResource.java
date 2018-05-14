package isi.aepad.tp.services;

import java.util.List;
import java.io.StringReader;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

import isi.aepad.tp.modelo.Factura;
import isi.aepad.tp.modelo.FacturaDetalle;
import isi.aepad.tp.modelo.Pago;
import isi.aepad.tp.modelo.Producto;
import isi.aepad.tp.modelo.Usuario;
import isi.aepad.tp.util.InterceptorAcceso;

@Singleton
@Path("factura")
@Dependent
@Interceptors(InterceptorAcceso.class)
@Timed
public class FacturaResource {

	@PersistenceContext(unitName = "AEPAD_PU")
	private EntityManager em;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response facturar(String f) {
		Factura facturaNueva = new Factura();
		try {
			JsonReader reader = Json.createReader(new StringReader(f));
			JsonObject facturaJson = reader.readObject();
			facturaNueva.setFecha(new Date());
			facturaNueva.setCliente(em.find(Usuario.class, facturaJson.getJsonObject("cliente").getInt("id")));
			em.persist(facturaNueva);
			em.flush();
			em.refresh(facturaNueva);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok(facturaNueva).build();
	}

	@POST
	@Path("item")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response agregarProducto(String f) {
		Random r = new Random();
		Factura facturaBase = null;
		FacturaDetalle detalle = new FacturaDetalle();
		Producto producto = null;		
		try {
			JsonReader reader = Json.createReader(new StringReader(f));
			JsonObject itemPedido = reader.readObject();
			producto = em.find(Producto.class, itemPedido.getInt("idProductoElegido"));
			facturaBase = em.find(Factura.class, itemPedido.getInt("idFactura"));
			detalle.setFactura(facturaBase);
			detalle.setCantidad(1+r.nextInt(20));
			detalle.setProducto(producto);
			detalle.setPrecioUnitarioFacturado(producto.getPrecio() * 1.21);
			em.persist(detalle);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok(detalle).build();
	}
	
	@GET
	@Path("saldo")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response consultarSaldo(@QueryParam("idFactura") int idFactura) {
		JsonObjectBuilder builderObj= Json.createObjectBuilder();
		Factura facturaBase = em.find(Factura.class, idFactura);
		Double montoFactura= 0.0;
		Double pagado = 0.0;
		List<Pago> listaPagos = em.createQuery("SELECT p FROM Pago p WHERE p.factura.id = :P_ID_FACTURA").setParameter("P_ID_FACTURA", idFactura).getResultList();
		List<FacturaDetalle> listaDetalles = em.createQuery("SELECT fd FROM FacturaDetalle fd WHERE fd.factura.id = :P_ID_FACTURA").setParameter("P_ID_FACTURA", idFactura).getResultList();
		Logger.getLogger("FACTURA_RESOURCE").log(Level.INFO, "ID FACTURA " + idFactura);
		Logger.getLogger("FACTURA_RESOURCE").log(Level.INFO, "lista pagos" + listaPagos);
		Logger.getLogger("FACTURA_RESOURCE").log(Level.INFO, "lista listaDetalles" + listaDetalles);
		try {
			if(listaPagos!=null && listaPagos.size()>0) {
				pagado = listaPagos.stream().mapToDouble(p -> p.getMonto()).sum();
			}
			if(listaDetalles!=null  && listaDetalles.size()>0) {
				montoFactura = listaDetalles.stream().mapToDouble(d -> d.getPrecioUnitarioFacturado()*d.getCantidad()).sum();
			}
			Logger.getLogger("FACTURA_RESOURCE").log(Level.INFO, "facturado " + montoFactura);
			Logger.getLogger("FACTURA_RESOURCE").log(Level.INFO, "pagos" + pagado);

			builderObj.add("factura", montoFactura);
			builderObj.add("pagos", pagado);
			builderObj.add("deuda", montoFactura-pagado);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok(builderObj.build().toString()).build();
	}

}
