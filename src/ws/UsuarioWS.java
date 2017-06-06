package ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bl.UsuarioBL;
import dto.Usuario;
import dto.UsuarioDTOws;
import exception.IWServiceException;
import exception.MyException;
import javassist.tools.rmi.RemoteException;

/**
 * Servicios Web para logica del Negocio de UsuarioBL
 * 
 * @author Yuri Quejada
 * @author Daniel Pelaez
 * @author Jean Herrera
 * @version 1.0
 */

@Path("Usuario")
@Component
public class UsuarioWS {
	
	@Autowired
	UsuarioBL usuarioBL;
	
	
	/**
	 * Servicio para guardar un usuario en la Base de Datos.
	 * 
	 * @param user
	 * 			username del usuerio a guardar
	 * @param pws
	 *            contraseña del usuario guardar
	 * @param rol
	 *            rol del usuario guardar
	 * @return Mensaje de confirmacion
	 * @throws RemoteException
	 */
	@Produces(MediaType.TEXT_HTML)
	@POST
	@Path("Guardar")
	public String guardar(@QueryParam("user") String user, @QueryParam("password") String pws,
			@QueryParam("rol") String rol) {

		String exito = null;
		try {
			exito = usuarioBL.guardarUsuario(user, pws, rol);
			return exito;
		} catch (MyException e) {
			throw new RemoteException("Error creando el usuario " + e);
		} catch (IWServiceException e) {
			throw new RemoteException("Error creando el usuario " + e);
		}
	}

	
	/**
	 * Servicio para autenticar un usuario en el sistema.
	 * 
	 * @param user usuario en el sistema.
	 * @param password contrasenia que corresponde al usuario.
	 * @return Mensaje de confirmacion.
	 * @throws RemoteException 
	 */
	@Produces(MediaType.TEXT_HTML)
	@GET
	@Path("Autenticar")
	public String autenticar(@QueryParam("user") String user, @QueryParam("password") String password) {
		
		String exito = null;
		try {
			 Usuario usuario = usuarioBL.autenticarUsuario(user, password);	
			 exito = usuario.getRol().getDescripcion();
			
		} catch (MyException e) {
			throw new RemoteException("Autenticacion Fallida " + e);
		} catch (IWServiceException e) {
			throw new RemoteException("Autenticacion Fallida " + e);
		}
		
		return exito;
}
	
	/**
	 * Servicio para obtener una lista de Usuarios de la Base de Datos
	 * 
	 * @return Lista de Usuarios
	 * @throws RemoteException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerLista")
	public List<UsuarioDTOws> obtener() {

		List<UsuarioDTOws> respuesta = new ArrayList<UsuarioDTOws>();

		try {
			for (Usuario usuario : usuarioBL.obtener()) {
				UsuarioDTOws usuarioDTOws = new UsuarioDTOws(usuario.getUser(), usuario.getPassword(),
						usuario.getRol());

				respuesta.add(usuarioDTOws);

			}
		} catch (MyException e) {

			throw new RemoteException("problema consultando los usuarios "+ e);
		}
		return respuesta;
	}
	
	/**
	 * Servicio para obtener un unico usuario
	 * @param user username del usuario que se desea obtener
	 * @return El usuario que corresponde al user enviado como parametro
	 * @throws RemoteException 
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerUsuario")
	public UsuarioDTOws obtener(@QueryParam("user") String user){
		Usuario usuario = new Usuario();
		
		try {
			usuario = usuarioBL.obtener(user);
			UsuarioDTOws usuarioDTOws = new UsuarioDTOws(usuario.getUser(), usuario.getPassword(),
					usuario.getRol());
			
			return usuarioDTOws;
			
		} catch (MyException e) {
			throw new RemoteException("Error al obtener el usuario " + e);
		} catch (IWServiceException e) {
			throw new RemoteException("Error al obtener el usuario " + e);
		}
	}	


}
