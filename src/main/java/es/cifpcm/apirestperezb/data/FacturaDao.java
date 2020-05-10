/**
 * 
 */
package es.cifpcm.apirestperezb.data;

import java.util.List;

import es.cifpcm.apirestperezb.model.Factura;
import es.cifpcm.apirestperezb.model.Tienda;

/**
 * @author Brian Pérez Ramos
 *
 */
public interface FacturaDao {
	public List<Factura> getAll();
	public Factura getFactura(Integer nFactura);
	public void addFactura(String cliente, String idTienda);
	public List<Tienda> getTiendas();
}
