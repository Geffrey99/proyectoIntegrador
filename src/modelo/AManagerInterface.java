package modelo;

import java.io.File;
import java.util.HashMap;

public interface AManagerInterface  {

	//Todo funciona en todas las clases----
	
	    public HashMap<String, Libro> mostrarTodos(); // Devuelve todos los registros
	    
	    public Libro insertarUno(Libro libro); // Inserta un nuevo libro

	    public Libro modificarUno(Libro libro); // Modifica un libro existente
	    
	    public void borrarUno(String id); // Borra un libro por ID
	    
	    public Libro buscarUno(String id); // Busca un libro por ID
	
	    public void guardarLibros(HashMap<String, Libro> libros);

	  //TODO --Por terminar--(4) de 10
	// public void hacerCopia(String tipoFichero);
	
}


	
	
	/* ESTRUCTURA
	public HashMap crearMapa();
	
	public HashMap leerTodos();
	
	public Libro buscarUno();
	
	public Libro modificar();
	
	public void borrar();
	
	public HashMap escribirMuchos();
	
	*/

