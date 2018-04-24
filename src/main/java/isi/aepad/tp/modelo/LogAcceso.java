package isi.aepad.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LOG_ACCESO")
public class LogAcceso {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String clase;
	private String metodo;
	private String parametros;
	private Long millisInicio;
	private Long millisFin;
	private Long duracion;
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	public Long getMillisInicio() {
		return millisInicio;
	}
	public void setMillisInicio(Long millisInicio) {
		this.millisInicio = millisInicio;
	}
	public Long getMillisFin() {
		return millisFin;
	}
	public void setMillisFin(Long millisFin) {
		this.millisFin = millisFin;
	}
	public Long getDuracion() {
		return duracion;
	}
	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}
	public Long getId() {
		return id;
	}
	public String getParametros() {
		return parametros;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	
	
}