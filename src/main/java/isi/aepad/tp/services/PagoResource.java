package isi.aepad.tp.services;

import java.io.StringReader;
import java.util.Date;
import java.util.Random;

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

import isi.aepad.tp.modelo.Factura;
import isi.aepad.tp.modelo.FacturaDetalle;
import isi.aepad.tp.modelo.Pago;
import isi.aepad.tp.modelo.Producto;
import isi.aepad.tp.modelo.Usuario;
import isi.aepad.tp.util.InterceptorAcceso;

@Singleton
@Path("pago")
@Dependent
@Interceptors(InterceptorAcceso.class)
public class PagoResource {

	@PersistenceContext(unitName = "AEPAD_PU")
	private EntityManager em;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregarPago(String f) {
		Random r = new Random();
		Factura facturaBase = null;
		Pago pago = new Pago();
		try {
			JsonReader reader = Json.createReader(new StringReader(f));
			JsonObject datosPago = reader.readObject();
			facturaBase = em.find(Factura.class, datosPago.getInt("idFactura"));
			pago.setFactura(facturaBase);
			pago.setFecha(new Date());
			pago.setCliente(facturaBase.getCliente());
			pago.setMonto(datosPago.getJsonNumber("monto").doubleValue());
			em.persist(pago);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok(pago).build();
	}	

}
