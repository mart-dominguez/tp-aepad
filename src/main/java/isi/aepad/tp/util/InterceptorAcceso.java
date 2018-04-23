package isi.aepad.tp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.sql.DataSource;

import isi.aepad.tp.modelo.LogAcceso;

@Interceptor
public class InterceptorAcceso {

	@Resource(lookup = "jdbc/aepad")
	DataSource ds;

	@AroundInvoke
	public Object logMethod(InvocationContext ic) throws Exception {
		LogAcceso log = new LogAcceso();
		log.setClase(ic.getTarget().toString());
		log.setMetodo(ic.getMethod().getName());
		log.setMillisInicio(System.currentTimeMillis());
		try {
			return ic.proceed();
		} finally {
			try (Connection conn = ds.getConnection()) {
				log.setMillisFin(System.currentTimeMillis());
				try (PreparedStatement ps = conn.prepareStatement("INSERT INTO LOG_ACCESO (?,?,?,?,?)")) {
					ps.setString(1, log.getClase());
					ps.setString(2, log.getMetodo());
					ps.setLong(3, log.getMillisInicio());
					ps.setLong(4, log.getMillisFin());
					ps.setLong(5, log.getMillisFin() - log.getMillisInicio());
					ps.executeUpdate();
				}
			} catch (SQLException ex) {
				Logger.getLogger(InterceptorAcceso.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}