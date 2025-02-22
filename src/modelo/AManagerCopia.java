package modelo;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
//import org.bson.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//TODO -CLase-- para exportar datos a otro tipo
// Por terminar - hecho 6/10 métodos ! Okkkkkk |Probado pero no implementado  ✔️✔️✔️

public class AManagerCopia {
//    private HashMap<String, Libro> libros;
//
//    public AManagerCopia(HashMap<String, Libro> libros) {
//        if (libros == null) {
//            this.libros = new HashMap<>();
//        } else {
//            this.libros = libros;
//        }
//    }
//
//    public void hacerCopia(String tipoFichero) {
//        switch (tipoFichero) {
//            case "Fichero":
//                guardarComoFichero();
//                break;
//            case "XML":
//                guardarComoXML();
//                break;
//            case "Binario":
//                guardarComoBinario();
//                break;
//            case "MySql":
//                guardarComoMySql();
//                break;
//            case "Hibernate":
//                guardarComoHibernate();
//                break;
//            case "SqLite":
//                guardarComoSqLite();
//                break;
//            case "Php":
//                guardarComoPhp();
//                break;
//            case "MongoDB":
//            //    guardarComoMongoDB();
//                break;
//            case "ObjectDB":
//                guardarComoObjectDB();
//                break;
//            case "BaseX":
//                guardarComoBaseX();
//                break;
//            default:
//                System.out.println("Tipo de fichero no soportado.");
//                break;
//        }
//    }

    //    //Okkkkkk ✔️✔️✔️
//    private void guardarComoFichero() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Ficheros/Libros.txt"))) {
//            for (Libro libro : libros.values()) {
//                writer.write(libro.toString());
//                writer.newLine();
//            }
//            System.out.println("Datos exportados correctamente a Ficheros/Libros.txt");
//        } catch (IOException e) {
//            System.out.println("Ocurrió un error al escribir en el archivo de texto.");
//            e.printStackTrace();
//        }
//    }



//    //Okkkkkk ✔️✔️✔️
////    private void guardarComoXML() {
////        try {
////            File xmlFile = new File("Ficheros/Libros.xml");
////            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
////            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
////            Document doc;
////
////            // Si el archivo existe, cargar su contenido
////            if (xmlFile.exists()) {
////                doc = docBuilder.parse(xmlFile);
////                doc.getDocumentElement().normalize();
////            } else {
////                // Si el archivo no existe, crear un nuevo documento
////                doc = docBuilder.newDocument();
////                Element rootElement = doc.createElement("libros");
////                doc.appendChild(rootElement);
////            }
////
////            Element rootElement = doc.getDocumentElement();
////
////            // Crear una lista de IDs existentes
////            NodeList nList = doc.getElementsByTagName("libro");
////            HashSet<String> existingIds = new HashSet<>();
////            for (int temp = 0; temp < nList.getLength(); temp++) {
////                Node nNode = nList.item(temp);
////                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
////                    Element eElement = (Element) nNode;
////                    String id = eElement.getElementsByTagName("id").item(0).getTextContent();
////                    existingIds.add(id);
////                }
////            }
////
////            // Agregar solo los libros nuevos
////            for (Libro libro : libros.values()) {
////                if (!existingIds.contains(libro.getId())) {
////                    Element libroElement = doc.createElement("libro");
////                    rootElement.appendChild(libroElement);
////
////                    Element id = doc.createElement("id");
////                    id.appendChild(doc.createTextNode(libro.getId()));
////                    libroElement.appendChild(id);
////
////                    Element titulo = doc.createElement("titulo");
////                    titulo.appendChild(doc.createTextNode(libro.getTitulo()));
////                    libroElement.appendChild(titulo);
////
////                    Element autor = doc.createElement("autor");
////                    autor.appendChild(doc.createTextNode(libro.getAutor()));
////                    libroElement.appendChild(autor);
////
////                    Element isbn = doc.createElement("isbn");
////                    isbn.appendChild(doc.createTextNode(libro.getIsbn()));
////                    libroElement.appendChild(isbn);
////
////                    Element anno = doc.createElement("anno");
////                    anno.appendChild(doc.createTextNode(String.valueOf(libro.getAnno())));
////                    libroElement.appendChild(anno);
////                }
////            }
////
////            TransformerFactory transformerFactory = TransformerFactory.newInstance();
////            Transformer transformer = transformerFactory.newTransformer();
////            DOMSource source = new DOMSource(doc);
////            StreamResult result = new StreamResult(xmlFile);
////            transformer.transform(source, result);
////
////            System.out.println("Datos exportados correctamente a Ficheros/Libros.xml");
////        } catch (Exception e) {
////            System.out.println("Ocurrió un error al escribir en el archivo XML.");
////            e.printStackTrace();
////        }
////    }
//

    //Okkkkkk ✔️✔️✔️
//    private void guardarComoBinario() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Ficheros/Libros.bin"))) {
//            oos.writeObject(libros);
//            System.out.println("Datos exportados correctamente a Ficheros/Libros.bin"");
//        } catch (IOException e) {
//            System.out.println("Ocurrió un error al escribir en el archivo binario.");
//            e.printStackTrace();
//        }
//    }



    // Okkkkkk ✔️✔️✔️
//    private void guardarComoMySql() {
//        String url = "jdbc:mysql://localhost:3306/proyectoIntegrador";
//        String user = "tu_usuario";
//        String password = "tu_contraseña";
//
//        try (Connection conn = DriverManager.getConnection(url, user, password)) {
//            String query = "INSERT INTO libros (id, titulo, autor, isbn, anno) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement pstmt = conn.prepareStatement(query);
//
//            for (Libro libro : libros.values()) {
//                pstmt.setString(1, libro.getId());
//                pstmt.setString(2, libro.getTitulo());
//                pstmt.setString(3, libro.getAutor());
//                pstmt.setString(4, libro.getIsbn());
//                pstmt.setInt(5, libro.getAnno());
//                pstmt.executeUpdate();
//            }
//
//            System.out.println("Datos exportados correctamente a MySQL.");
//        } catch (SQLException e) {
//            System.out.println("Ocurrió un error al escribir en la base de datos MySQL.");
//            e.printStackTrace();
//        }
//    }
//

    //TODO ---Por terminar ! Okkkkkk ✔️✔️✔️
//    private void guardarComoHibernate() {
//        // Implementación para guardar con Hibernate
//        System.out.println("Datos exportados correctamente con Hibernate.");
//    }

    // Okkkkkk ✔️✔️✔️
//    private void guardarComoSqLite() {
//        String url = "jdbc:sqlite:libros.db";
//
//        try (Connection conn = DriverManager.getConnection(url)) {
//            String query = "INSERT INTO libros (id, titulo, autor, isbn, anno) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement pstmt = conn.prepareStatement(query);
//
//            for (Libro libro : libros.values()) {
//                pstmt.setString(1, libro.getId());
//                pstmt.setString(2, libro.getTitulo());
//                pstmt.setString(3, libro.getAutor());
//                pstmt.setString(4, libro.getIsbn());
//                pstmt.setInt(5, libro.getAnno());
//                pstmt.executeUpdate();
//            }
//
//            System.out.println("Datos exportados correctamente a SQLite.");
//        } catch (SQLException e) {
//            System.out.println("Ocurrió un error al escribir en la base de datos SQLite.");
//            e.printStackTrace();
//        }
//    }


    //TODO ---Por terminar ! Okkkkkk ✔️✔️✔️
//    private void guardarComoPhp() {
//        // Implementación para guardar vía PHP
//        System.out.println("Datos exportados correctamente vía PHP.");
//    }
//

    // Okkkkkk ✔️✔️✔️
//   // private void guardarComoMongoDB() {
//     //   MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
//       // MongoDatabase database = mongoClient.getDatabase("proyectoIntegrador");
//       // MongoCollection<org.bson.Document> collection = database.getCollection("libros");
//
//        //for (Libro libro : libros.values()) {
//          //  Document doc = new Document("id", libro.getId())
//            //        .append("titulo", libro.getTitulo())
//              //      .append("autor", libro.getAutor())
//                //    .append("isbn", libro.getIsbn())
//                  //  .append("anno", libro.getAnno());
//          //  collection.insertOne(doc);
//        //}
//
//        //mongoClient.close();
//       // System.out.println("Datos exportados correctamente a MongoDB.");
//    //}


    //TODO ---Por terminar ! Okkkkkk ✔️✔️✔️
//    private void guardarComoObjectDB() {
//        // Implementación para guardar en ObjectDB
//        System.out.println("Datos exportados correctamente a ObjectDB.");
//    }


    //TODO ---Por terminar ! Okkkkkk ✔️✔️✔️
//    private void guardarComoBaseX() {
//        // Implementación para guardar en BaseX
//        System.out.println("Datos exportados correctamente a BaseX.");
//    }
}