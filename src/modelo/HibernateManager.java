package modelo;

import java.util.HashMap;

import org.hibernate.Query;
import org.hibernate.Session;

import utils.HibernateUtil;
import utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Iterator;
import java.util.List;

public class HibernateManager implements AManagerInterface {

	private Session session;
	public HibernateManager(Session session) {
		this.session = session;
	}
	
	@Override
	public HashMap<String, Libro> mostrarTodos() {
		// TODO Auto-generated method stub
		HashMap<String, Libro> librosMap = new HashMap<>();
		Query<Libro> query = session.createQuery("select l from Libro l", Libro.class);
		List<Libro> libros = query.getResultList();
		for (Libro libro : libros) {
			librosMap.put(libro.getId(), libro);
			}
		return librosMap; }

	@Override
	public Libro insertarUno(Libro libro) {
		Transaction transaction = session.beginTransaction();
		session.save(libro);
		transaction.commit();
		System.out.println("Libro INSERTADO! " + libro);
		return libro;
	}

	@Override
	public Libro modificarUno(Libro libro) {
		Transaction transaction = session.beginTransaction();
		Libro libroExistente = session.get(Libro.class, libro.getId());
		if (libroExistente != null) {
			libroExistente.setTitulo(libro.getTitulo());
			libroExistente.setAutor(libro.getAutor());
			libroExistente.setIsbn(libro.getIsbn());
			libroExistente.setAnno(libro.getAnno());
			session.update(libroExistente);
			transaction.commit();
			System.out.println("Libro MODIFICADO! " + libroExistente);
			return libroExistente; 
			} else {
				System.out.println("Libro NO ENCONTRADO! " + libro.getId());
				
			return null; 
			
			}
		}
	

	@Override
	public void borrarUno(String id) {
		Transaction transaction = session.beginTransaction();
		Libro libro = session.get(Libro.class, id);
		if (libro != null) {
			session.delete(libro);
			transaction.commit();
			System.out.println("Libro eliminado " + libro);
			} else {
				System.out.println("Libro NO encontrado " + id); 
				}
		}

	@Override
	public Libro buscarUno(String id) {
		Libro libro = session.get(Libro.class, id);
		if (libro != null) {
			System.out.println("Libro encontrado " + libro);
			} else {
				System.out.println("Libro NO encontrado " + id);
				} 
		return libro; 
		}

	@Override
	public void guardarLibros(HashMap<String, Libro> libros) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hacerCopia(String tipoFichero) {

	}
}
