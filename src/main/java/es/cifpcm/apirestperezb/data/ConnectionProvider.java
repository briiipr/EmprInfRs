/**
 * 
 */
package es.cifpcm.apirestperezb.data;

import java.sql.Connection;

/**
 * @author Brian Pérez Ramos
 *
 */
public interface ConnectionProvider {
	 public Connection getConnection();
}
