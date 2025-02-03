package modelo;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import java.util.List;
import java.util.UUID;

//para que pueda escribir y leer objetos de tipo Libro desde archivos binarios
//necesito implementar la interfaz serializable
@XmlRootElement(name = "libros")
@Entity
@Table(name = "libros")
public class Libro implements Serializable {
    private static final long serialVersionUID = 1L; // versión de la clase para la serialización

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "autor", nullable = false)
	private String autor;

	@Column(name = "isbn", nullable = false, unique = true)
	private String isbn;

	@Column(name = "categoria")
	private String categoria;

	@Column(name = "anno", nullable = false)
	private int anno;

	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Premio> premios;

	//public Libro () {};

	private List<Libro> libros;
//PARA TRATAR CON FICHERO XML ---- USADO EN MANAGER BASE X
	@XmlElement(name = "libro")  //indica que cada elemento de la lista será representado como un elemento <libro> en el XML.
	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

	public Libro() {
		this.id = UUID.randomUUID().toString(); // Generar un UUID como ID
	}

	public Libro(String id, String titulo, String autor, String isbn, int anno) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.anno = anno;
	}

	public Libro(String id, String titulo, String autor, String isbn, String categoria, int anno) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.categoria = categoria;
		this.anno = anno;
	}

	public Libro(String titulo, String autor, String id, String isbn, String categoria, int anno, List<Premio> premios) {
		this.titulo = titulo;
		this.autor = autor;
		this.id = id;
		this.isbn = isbn;
		this.categoria = categoria;
		this.anno = anno;
		this.premios = premios;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<Premio> getPremios() {
		return premios;
	}

	public void setPremios(List<Premio> premios) {
		this.premios = premios;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}

    @Override
    public String toString() {
        return "*** LIBRO ***\n" +
               "_______________________________________\n" +
               "• Título_: " + titulo + "\n" +
               "• Autor_: " + autor + "\n" +
               "• ISBN_: " + isbn + "\n" +
               "• Año_: " + anno + "\n" +
               "_________________________________________\n";
    }
	
	
}
