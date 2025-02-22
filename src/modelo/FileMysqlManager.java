package modelo;


import java.sql.*;
import java.util.HashMap;

import utils.MySqlUtil;


public class FileMysqlManager implements AManagerInterface {

	
	private Connection coneXion;
	
	public FileMysqlManager() {
		try {
			coneXion = MySqlUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
	}
	}
	

@Override
public HashMap<String, Libro> mostrarTodos() {
	HashMap<String, Libro> libros = new HashMap();
	String query = "SELECT * FROM Libro";
	try (
			Statement ejecutaOperacion = coneXion.createStatement();
			ResultSet recogeResultado = ejecutaOperacion.executeQuery(query) 
	) {
		while (recogeResultado.next()) {
			String id = recogeResultado.getString("id");
			String titulo = recogeResultado.getString("titulo");
			String autor = recogeResultado.getString("autor");
			String isbn = recogeResultado.getString("isbn");
			int anno = recogeResultado.getInt("anno");
			Libro libro = new Libro(id, titulo, autor, isbn, anno);
			libros.put(id, libro);
		}
	} catch (SQLException e) {
		System.err.println("¡Ocurrió una excepción!");
		System.err.println(e.getMessage());
		e.printStackTrace();
	}

	return libros;
}

	
@Override
public Libro insertarUno(Libro libro) {
	String sql = "INSERT INTO Libro (id, titulo, autor, isbn, anno ) VALUES (?,?,?,?,?)"; 
	try (
		 PreparedStatement ejecutaOperacion = coneXion.prepareStatement(sql)
			) {
		ejecutaOperacion.setString(1, libro.getId());
		ejecutaOperacion.setString(2, libro.getTitulo());
		ejecutaOperacion.setString(3, libro.getAutor());
		ejecutaOperacion.setString(4, libro.getIsbn());
		ejecutaOperacion.setInt(5, libro.getAnno());
		int filasAfectadas = ejecutaOperacion.executeUpdate();
		System.out.println("Filas afectadas: " + filasAfectadas);
	} catch (SQLException e) {
		System.out.println("Código de Error: " 
				+ e.getErrorCode() + "\n"
				+ "SLQState: " + e.getSQLState() + "\n" 
				+ "Mensaje: " + e.getMessage() + "\n");
	}
	return libro;
}



@Override
public Libro modificarUno(Libro libro) {
 String updateSql = "UPDATE Libro SET titulo = ?, autor = ?, isbn = ?, anno = ? WHERE id = ?";
 try ( PreparedStatement updateOperacion = coneXion.prepareStatement(updateSql) )
 	{ 
	 updateOperacion.setString(1, libro.getTitulo());
	 updateOperacion.setString(2, libro.getAutor());
	 updateOperacion.setString(3, libro.getIsbn());
	 updateOperacion.setInt(4, libro.getAnno());
	 updateOperacion.setString(5, libro.getId());
	 int filasAfectadas = updateOperacion.executeUpdate();
	 System.out.println("Filas afectadas: " + filasAfectadas);
	 } catch (SQLException e) {
		 System.out.println("Código de Error: " 
				 + e.getErrorCode() + "\n" + "SLQState: " 
				 + e.getSQLState() + "\n" + "Mensaje: " 
				 + e.getMessage() + "\n"); 
		 } 
 return libro;
 
}



@Override
public void borrarUno(String id) {
	String deleteSql = "DELETE FROM Libro WHERE id = ?";
	try ( PreparedStatement deleteOperacion = coneXion.prepareStatement(deleteSql) )
	{ 
		deleteOperacion.setString(1, id);
		int filasAfectadas = deleteOperacion.executeUpdate();
		System.out.println("Libro eliminado. Filas afectadas: " + filasAfectadas);
		} catch (SQLException e) {
			System.out.println("Código de Error: " 
					+ e.getErrorCode() + "\n" + "SLQState: " 
					+ e.getSQLState() + "\n" + "Mensaje: " 
					+ e.getMessage() + "\n"); 
			} 
	
}



@Override
public Libro buscarUno(String id) {
	String selectSql = "SELECT * FROM Libro WHERE id = ?";
	Libro libro = null;
	try ( PreparedStatement selectOperacion = coneXion.prepareStatement(selectSql) )
	{ 
		selectOperacion.setString(1, id);
		ResultSet rs = selectOperacion.executeQuery();
		if (rs.next()) {
			String titulo = rs.getString("titulo");
			String autor = rs.getString("autor");
			String isbn = rs.getString("isbn");
			int anno = rs.getInt("anno");
			libro = new Libro(id, titulo, autor, isbn, anno);
			System.out.println("Libro encontrado "
					+ "con titulo: " +
					 libro.getTitulo()
					+ "Autor : " +
					 libro.getAutor());
			} else {
				System.out.println("No se encontró un libro con el ID proporcionado.");
				} 
		} catch (SQLException e) {
			System.out.println("Código de Error: " 
								+ e.getErrorCode() + "\n" 
								+ "SLQState: " + e.getSQLState() + "\n" 
								+ "Mensaje: " + e.getMessage() + "\n"); 
			} 
	return libro; 
			
}


@Override
public void guardarLibros(HashMap<String, Libro> libros) {
	// TODO -----------------
	
}

//	@Override
//	public void hacerCopia(String tipoFichero) {
//
//	}
}



	 


    