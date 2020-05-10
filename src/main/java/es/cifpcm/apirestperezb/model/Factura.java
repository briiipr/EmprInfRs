/**
 * 
 */
package es.cifpcm.apirestperezb.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Brian Pérez Ramos
 *
 */
public class Factura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8194996808724817992L;

	Integer idFactura;
	Timestamp fecha;
	String fechaFormateada;
	String cliente;
	String nombreTienda;
	Integer idTienda;

	/**
	 * 
	 */
	public Factura() {
		super();
	}

	/**
	 * @return the idFactura
	 */
	public Integer getIdFactura() {
		return idFactura;
	}

	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	/**
	 * @return the fecha
	 */
	public Timestamp getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getFechaFormateada() {
		return fechaFormateada;
	}

	public void setFechaFormateada(String fechaFormateada) {
		this.fechaFormateada = fechaFormateada;
	}

	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the nombreTienda
	 */
	public String getNombreTienda() {
		return nombreTienda;
	}

	/**
	 * @param nombreTienda the nombreTienda to set
	 */
	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	/**
	 * @return the idTienda
	 */
	public Integer getIdTienda() {
		return idTienda;
	}

	/**
	 * @param idTienda the idTienda to set
	 */
	public void setIdTienda(Integer idTienda) {
		this.idTienda = idTienda;
	}

	/**
	 * @param date
	 * @param cliente
	 * @param idTienda
	 */
	public Factura(Timestamp fecha, String cliente, Integer idTienda) {
		super();
		this.fecha = fecha;
		this.cliente = cliente;
		this.idTienda = idTienda;
	}
}
