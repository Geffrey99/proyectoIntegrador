package modelo;
import javax.persistence.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.persistence.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ObjectDBManager implements AManagerInterface {

    private static final String DATABASE_PATH = "databaseLibro.odb";
    private static EntityManagerFactory emf;

    public ObjectDBManager() {
        // Verificar y crear la base de datos si no existe
        initializeDatabase();
    }

    private void initializeDatabase() {
        File dbFile = new File(DATABASE_PATH);
        if (!dbFile.exists()) {
            System.out.println("Creando la base de datos...");
            emf = Persistence.createEntityManagerFactory(DATABASE_PATH);
            EntityManager em = emf.createEntityManager();
            em.close();
        } else {
            System.out.println("La base de datos ya existe.");
            emf = Persistence.createEntityManagerFactory(DATABASE_PATH);
        }
    }

    @Override
    public HashMap<String, Libro> mostrarTodos() {
        EntityManager em = emf.createEntityManager();
        HashMap<String, Libro> libros = new HashMap<>();
        try {
            TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l", Libro.class);
            List<Libro> resultList = query.getResultList();
            for (Libro libro : resultList) {
                libros.put(libro.getId(), libro);
            }
        } finally {
            em.close();
        }
        return libros;
    }

    @Override
    public Libro insertarUno(Libro libro) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(libro);
            tx.commit();
        } finally {
            em.close();
        }
        return libro;
    }

    @Override
    public Libro modificarUno(Libro libro) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro existingLibro = em.find(Libro.class, libro.getId());
            existingLibro.setTitulo(libro.getTitulo());
            existingLibro.setAutor(libro.getAutor());
            existingLibro.setIsbn(libro.getIsbn());
            existingLibro.setCategoria(libro.getCategoria());
            existingLibro.setAnno(libro.getAnno());
            tx.commit();
        } finally {
            em.close();
        }
        return libro;
    }

    @Override
    public void borrarUno(String id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro libro = em.find(Libro.class, id);
            if (libro != null) {
                em.remove(libro);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Libro buscarUno(String id) {
        EntityManager em = emf.createEntityManager();
        Libro libro = null;
        try {
            libro = em.find(Libro.class, id);
        } finally {
            em.close();
        }
        return libro;
    }

    @Override
    public void guardarLibros(HashMap<String, Libro> libros) {
        for (Libro libro : libros.values()) {
            if (buscarUno(libro.getId()) == null) {
                insertarUno(libro);
            } else {
                modificarUno(libro);
            }
        }
    }

//    @Override
//    public void hacerCopia(String tipoFichero) {
//
//    }


}