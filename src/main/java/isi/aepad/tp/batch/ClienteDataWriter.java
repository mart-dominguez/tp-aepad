package isi.aepad.tp.batch;

import java.io.Serializable;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.Query;
import javax.sql.DataSource;

import isi.aepad.tp.modelo.Pago;

@Dependent
@Named("ClienteDataWriter")
public class ClienteDataWriter implements javax.batch.api.chunk.ItemWriter {

	@Resource(lookup = "jdbc/aepad")
	DataSource ds;
	// ResultSet rs;
	// Connection conn;
	// PreparedStatement ps ;
	MyCheckpoint ckpt;

	@Inject
	private JobContext jobCtx;

	@Override
	public void open(Serializable ckpt) throws Exception {
		if (ckpt == null)
			this.ckpt = new MyCheckpoint();
		else
			this.ckpt = (MyCheckpoint) ckpt;
	}

	@Override
	public void writeItems(List<Object> items) throws Exception {
		java.sql.Timestamp t = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		try (Connection conn = ds.getConnection()) {
			try (PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO REPORTE_CLIENTE(ID_CLIENTE, COMPRAS, COMPRA_PROMEDIO, COMPRAS_TOTAL, PRODCUTOS_COMPRADOS, PAGOS, PAGO_PROMEDIO, PAGOS_TOTALES, SALDO, FECHA_CALCULO) VALUES (?,?,?,?,?,?,?,?,?,?)")) {
				for (Object obj : items) {
					JsonReader reader = Json.createReader(new StringReader(obj.toString()));
					JsonObject filaJson = reader.readObject();
					ps.setInt(1, filaJson.getInt("id_usuario"));
					ps.setInt(2, filaJson.getInt("compras"));
					ps.setDouble(3, filaJson.getJsonNumber("compraPromedio").doubleValue());
					ps.setDouble(4, filaJson.getJsonNumber("comprasTotales").doubleValue());
					ps.setDouble(5, filaJson.getJsonNumber("productosComprados").intValue());
					ps.setDouble(6, filaJson.getJsonNumber("pagos").intValue());
					ps.setDouble(7, filaJson.getJsonNumber("pagoPromedio").doubleValue());
					ps.setDouble(8, filaJson.getJsonNumber("pagosTotales").doubleValue());
					ps.setDouble(9, filaJson.getJsonNumber("saldo").doubleValue());
					ps.setTimestamp(10, t);
					ps.executeUpdate();
					this.ckpt.setLastId(filaJson.getInt("id_usuario"));
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		return this.ckpt;
	}

	@Override
	public void close() throws Exception {
		System.out.println("CIERRA CLIETNE DATA WRITER");
	}
}