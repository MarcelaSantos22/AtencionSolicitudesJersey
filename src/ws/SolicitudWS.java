package ws;

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
import logicaNegocio.SolicitudService;
import javassist.tools.rmi.RemoteException;

/**
 * Servicios Web para logica del Negocio
 * de SolicitudService.
 * 
 * @author Diana Ciro
 * @author Milena Cardenas
 * @author Jorge Bojaca  
 * @version 1.0
 */
@Component
@Path("Solicitud")
public class SolicitudWS {

	/*Se realiza una inyeccion de dependencia, para crear una instancia de EncuestaService */
	@Autowired
	SolicitudService solicitudService;
	
	/**
	 * Servicio para Obtener la lista de solicitures de un usuario enviado como parametro.
	 * @param user identificador del usuario.
	 * @return Lista de solicitudes.
	 * @throws RemoteException llama las excepciones propias.
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<SolicitudDTOws> obtener(@QueryParam("user") String user) throws RemoteException {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudService.obtenerSolicitudes(user);
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setCliente(solicitud.getCliente().getNombres() + " " + solicitud.getCliente().getApellidos());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setId(solicitud.getId());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable().getUser());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
					solic.setProducto(solicitud.getProducto());
					solic.setRespuestaSolicitud(solicitud.getRespuesta());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}

	/**
	 * Servicio para guardar una solicitud.
	 * @param descripcion descripcion de la solicitud
	 * @param tiposolicitud identificador del tipo de solicitud.
	 * @param cliente identificador del cliente.
	 * @param producto nombre del producto.
	 * @param idSucursal identificador de la sucursa.
	 * @return mensaje de confirmación.
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	@Path("Guardar")
	public String guardar(@QueryParam("descripcion") String descripcion, @QueryParam("tiposolicitud") int tiposolicitud,
			@QueryParam("cliente") String cliente, @QueryParam("producto") String producto,
			@QueryParam("idsucursal") int idSucursal) {

		try {
			solicitudService.guardarSolicitud(descripcion, tiposolicitud, cliente, producto, idSucursal, new Date());
			return "Se guardo correctamente la solicitud";
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
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
			@QueryParam("responsable") String usuarioResponsable, @QueryParam("gerente") String usuarioGerente) {
		try {
			solicitudService.asignarResponsable(idSolicitud, usuarioResponsable, usuarioGerente);
			return "Se asigno responsable correctamente";
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
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
			@QueryParam("respuesta") String respuestaSolicitud, @QueryParam("responsable") String usuarioResponsable) {

		try {
			solicitudService.responderSolicitud(idSolicitud, respuestaSolicitud, new Date(), usuarioResponsable);
			return "Se respondio la solicitud correctamente";
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
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
	public SolicitudDTOws obtenerSolicitud(@QueryParam("idSolicitud") int idSolicitud) {
		Solicitud solicitud = null;
		SolicitudDTOws solic = null;
		try {
			solicitud = solicitudService.obtenerSolicitud(idSolicitud);
			if (solicitud != null) {
				solic = new SolicitudDTOws();
				solic.setCliente(solicitud.getCliente().getNombres() + " " + solicitud.getCliente().getApellidos());
				solic.setComplejidad(solicitud.getComplejidad());
				solic.setDescripcion(solicitud.getDescripcion());
				solic.setFechaSolicitud(solicitud.getFechaSolicitud());
				solic.setFechaRespuesta(solicitud.getFechaRespuesta());
				solic.setId(solicitud.getId());
				if (solicitud.getResponsable() != null) {
					solic.setResponsable(solicitud.getResponsable().getUser());
				}
				solic.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
				solic.setProducto(solicitud.getProducto());
				solic.setRespuestaSolicitud(solicitud.getRespuesta());
			}

			return solic;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}

	/**
	 * Servicio para obtner una lista de Solicitudes en formato JSON
	 * @return lista de solicitudes
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("SolicitudesAtrasadas")
	public List<SolicitudDTOws> solicitudesAtrasadas() {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudService.seguimientoSolicitudes();
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setCliente(solicitud.getCliente().getNombres() + " " + solicitud.getCliente().getApellidos());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setId(solicitud.getId());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable().getUser());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
					solic.setProducto(solicitud.getProducto());
					solic.setRespuestaSolicitud(solicitud.getRespuesta());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
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
	public List<SolicitudDTOws> fitrarSolicitudes(@QueryParam("tipo") int tipoSolicitud) {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudService.filtrarPorTipo(tipoSolicitud);
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setCliente(solicitud.getCliente().getNombres() + " " + solicitud.getCliente().getApellidos());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setId(solicitud.getId());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable().getUser());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
					solic.setProducto(solicitud.getProducto());
					solic.setRespuestaSolicitud(solicitud.getRespuesta());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}

	}

}