package isi.aepad.tp.batch;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import isi.aepad.tp.modelo.Factura;
import isi.aepad.tp.modelo.Pago;

@Dependent
@Named("ClienteDataProcessor")
public class ClienteDataProcessor implements javax.batch.api.chunk.ItemProcessor {
	public ClienteDataProcessor() {}

	private Map<String,Double> datos = new HashMap<>();
	
	@PersistenceContext(unitName = "AEPAD_PU")
	private EntityManager em;
	
	@Override
	public Object processItem(Object obj) throws Exception {
		Query q1= em.createQuery("SELECT f FROM Factura f WHERE f.cliente.id = :P_ID_CLIENTE");
		Integer i = (Integer) obj;
		List<Factura> listaFacturas =q1.setParameter("P_ID_CLIENTE", i).getResultList();
		double comprasTotales = 0.0;
		int productosComprados = 0;
		for(Factura f:listaFacturas) {
			comprasTotales += f.getDetalles().stream().mapToDouble(d -> d.getCantidad()*d.getPrecioUnitarioFacturado()).sum();
			productosComprados  += f.getDetalles().stream().mapToInt(d->d.getCantidad()).sum();
		}
		double media= comprasTotales/listaFacturas.size();
		JsonObjectBuilder builderObj= Json.createObjectBuilder();
		builderObj.add("compras", listaFacturas.size());
		builderObj.add("compraPromedio", media);
		builderObj.add("comprasTotales", comprasTotales);
		builderObj.add("productosComprados", productosComprados);
		
		Query q2= em.createQuery("SELECT f FROM Pago f WHERE f.cliente.id = :P_ID_CLIENTE");
		List<Pago> listaPagos=q2.setParameter("P_ID_CLIENTE", i).getResultList();
		double pagosTotales = listaPagos.stream().mapToDouble(p -> p.getMonto()).sum();
		int cantidadPagos= listaPagos.size();
		builderObj.add("id_usuario", i);
		builderObj.add("pagos", cantidadPagos);
		builderObj.add("pagoPromedio", pagosTotales/cantidadPagos);
		builderObj.add("pagosTotales", pagosTotales);
		builderObj.add("saldo", pagosTotales-comprasTotales);
		
		return builderObj.build().toString();
	}
}
