package modelo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

//Archivo txt
public class FileTextManager implements AManagerInterface {

	private File textFile;

	public FileTextManager(String filePath) {
		this.textFile = new File("Ficheros/" + filePath);
	}

	@Override
	public HashMap<String, Libro> mostrarTodos() {
		HashMap<String, Libro> libros = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 5) {
					String id = data[0];
					String titulo = data[1];
					String autor = data[2];
					String isbn = data[3];
					int anno = Integer.parseInt(data[4]);

					Libro libro = new Libro(id, titulo, autor, isbn, anno);
					libros.put(id, libro);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return libros;
	}

	@Override
	public Libro insertarUno(Libro libro) {
	    // Comprobamos que el libro no existe ya
	    HashMap<String, Libro> libros = mostrarTodos();
	    if (libros.containsKey(libro.getId())) {
	        System.out.println("El libro con ID " + libro.getId() + " ya existe.");
	        return null;
	    }

	    // Guardamos el nuevo libro en el archivo
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile, true))) {
	        // Verificamos si el archivo no está vacío, entonces añadimos una nueva línea antes del nuevo registro
	        if (textFile.length() > 0) {
	            bw.newLine();
	        }
	        
	        String record = libro.getId() + "," + libro.getTitulo() + "," + libro.getAutor() + "," + libro.getIsbn() + "," + libro.getAnno();
	        bw.write(record);
	        bw.newLine();
	        System.out.println("El libro se ha insertado satisfactoriamente.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return libro;
	}

	@Override
	public Libro modificarUno(Libro libroModificado) {
		// Cargamos todos los libros
		HashMap<String, Libro> libros = mostrarTodos();

		// Comprobamos si el libro existe
		if (libros.containsKey(libroModificado.getId())) {
			// Modificamos el objeto Libro
			libros.put(libroModificado.getId(), libroModificado);

			// Sobrescribimos el archivo con los libros modificados
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile))) {
				for (Libro libroActual : libros.values()) {
					String record = libroActual.getId() + "," + libroActual.getTitulo() + "," + libroActual.getAutor()
							+ "," + libroActual.getIsbn() + "," + libroActual.getAnno();
					bw.write(record);
					bw.newLine();
				}
				System.out.println("El libro se ha modificado satisfactoriamente.");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("No se ha encontrado el libro con ID: " + libroModificado.getId());
		}

		return libroModificado;
	}



	@Override
	public void borrarUno(String id) {
		// Cargamos todos los libros
		HashMap<String, Libro> libros = mostrarTodos();

		// Verificamos si el libro existe
		if (libros.containsKey(id)) {
			// Eliminamos el libro del HashMap
			libros.remove(id);

			// Sobrescribimos el archivo con los libros restantes
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile))) {
				for (Libro libro : libros.values()) {
					String record = libro.getId() + "," + libro.getTitulo() + "," + libro.getAutor() + ","
							+ libro.getIsbn() + "," + libro.getAnno();
					bw.write(record);
					bw.newLine();
				}
				System.out.println("El libro se ha borrado satisfactoriamente.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No se ha encontrado el libro con ID: " + id);
		}
	}

	@Override
	public Libro buscarUno(String id) {
		// Cargamos todos los libros
		HashMap<String, Libro> libros = mostrarTodos();

		// Verificamos si el libro existe
		if (libros.containsKey(id)) {
			Libro libro = libros.get(id);
			System.out.println(libro.toString());
			return libro;
		} else {
			System.out.println("No se ha encontrado el libro con ID: " + id);
			return null;
		}
	}

	@Override
	public void guardarLibros(HashMap<String, Libro> libros) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(textFile));
			for(Entry<String, Libro> entry : libros.entrySet()) {
				Libro libro=entry.getValue();
				bw.write(libro.getId()+","+libro.getTitulo()+","+libro.getAutor()+","+libro.getIsbn()+","+libro.getAnno()+"\n");
			}
			bw.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}