package modelo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.io.File;


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
        Libro librosWrapper = new Libro();
        librosWrapper.setLibros(libros.values().stream().collect(Collectors.toList()));
/*Libros librosWrapper = new Libros()
utilizada para crear un contenedor de la lista de libros,
lo que facilita la serialización de la colección de objetos Libro a un archivo XML utilizando JAXB.
 */
        try {
            JAXBContext context = JAXBContext.newInstance(Libro.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(librosWrapper, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void hacerCopia(String tipoFichero) {
//
//    }

    /*
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
    */
    //OTRA OPCION VALIDA ☑️☑️☑️☑️☑️☑️☑️☑️ oKKKKOKOKOK
   /*Más sencillo y directo, pero puede consumir más memoria,
   ya que carga todo el archivo XML en memoria antes de procesarlo.
---------------------------------------------------------------------------------*/
    private HashMap<String, Libro> cargarLibros() {
        try {
            JAXBContext context = JAXBContext.newInstance(Libro.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Libro librosWrapper = (Libro) unmarshaller.unmarshal(new File(filePath));

            return (HashMap<String, Libro>) librosWrapper.getLibros().stream().collect(Collectors.toMap(Libro::getId, libro -> libro));
        } catch (JAXBException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }


}