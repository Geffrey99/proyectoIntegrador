package modelo;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedWriter;
import java.io.File;


//Archivo XML
public class FileXMLManager implements AManagerInterface{
	 private String filePath; // existe un path para el archivo XML
    //private AManagerCopia dataCopyManager;

    private HashMap<String, Libro> libros;

    // Constructor para inicializar el filePath
    public FileXMLManager(String filePath) {

        this.filePath = new String("Ficheros/" + filePath);
    //    this.dataCopyManager = new AManagerCopia(libros); // Inicializar DataCopyManager
	    }

   // Nuevo atributo para la clase DataCopyManager
	
	@Override
	public HashMap<String, Libro> mostrarTodos() {
		 //Cargamos los libros actuales
		HashMap<String, Libro> libros = new HashMap<>();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(filePath));
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("libro");
			for (int temp = 0; temp < nList.getLength(); temp++) {
             Node nNode = nList.item(temp);
             if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                 Element eElement = (Element) nNode;
                 String id = eElement.getElementsByTagName("id").item(0).getTextContent();
                 String titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
                 String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
                 String isbn = eElement.getElementsByTagName("isbn").item(0).getTextContent();
                 int anno = Integer.parseInt(eElement.getElementsByTagName("anno").item(0).getTextContent());
                 Libro libro = new Libro(id, titulo, autor, isbn, anno);
                 libros.put(id, libro);
             }
         }
			
     } catch (Exception e) {
         e.printStackTrace();
     }
     return libros;
 }

    
	/*
	 * 
	 * INSERTAR UN LIBRO_________________________________________________________________________
	 * 
	 */
	@Override
	public Libro insertarUno(Libro libro) {
		 HashMap<String, Libro> libros = mostrarTodos(); // Cargamos los libros actuales
	        libros.put(libro.getId(), libro); // Insertamos el nuevo libro
	        // Aquí puedes agregar la lógica para guardar los cambios en el XML
	      
	        //se guardan los cambios en el XML
            try {
            	  // Leer el archivo XML existente
                File xmlFile = new File(filePath);
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc;
                
                if (xmlFile.exists()) {
                    doc = docBuilder.parse(xmlFile);
                    doc.getDocumentElement().normalize();
                } else {
                    doc = docBuilder.newDocument();
                    Element rootElement = doc.createElement("libros");
                    doc.appendChild(rootElement);
                }

                // Elemento raíz
                Element rootElement = doc.getDocumentElement();
                    // Añadir el nuevo libro al documento XML
                    Element libroElement = doc.createElement("libro");
                    rootElement.appendChild(libroElement);

                    // Añadir los atributos del libro
                    Element id = doc.createElement("id");
                    id.appendChild(doc.createTextNode(libro.getId()));
                    libroElement.appendChild(id);

                    Element titulo = doc.createElement("titulo");
                    titulo.appendChild(doc.createTextNode(libro.getTitulo()));
                    libroElement.appendChild(titulo);

                    Element autor = doc.createElement("autor");
                    autor.appendChild(doc.createTextNode(libro.getAutor()));
                    libroElement.appendChild(autor);

                    Element isbn = doc.createElement("isbn");
                    isbn.appendChild(doc.createTextNode(libro.getIsbn()));
                    libroElement.appendChild(isbn);

                    Element anno = doc.createElement("anno");
                    anno.appendChild(doc.createTextNode(String.valueOf(libro.getAnno())));
                    libroElement.appendChild(anno);

                    // Escribir el contenido en un archivo XML
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(xmlFile);

                    transformer.transform(source, result);

                    System.out.println("Archivo XML actualizado en: " + filePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                return libro;
            }
    
    /*
     *  EDITAR UN LIBRO 
     */
	@Override
	public Libro modificarUno(Libro libro) {
        //se cargan los libros actuales
		HashMap<String, Libro> libros = mostrarTodos();
        
        if (libros.containsKey(libro.getId())) {
        	  // Modificamos el libro existente
            libros.put(libro.getId(), libro); 
            
         // Guardamos los cambios en el XML
            try {
                // Leer el archivo XML existente
                File xmlFile = new File(filePath);
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc;

                if (xmlFile.exists()) {
                    doc = docBuilder.parse(xmlFile);
                    doc.getDocumentElement().normalize();
                } else {
                    doc = docBuilder.newDocument();
                    Element rootElement = doc.createElement("libros");
                    doc.appendChild(rootElement);
                }
                
                
             // Elemento raíz
                Element rootElement = doc.getDocumentElement();

                /*
                 * Eliminar el libro existente
                 */
                NodeList librosList = rootElement.getElementsByTagName("libro");
                for (int i = 0; i < librosList.getLength(); i++) {
                    Element libroElement = (Element) librosList.item(i);
                    if (libroElement.getElementsByTagName("id").item(0).getTextContent().equals(libro.getId())) {
                        rootElement.removeChild(libroElement);
                        break;
                    }
                }
                
                /*
                 * Añadir el libro modificado --->>>
                 */
                Element libroElement = doc.createElement("libro");
                rootElement.appendChild(libroElement);

                // Añadir los atributos del libro
                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(libro.getId()));
                libroElement.appendChild(id);

                Element titulo = doc.createElement("titulo");
                titulo.appendChild(doc.createTextNode(libro.getTitulo()));
                libroElement.appendChild(titulo);

                Element autor = doc.createElement("autor");
                autor.appendChild(doc.createTextNode(libro.getAutor()));
                libroElement.appendChild(autor);

                Element isbn = doc.createElement("isbn");
                isbn.appendChild(doc.createTextNode(libro.getIsbn()));
                libroElement.appendChild(isbn);

                Element anno = doc.createElement("anno");
                anno.appendChild(doc.createTextNode(String.valueOf(libro.getAnno())));
                libroElement.appendChild(anno);
                
                
            
                // Escribir el contenido en XML ------>
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);

                transformer.transform(source, result);

                System.out.println("Archivo XML actualizado en: " + filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return libro;
    }
    
    /*
     * BORRAR UN LIBRO
     */
	@Override
	public void borrarUno(String id) {
		//se cargan los libros actualeees
        HashMap<String, Libro> libros = mostrarTodos();
        
        if (libros.containsKey(id)) {
        //eliminamos el libro
        	libros.remove(id); 
            System.out.println("Libro con id -> " + id + " ha sido eliminado!");
            /*
             * y se guardan los cambios 
             */
            try {
                // Leer el archivo XML existente
                File xmlFile = new File(filePath);
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc;

                if (xmlFile.exists()) {
                    doc = docBuilder.parse(xmlFile);
                    doc.getDocumentElement().normalize();
                } else {
                    doc = docBuilder.newDocument();
                    Element rootElement = doc.createElement("libros");
                    doc.appendChild(rootElement);
                }
                
             // Elemento raíz
                Element rootElement = doc.getDocumentElement();

                // Eliminar el libro del documento XML
                NodeList librosList = rootElement.getElementsByTagName("libro");
                for (int i = 0; i < librosList.getLength(); i++) {
                    Element libroElement = (Element) librosList.item(i);
                    if (libroElement.getElementsByTagName("id").item(0).getTextContent().equals(id)) {
                        rootElement.removeChild(libroElement);
                        break;
                    }
                }
                
             // Escribir el contenido actualizado en XML
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);

                transformer.transform(source, result);

                System.out.println("Archivo XML actualizado en: " + filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Libro con id -> " + id + " NO ha sido encontrado!");
        }
    }


    @Override
    public Libro buscarUno(String id) {
        //cargo los libros actuales
        HashMap<String, Libro> libros = mostrarTodos();

        // Buscamos el libro por ID
        Libro libro = libros.get(id);
        if (libro != null) {

            System.out.println("\nLibro encontrado: ---->>>>" + libro.getTitulo());
            System.out.println("__________________________________________________");
        } else {
            System.out.println("Libro con id -> " + id + " NO ha sido encontrado!");
        }

        return libro; // Retornamos el libro si existe, o null si no existe
    }
    




	@Override
	public void guardarLibros(HashMap<String, Libro> libros) {


	}

//    @Override
//    public void hacerCopia(String tipoFichero) {
//
//       //dataCopyManager.hacerCopia(tipoFichero);
//    }

    /*------------------------------------------------------------------------------------
     *
     *
     *---NO IMPLEMENTADO---------------TODO -CONVERTIR ARCHIVO XML A .TXT. // .BIN //
     *
     */
    public void escribirLibrosATxt(HashMap<Integer, Libro> libros, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Libro libro : libros.values()) {
                writer.write(libro.toString());
                writer.newLine(); // Para agregar una nueva línea entre libros
            }
            System.out.println("Datos exportados correctamente a " + filePath);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo de texto.");
            e.printStackTrace();
        }
    }

    public void escribirLibrosABinario(HashMap<Integer, Libro> libros, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(libros);  // Escribir todo el HashMap de libros
            System.out.println("Datos exportados correctamente a " + filePath);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo binario.");
            e.printStackTrace();
        }
    }

}