package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import com.mongodb.MongoClient;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class FileMongoDBManager implements AManagerInterface {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    //PARA EJECUTAR INICIAR SERVIDOR...:\mongoDB\bin>mongod

    public FileMongoDBManager() {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.database = mongoClient.getDatabase("bdproyectoIntegrador");
        this.collection = database.getCollection("libro");

        // si no existe --->>>>>

        // Verificar si la colección ya existe
        if (!database.listCollectionNames().into(new ArrayList<>()).contains("libro")) {
            database.createCollection("libro");
        }

        //si existe da pálante----
        this.collection = database.getCollection("libro");
    }


    @Override
    public HashMap<String, Libro> mostrarTodos() {
        HashMap<String, Libro> libros = new HashMap<>();
        for(Document doc : collection.find()) {
            Libro libro = new Libro(
                    doc.getString("id"),
                    doc.getString("titulo"),
                    doc.getString("autor"),
                    doc.getString("isbn"),
                    doc.getInteger("anno")
                    //doc.getString("ca")
            );
            libros.put(libro.getId(), libro);
        }
        return libros;
    }

    @Override
    public Libro insertarUno(Libro libro) {
        Document doc = new Document("id", libro.getId())
                .append("titulo", libro.getTitulo())
                .append("autor", libro.getAutor())
                .append("isbn", libro.getIsbn())
                .append("anno", libro.getAnno());
        collection.insertOne(doc);
        return libro;
    }

    @Override
    public Libro modificarUno(Libro libro) {
        Document doc = new Document("id", libro.getId())
                .append("titulo", libro.getTitulo())
                .append("autor", libro.getAutor())
                .append("isbn", libro.getIsbn())
                .append("anno", libro.getAnno())
                .append("categoria", libro.getCategoria());
        collection.replaceOne(new Document("id", libro.getId()), doc);
        return libro;
    }

    @Override
    public void borrarUno(String id) {
        collection.deleteOne(new Document("id", id));
    }

    @Override
    public Libro buscarUno(String id) {
        Document doc = collection.find(new Document("id", id)).first();
        if (doc != null) {
            return new Libro(
                    doc.getString("id"),
                    doc.getString("titulo"),
                    doc.getString("autor"),
                    doc.getString("isbn"),
                    doc.getInteger("anno")
                    //doc.getString("categoria")
            );
        }
        return null;
    }

    @Override
    public void guardarLibros(HashMap<String, Libro> libros) {
        for (Libro libro : libros.values()) {
            insertarUno(libro);
        }
    }
}
