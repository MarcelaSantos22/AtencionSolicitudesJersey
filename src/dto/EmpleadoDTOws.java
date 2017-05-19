package dto;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * DTO para el servicio Web EmpleadoWS
 * 
 * @author Yuri Quejada
 * @author Daniel Pelaez
 * @author Jean Herrera
 * @version 1.0
 */
/*Parsear de manera automatica los objetos de esta clase a formato JSON*/
@XmlRootElement  
public class EmpleadoDTOws {

	private String cedula; //PK
	private String nombre;
	private String apellido;
	private String email;
	private Usuario usuario;
	
	
	public EmpleadoDTOws() {
		
	}
	
	public EmpleadoDTOws(String cedula, String nombre, String apellido, String email, Usuario usuario) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.usuario = usuario;
	}
	
	
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
