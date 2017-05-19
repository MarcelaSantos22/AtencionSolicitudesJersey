package ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bl.ClienteBL;
import dto.Cliente;
import dto.ClienteDTOws;
import dto.Usuario;
import exception.IWServiceException;
import exception.MyException;
import javassist.tools.rmi.RemoteException;

/**
 * Servicios Web para logica del Negocio de ClienteBL
 * 
 * @author Yuri Quejada
 * @author Daniel Pelaez
 * @author Jean Herrera
 * @version 1.0
 */

@Path("Cliente")
@Component
public class ClienteWS {

	@Autowired
	ClienteBL clienteBL;

	/**
	 * Servicio para guardar un cliente en la Base de Datos.
	 * 
	 * @param Cedula
	 * 			cedula del cliente a guardar
	 * @param nombre
	 *            nombre del cliente a guardar
	 * @param apellido
	 *            apellido del cliente a guardar
	 * @param email
	 *            correo electronico a guardar
	 * @param telefono
	 *            telefono a guardar
	 * @param direccion
	 *            direccion a guardar
	 * @param usuario
	 *            username del cliente a guardar
	 * @return Mensaje de confirmacion
	 * @throws RemoteException
	 */
	@Produces(MediaType.TEXT_HTML)
	@POST
	@Path("Guardar")
	public String guardar(@QueryParam("cedula") String cedula, @QueryParam("nombre") String nombre,
			@QueryParam("apellido") String apellido, @QueryParam("email") String email,
			@QueryParam("telefono") String telefono, @QueryParam("direccion") String direccion,
			@QueryParam("usuario") String usuario) {

		String exito = null;
		try {
			exito = clienteBL.guardarCliente(cedula, nombre, apellido, email, telefono, direccion, usuario);
			return exito;
		} catch (MyException e) {
			throw new RemoteException("Error creando el cliente " + e);
		} catch (IWServiceException e) {
			throw new RemoteException("Error creando el cliente " + e);
		}
	}

	/**
	 * Servicio para actualizar un cliente en la Base de Datos.
	 * 
	 * @param cedula
	 *            cedula del cliente
	 * @param nombre
	 *            nombre del cliente a modificar
	 * @param apellido
	 *            apellido del cliente a modificar
	 * @param email
	 *            correo electronico a modificar
	 * @param telefono
	 *            telefono a modificar
	 * @param direccion
	 *            direccion a modificar
	 * @param usuario
	 *            username del cliente a modificar
	 * @return Mensaje de confirmacion
	 * @throws RemoteException
	 */
	@Produces(MediaType.TEXT_HTML)
	@PUT
	@Path("Actualizar")
	public String actualizar(@QueryParam("cedula") String cedula, @QueryParam("nombre") String nombre,
			@QueryParam("apellido") String apellido, @QueryParam("email") String email,
			@QueryParam("telefono") String telefono, @QueryParam("direccion") String direccion,
			@QueryParam("usuario") String usuario){

		String exito = null;
		try {
			exito = clienteBL.actualizarCliente(cedula, nombre, apellido, email, telefono, direccion, usuario);
			return exito;
		} catch (MyException e) {
			throw new RemoteException("Error actualizando el cliente " + e);
		} catch (IWServiceException e) {
			throw new RemoteException("Error actualizando el cliente " + e);
		}

	}

	/**
	 * Servicio para obtener una lista de Clientes de la Base de Datos
	 * 
	 * @return Lista de Clientes
	 * @throws RemoteException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerLista")
	public List<ClienteDTOws> obtener(){
		
		List<ClienteDTOws> respuesta = new ArrayList<ClienteDTOws>();

		try {
			for (Cliente cliente : clienteBL.obtener()) {
				ClienteDTOws clienteDTOws = new ClienteDTOws(cliente.getCedula(), cliente.getNombre(),
						cliente.getApellido(), cliente.getEmail(), cliente.getDireccion(), cliente.getTelefono(), cliente.getUsuario());

				respuesta.add(clienteDTOws);

			}
		} catch (MyException e) {

			throw new RemoteException("promblema consultando " + e);
		}
		return respuesta;
	}

}
