package dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Servicios Web para logica del Negocio
 * de la SolicitudService
 * 
 * @author Daniel Pelaez

 */
/*Parsear de manera automatica los objetos de esta clase a formato JSON*/
@XmlRootElement
public class SolicitudDTOws {
	private int id;
	private String descripcion;
	private String complejidad;
	private Date fechaSolicitud;
	private Date fechaRespuesta;
	private String respuestaSolicitud;
	private Cliente cliente;
	private Sucursal sucursal;
	private TipoSolicitud tipoSolicitud;
	private Empleado responsable;
	
	public SolicitudDTOws(){}

	public SolicitudDTOws(int id, String descripcion, String complejidad, Date fechaSolicitud, Date fechaRespuesta,
			String respuestaSolicitud, Cliente cliente, Sucursal sucursal, TipoSolicitud tipoSolicitud,
			Empleado responsable) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.complejidad = complejidad;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaRespuesta = fechaRespuesta;
		this.respuestaSolicitud = respuestaSolicitud;
		this.cliente = cliente;
		this.sucursal = sucursal;
		this.tipoSolicitud = tipoSolicitud;
		this.responsable = responsable;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getComplejidad() {
		return complejidad;
	}

	public void setComplejidad(String complejidad) {
		this.complejidad = complejidad;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public String getRespuestaSolicitud() {
		return respuestaSolicitud;
	}

	public void setRespuestaSolicitud(String respuestaSolicitud) {
		this.respuestaSolicitud = respuestaSolicitud;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public Empleado getResponsable() {
		return responsable;
	}

	public void setResponsable(Empleado responsable) {
		this.responsable = responsable;
	}

	
	
	
}