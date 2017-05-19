package dto;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * DTO para el servicio Web UsuarioWS
 * 
 * @author Yuri Quejada
 * @author Daniel Pelaez
 * @author Jean Herrera
 * @version 1.0
 */
/*Parsear de manera automatica los objetos de esta clase a formato JSON*/
@XmlRootElement  
public class UsuarioDTOws {
	private String user; //PK
	private String password;
	private Rol rol;
	
	
	public UsuarioDTOws() {
		super();
	}
	
	public UsuarioDTOws(String user, String password, Rol rol) {
		super();
		this.user = user;
		this.password = password;
		this.rol = rol;
	}
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	
}
