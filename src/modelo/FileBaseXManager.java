package modelo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.io.*;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashMap;

public class FileBaseXManager implements AManagerInterface {
    private String filePath;
    private HashMap<String, Libro> libros;

    public FileBaseXManager(String filePath) {
        this.filePath = "Ficheros/" + filePath;
        this.libros = cargarLibros();
    }

    @Override
    public HashMap<String, Libro> mostrarTodos() {
        return libros;
    }

    @Override
    public Libro insertarUno(Libro libro) {
        libros.put(libro.getId(), libro);
        guardarLibros(libros);
        return libro;
    }

    @Override
    public Libro modificarUno(Libro libro) {
        if (libros.containsKey(libro.getId())) {
            libros.put(libro.getId(), libro);
            guardarLibros(libros);
            return libro;
        }
        return null;
    }

    @Override
    public void borrarUno(String id) {
        if (libros.containsKey(id)) {
            libros.remove(id);
            guardarLibros(libros);
        }
    }

    @Override
    public Libro buscarUno(String id) {
        return libros.get(id);
    }

    @Override
    public void guardarLibros(HashMap<String, Libro> libros) {
        // Aquí deberías implementar la lógica para guardar los libros en el archivo XML.
        // Esto puede hacerse utilizando una biblioteca como JAXB o manualmente escribiendo el XML.
        // Para este ejemplo, vamos a dejarlo como un método no implementado.
        throw new UnsupportedOperationException("Método guardarLibros no implementado.");
    }

    private HashMap<String, Libro> cargarLibros() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            LibroHandler handler = new LibroHandler();
            saxParser.parse(new File(filePath), handler);
            return handler.getLibros();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}