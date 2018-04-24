package isi.aepad.tp.batch;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Dependent
@Named("ClienteDataReader")
public class ClienteDataReader  implements javax.batch.api.chunk.ItemReader{
	
	private MyCheckpoint checkpoint;

	@Resource(lookup = "jdbc/aepad")
	DataSource ds;
	ResultSet rs;
	Connection conn;
	PreparedStatement ps ;

	@Override
	public Serializable checkpointInfo() throws Exception {
		return null;
	}

	@Override
	public void close() throws Exception {
		if(rs!=null) rs.close();
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();
	}

	@Override
	public void open(Serializable ckpt) throws Exception {
		  if (ckpt == null)
	            checkpoint = new MyCheckpoint();
	        else
	            checkpoint = (MyCheckpoint) ckpt;

		conn = ds.getConnection();
		ps = conn.prepareStatement("SELECT id FROM USUARIO WHERE id > ? ORDER BY id");
		ps.setInt(1, checkpoint.lastId());
		rs = ps.executeQuery();											
	}

	@Override
	public Object readItem() throws Exception {
		rs.next();
		checkpoint.setLastId(rs.getInt(1));
		return Integer.valueOf(checkpoint.lastId());
	}

}
