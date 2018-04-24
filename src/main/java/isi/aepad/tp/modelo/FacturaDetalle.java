package isi.aepad.tp.modelo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class FacturaDetalle {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="ID_PRODUCTO")
	private Producto producto;
	private Integer cantidad;
	private Double precioUnitarioFacturado;	
	@ManyToOne
	@JoinColumn(name="ID_FACTURA")
	private Factura factura;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPrecioUnitarioFacturado() {
		return precioUnitarioFacturado;
	}
	public void setPrecioUnitarioFacturado(Double precioUnitarioFacturado) {
		this.precioUnitarioFacturado = precioUnitarioFacturado;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	
}
