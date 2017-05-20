package ws;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import dto.Solicitud;
import dto.SolicitudDTOws;
import exception.MyException;
import exception.IWServiceException;
import bl.SolicitudBL;
import java.rmi.RemoteException;

/**
 * Servicios Web para logica del Negocio
 * de SolicitudService.
 * 
 * @author Yuri Quejada
 * @author Daniel Pelaez
 * @author Jean Herrera 
 * @version 1.0
 */
@Component
@Path("Solicitud")
public class SolicitudWS {

	/*Se realiza una inyeccion de dependencia, para crear una instancia de EncuestaService */
	@Autowired
	SolicitudBL solicitudBL;
	
	
	
	public SolicitudBL getSolicitudBL() {
		return solicitudBL;
	}

	public void setSolicitudBL(SolicitudBL solicitudBL) {
		this.solicitudBL = solicitudBL;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("saludo")
	public String saludo() throws RemoteException, IWServiceException{
		return "Si señor";
	}
	/**
	 * Servicio para Obtener la lista de solicitures de un usuario enviado como parametro.
	 * @param usuario identificador del usuario.
	 * @return Lista de solicitudes.
	 * @throws RemoteException llama las excepciones propias.
	 */
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("Obtener")
	public List<SolicitudDTOws> obtener() throws RemoteException, IWServiceException {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudBL.obtenerSolicitudes();
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setId(solicitud.getId());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
					solic.setCliente(solicitud.getCliente());
					solic.setSucursal(solicitud.getSucursal());
					solic.setTipoSolicitud(solicitud.getTipoSolicitud());
					solic.setResponsable(solicitud.getResponsable());
					
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud());
					solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (MyException e) {
			System.out.println("Ex: "+e.getMessage());
			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Servicio para guardar una solicitud.
	 * @param descripcion descripcion de la solicitud
	 * @param tiposolicitud identificador del tipo de solicitud.
	 * @param fechaSolicitud fecha en que se realiz� la solicitud.
	 * @param cliente identificador del cliente.
	 * @return mensaje de confirmaci�n.
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	@Path("Guardar")
	public void guardar(@QueryParam("descripcion") String descripcion, 
			@QueryParam("tiposolicitud") int tiposolicitud, 
			@QueryParam("cliente") String cliente) throws RemoteException, IWServiceException{
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaSolicitud = null;*/
		try {
			//fechaNacimientoD = sdf.parse(fechaNacimiento);
			solicitudBL.guardarSolicitud(descripcion, tiposolicitud, new Date(), cliente);
		} catch (MyException e) {
			throw new RemoteException("Error en servicio para guardar solicitud");
		}
	}
	
	/**
	 * Servicio para asinar un responsable para responder la solicitud hecha por un cliente.
	 * @param idSolicitud identificador de la solicitud.
	 * @param usuarioResponsable user del responsable al que se le asignara la solicitud.
	 * @param usuarioGerente user del gerente (unico que puede asignar responsables).
	 * @return mensaje de confirmacion.
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	@Path("AsignarResponsable")
	public String asignarResponsable(@QueryParam("idSolicitud") int idSolicitud,
			@QueryParam("responsable") String usuarioResponsable,
			@QueryParam("gerente") String usuarioGerente) throws RemoteException, IWServiceException {
		try {
			solicitudBL.asignarResponsable(idSolicitud, usuarioResponsable, usuarioGerente);
			return "Se asigno responsable correctamente";
		} catch (MyException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	/**
	 * Servicio para responder una solicitud.
	 * @param idSolicitud identificador de la solicitud.
	 * @param respuestaSolicitud campo de texto con la respuesta a la solicitud.
	 * @param usuarioResponsable identificador del usuario responsable de responder la solicitud.
	 * @return
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	@Path("ResponderSolicitud")
	public String responderSolicitud(@QueryParam("idSolicitud") int idSolicitud,
			@QueryParam("respuesta") String respuestaSolicitud, @QueryParam("responsable") String usuarioResponsable) throws IWServiceException, RemoteException {

		try {
			solicitudBL.responderSolicitud(idSolicitud, respuestaSolicitud, new Date(), usuarioResponsable);
			return "Se respondio la solicitud correctamente";
		} catch (MyException e) {
			throw new RemoteException(e.getMessage());
		}

	}

	/**
	 * Servicio para obtener una solicitud en formato de JSON 
	 * @param idSolicitud identificador de la solicitud
	 * @return Solicitud con los atributos definidos en SolicitudDTOws
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerSolicitud")
	public SolicitudDTOws obtenerSolicitud(@QueryParam("idSolicitud") int idSolicitud) throws IWServiceException, RemoteException {
		Solicitud solicitud = null;
		SolicitudDTOws solic = null;
		try {
			solicitud = solicitudBL.obtenerSolicitud(idSolicitud);
			if (solicitud != null) {
				solic = new SolicitudDTOws();
				solic.setId(solicitud.getId());
				solic.setDescripcion(solicitud.getDescripcion());
				solic.setComplejidad(solicitud.getComplejidad());
				solic.setFechaSolicitud(solicitud.getFechaSolicitud());
				solic.setFechaRespuesta(solicitud.getFechaRespuesta());
				solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
				solic.setCliente(solicitud.getCliente());
				solic.setSucursal(solicitud.getSucursal());
				solic.setTipoSolicitud(solicitud.getTipoSolicitud());
				solic.setResponsable(solicitud.getResponsable());
				if (solicitud.getResponsable() != null) {
					solic.setResponsable(solicitud.getResponsable());
				}
				solic.setTipoSolicitud(solicitud.getTipoSolicitud());
				solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
			}

			return solic;
		} catch (MyException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	/**
	 * Servicio para obtner una lista de Solicitudes en formato JSON
	 * @return lista de solicitudes
	 */
	/*@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("infoTiempoRespuestas")
	public List<SolicitudDTOws> infoTiempoRespuestas() throws IWServiceException {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudBL.infoTiemposRespuestas();
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setId(solicitud.getId());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
					solic.setCliente(solicitud.getCliente());
					solic.setSucursal(solicitud.getSucursal());
					solic.setTipoSolicitud(solicitud.getTipoSolicitud());
					solic.setResponsable(solicitud.getResponsable());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud());
					solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (MyException e) {
			throw new RemoteException(e);
		}
	}*/

	/**
	 * Servicio para obtner una lista de Solicitudes en formato JSON
	 * @return lista de solicitudes
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("SolicitudesAtrasadas")
	public List<SolicitudDTOws> solicitudesAtrasadas() throws IWServiceException, RemoteException {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudBL.seguimientoSolicitudes();
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setId(solicitud.getId());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
					solic.setCliente(solicitud.getCliente());
					solic.setSucursal(solicitud.getSucursal());
					solic.setTipoSolicitud(solicitud.getTipoSolicitud());
					solic.setResponsable(solicitud.getResponsable());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud());
					solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (MyException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Servicio para filtrar la informacion de una solicitud enviada como parametro
	 * @param tipoSolicitud identificador de la solicitud
	 * @return retorna una lista de solicitudes
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("Filtrar") 
	public List<SolicitudDTOws> fitrarSolicitudes(@QueryParam("tipo") int tipoSolicitud) throws IWServiceException, RemoteException {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudBL.filtrarPorTipo(tipoSolicitud);
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setId(solicitud.getId());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
					solic.setCliente(solicitud.getCliente());
					solic.setSucursal(solicitud.getSucursal());
					solic.setTipoSolicitud(solicitud.getTipoSolicitud());
					solic.setResponsable(solicitud.getResponsable());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud());
					solic.setRespuestaSolicitud(solicitud.getRespuestaSolicitud());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (MyException e) {
			throw new RemoteException(e.getMessage());
		}

	}

}