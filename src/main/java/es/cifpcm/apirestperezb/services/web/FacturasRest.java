/**
 * 
 */
package es.cifpcm.apirestperezb.services.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cifpcm.apirestperezb.data.DaoFactory;
import es.cifpcm.apirestperezb.data.FacturaDao;
import es.cifpcm.apirestperezb.model.Factura;
import es.cifpcm.apirestperezb.model.Tienda;

/**
 * @author Brian Pérez Ramos
 *
 */
@Path("/facturas")
public class FacturasRest {
	private static Logger logger = LoggerFactory.getLogger(FacturasRest.class);
	private FacturaDao facDao;
	
	public FacturasRest() {
		
	}
	
	@GET
	@Path("/getAll") // Devuelve la lista de facturas en formato JSON
	@Produces(MediaType.APPLICATION_JSON)
	public List<Factura> getAll() {
		List<Factura> listaDevolver = new ArrayList<>();
		facDao = DaoFactory.getInstance().getFacturasDao();
		listaDevolver.addAll(facDao.getAll());
		
		return listaDevolver;
	}
	
	@GET
	@Path("/getTiendas") // Devuelve la lista de tiendas en formato JSON (para el select de Insertar Factura)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tienda> getTiendas(){
		List<Tienda> listaTiendas = new ArrayList<>();
		facDao = DaoFactory.getInstance().getFacturasDao();
		listaTiendas.addAll(facDao.getTiendas());
		return listaTiendas;
	}
	
	@GET
	@Path("/getFactura") // Devuelve la factura encontrada en formato JSON
	@Produces(MediaType.APPLICATION_JSON)
	public Factura getFactura(@QueryParam("nFactura") Integer nFactura) {
		Factura facturaDevolver = new Factura();
		facDao = DaoFactory.getInstance().getFacturasDao();
		facturaDevolver = facDao.getFactura(nFactura);
		return facturaDevolver;
	}
	
	@POST
	@Path("/addFactura")
	@Consumes(MediaType.APPLICATION_JSON) // Recibe el JSON con el nombre del cliente y el id de la Tienda escogidos en el formulario para insertarlo.
	public void addFactura(Factura facturaRecibida) {
		facDao = DaoFactory.getInstance().getFacturasDao();
		logger.info("Datos recibidos: " + facturaRecibida.getIdTienda() + " cliente: " + facturaRecibida.getCliente());

		logger.info("FActura recibida " + facturaRecibida);
		facDao.addFactura(facturaRecibida.getCliente(), facturaRecibida.getIdTienda().toString());
	}
}
