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

import bl.EmpleadoBL;
import dto.ClienteDTOws;
import dto.Empleado;
import dto.EmpleadoDTOws;
import exception.IWServiceException;
import exception.MyException;
import javassist.tools.rmi.RemoteException;

/**
 * Servicios Web para logica del Negocio de EmpleadoBL
 * 
 * @author Yuri Quejada
 * @author Daniel Pelaez
 * @author Jean Herrera
 * @version 1.0
 */

@Path("Empleado")
@Component
public class EmpleadoWS {

	@Autowired
	EmpleadoBL empleadoBL;

	/**
	 * Servicio para guardar un empleado en la Base de Datos.
	 * 
	 * @param Cedula
	 *            cedula del empleado a guardar
	 * @param nombre
	 *            nombre del empleado a guardar
	 * @param apellido
	 *            apellido del empleado a guardar
	 * @param email
	 *            correo electronico a guardar
	 * @param usuario
	 *            username del empleado a guardar
	 * @return Mensaje de confirmacion
	 * @throws RemoteException
	 */
	@Produces(MediaType.TEXT_HTML)
	@POST
	@Path("Guardar")
	public String guardar(@QueryParam("cedula") String cedula, @QueryParam("nombre") String nombre,
			@QueryParam("apellido") String apellido, @QueryParam("email") String email,
			@QueryParam("usuario") String usuario){

		String exito = null;
		try {
			exito = empleadoBL.guardarEmpleado(cedula, nombre, apellido, email, usuario);
			return exito;
		} catch (MyException e) {
			throw new RemoteException("Error creando el empleado "+ e);
		} catch (IWServiceException e) {
			throw new RemoteException("Error creando el empleado "+ e);
		}
	}

	/**
	 * Servicio para actualizar un empleado en la Base de Datos.
	 * 
	 * @param cedula
	 *            cedula del empleado
	 * @param nombre
	 *            nombre del empleado a modificar
	 * @param apellido
	 *            apellido del empleado a modificar
	 * @param email
	 *            correo electronico a modificar
	 * @param usuario
	 *            username del empleado a modificar
	 * @return Mensaje de confirmacion
	 * @throws RemoteException
	 */
	@Produces(MediaType.TEXT_HTML)
	@PUT
	@Path("Actualizar")
	public String actualizar(@QueryParam("cedula") String cedula, @QueryParam("nombre") String nombre,
			@QueryParam("apellido") String apellido, @QueryParam("email") String email,
			@QueryParam("usuario") String usuario) throws RemoteException {

		String exito = null;
		try {
			exito = empleadoBL.actualizarEmpleado(cedula, nombre, apellido, email, usuario);
			return exito;
		} catch (MyException e) {
			throw new RemoteException("Error actualizando el empleado "+ e);
		} catch (IWServiceException e) {
			throw new RemoteException("Error actualizando el empleado "+ e);
		}

	}

	/**
	 * Servicio para obtener una lista de Empleados de la Base de Datos
	 * 
	 * @return Lista de empleados
	 * @throws RemoteException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerLista")
	public List<EmpleadoDTOws> obtener(){

		List<EmpleadoDTOws> respuesta = new ArrayList<EmpleadoDTOws>();

		try {
			for (Empleado empleado : empleadoBL.obtener()) {
				EmpleadoDTOws empleadoDTOws = new EmpleadoDTOws(empleado.getCedula(), empleado.getNombre(),
						empleado.getApellido(), empleado.getEmail(),  empleado.getUsuario());

				respuesta.add(empleadoDTOws);

			}
		} catch (MyException e) {

			throw new RemoteException("problema consultando los empleados "+ e);
		}
		return respuesta;
	}

}
