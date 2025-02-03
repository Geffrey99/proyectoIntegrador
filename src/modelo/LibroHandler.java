package modelo;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

public class LibroHandler extends DefaultHandler {
    private HashMap<String, Libro> libros;
    private Libro libro;
    private StringBuilder data;

    public LibroHandler() {
        libros = new HashMap<>();
        data = new StringBuilder();
    }

    public HashMap<String, Libro> getLibros() {
        return libros;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("libro")) {
            libro = new Libro();
        }
        data.setLength(0); // Clear the data buffer
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (libro != null) {
            switch (qName) {
                case "id":
                    libro.setId(data.toString());
                    break;
                case "titulo":
                    libro.setTitulo(data.toString());
                    break;
                case "autor":
                    libro.setAutor(data.toString());
                    break;
                case "isbn":
                    libro.setIsbn(data.toString());
                    break;
          /*      case "categoria":
                    libro.setCategoria(data.toString());
                    break; */
                case "anno":
                    libro.setAnno(Integer.parseInt(data.toString()));
                    break;
                case "libro":
                    libros.put(libro.getId(), libro);
                    libro = null;
                    break;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}