package isi.aepad.tp.modelo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Pago {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Date fecha;
	private Double monto;
	@ManyToOne
	@JoinColumn(name="ID_FACTURA")
	private Factura factura;

	@ManyToOne
	@JoinColumn(name="ID_CLIENTE")
	private Usuario cliente;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public Usuario getCliente() {
		return cliente;
	}
	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	
	
}
