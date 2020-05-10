/**
 * 
 */
package es.cifpcm.apirestperezb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cifpcm.apirestperezb.model.Factura;
import es.cifpcm.apirestperezb.model.Tienda;

/**
 * @author Brian Pérez Ramos
 *
 */
public class FacturaDaoImpl implements FacturaDao {
	static Logger logger = LoggerFactory.getLogger(FacturaDaoImpl.class);
	private ConnectionProvider cp = DaoFactory.getInstance();
	private Connection conexion;
	private PreparedStatement pstmt = null;
	

	@Override
	public List<Factura> getAll() {
		// TODO Auto-generated method stub
		List<Factura> facturasEncontradas = new ArrayList<>();
		try {
			String consultaFacturas = "SELECT NFactura,Fecha,Cliente,idTienda FROM factura ORDER BY NFactura desc";
			conexion = cp.getConnection();
			logger.info("Estableciendo conexión getAll Facturas");

			pstmt = conexion.prepareStatement(consultaFacturas);
			logger.info("Ejecutando consulta getAll Facturas");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Factura facturaAgregar = new Factura();
				facturaAgregar.setIdFactura(rs.getInt(1));
				facturaAgregar.setFecha(rs.getTimestamp(2));
				facturaAgregar.setCliente(rs.getString(3));
				facturaAgregar.setIdTienda(rs.getInt(4));
				
				String consultaNombreTienda = "SELECT NombreTienda FROM tienda WHERE IdTienda = ?";
				PreparedStatement pstmtTienda = conexion.prepareStatement(consultaNombreTienda);
				pstmtTienda.setInt(1, facturaAgregar.getIdTienda());
				ResultSet rsTienda = pstmtTienda.executeQuery();
				if(rsTienda.next()) {
					facturaAgregar.setNombreTienda(rsTienda.getString(1));
				}
				String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(facturaAgregar.getFecha());
				facturaAgregar.setFechaFormateada(fechaFormateada);

				facturasEncontradas.add(facturaAgregar);
			}
		} catch (SQLException e) {
			logger.error("Error: " + e);
		}

		return facturasEncontradas;
	}

	@Override
	public Factura getFactura(Integer nFactura) {
		Factura facturaDevolver = new Factura();
		try {
			String consultaFacturas = "SELECT NFactura,Fecha,Cliente,idTienda FROM factura WHERE NFactura = ?";
			conexion = cp.getConnection();
			logger.info("Estableciendo conexión getFactura " + nFactura);

			pstmt = conexion.prepareStatement(consultaFacturas);
			logger.info("Ejecutando consulta getFactura " + nFactura);
			pstmt.setInt(1, nFactura);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				facturaDevolver.setIdFactura(rs.getInt(1));
				facturaDevolver.setFecha(rs.getTimestamp(2));
				facturaDevolver.setCliente(rs.getString(3));
				facturaDevolver.setIdTienda(rs.getInt(4));
				
				String consultaNombreTienda = "SELECT NombreTienda FROM tienda WHERE IdTienda = ?";
				PreparedStatement pstmtTienda = conexion.prepareStatement(consultaNombreTienda);
				pstmtTienda.setInt(1, facturaDevolver.getIdTienda());
				ResultSet rsTienda = pstmtTienda.executeQuery();
				if(rsTienda.next()) {
					facturaDevolver.setNombreTienda(rsTienda.getString(1));
				}

				logger.info("Se ha encontrado la factura. Cliente: " + facturaDevolver.getCliente());
				String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(facturaDevolver.getFecha());
				facturaDevolver.setFechaFormateada(fechaFormateada);
				logger.info("Fecha encontrada: " + facturaDevolver.getFecha());
				logger.info("Fecha formateada: " + fechaFormateada);
			}
		} catch (SQLException e) {
			logger.error("Error: " + e);
		}

		return facturaDevolver;
	}
	
	@Override
	public void addFactura(String cliente, String idTiendaString) {
		
		try {
			Integer nFactura = 0;
			conexion = cp.getConnection();
			logger.info("Estableciendo conexion addFactura");
			String consultaNFactura = "SELECT MAX(NFactura) from factura";
			pstmt = conexion.prepareStatement(consultaNFactura);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				nFactura = rs.getInt(1) + 1;
				logger.info("Se encuentra el NFactura maximo: " + nFactura);
			}
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			
			
			
			String consultaInsertar = "INSERT INTO factura (NFactura,Fecha,Cliente,idTienda) VALUES (?,?,?,?)";
			pstmt = conexion.prepareStatement(consultaInsertar);
			Integer idTienda = Integer.parseInt(idTiendaString);
			pstmt.setInt(1, nFactura);
			pstmt.setTimestamp(2, ts);
			pstmt.setString(3, cliente);
			pstmt.setInt(4, idTienda);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			logger.error("Error: " + e);
		}
	}
	
	@Override
	public List<Tienda> getTiendas(){
		List<Tienda> listaTiendas = new ArrayList<>();
		
		try {
			conexion = cp.getConnection();
			logger.info("Estableciendo conexion getTiendas");
			String consultaTiendas = "SELECT IdTienda,NombreTienda FROM tienda";
			pstmt = conexion.prepareStatement(consultaTiendas);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Tienda tiendaAgregar = new Tienda();
				tiendaAgregar.setIdTienda(rs.getInt(1));
				tiendaAgregar.setNombreTienda(rs.getString(2));
				listaTiendas.add(tiendaAgregar);
			}
			logger.info("Se han encontrado " + listaTiendas.size() + " tiendas.");
			
		}catch(SQLException e) {
			logger.error("Error: " + e);
		}
		
		return listaTiendas;
	}
}