package modelo;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import utils.ApiRequests;
import java.util.HashMap;

//ok todo funcionando --CRUD

public class FilePhpManager implements AManagerInterface {
    ApiRequests encargadoPeticiones;
    private String SERVER_PATH, GET_PLAYER;

    public FilePhpManager() {
        encargadoPeticiones = new ApiRequests();
        SERVER_PATH = "http://localhost/geffDam/proyectoIntegrador/";
        GET_PLAYER = "librOk.php";
    }

    @Override
    public HashMap<String, Libro> mostrarTodos() {
        return lee();
    }

    @Override
    public Libro insertarUno(Libro libro) {
        añadirLibro(libro);
        return libro;
    }

    @Override
    public Libro modificarUno(Libro libro) {
        editarLibro(libro);
        return libro;
    }

    @Override
    public void borrarUno(String id) {
        eliminarLibro(id);
    }

    @Override
    public Libro buscarUno(String id) {
        return buscarLibro(id);
    }

    @Override
    public void guardarLibros(HashMap<String, Libro> libros) {
        // Implementación opcional si es necesario
    }

    public HashMap<String, Libro> lee() {
        HashMap<String, Libro> auxhm = new HashMap<>();
        try {
            String url = SERVER_PATH + GET_PLAYER;
            String response = encargadoPeticiones.getRequest(url);
            JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

            if (respuesta != null && "ok".equals(respuesta.get("estado"))) {
                JSONArray array = (JSONArray) respuesta.get("libros");
                for (Object o : array) {
                    JSONObject row = (JSONObject) o;
                    Libro nuevoLib = new Libro(
                        row.get("id").toString(),
                        row.get("titulo").toString(),
                        row.get("autor").toString(),
                        row.get("isbn").toString(),
                        Integer.parseInt(row.get("anno").toString())
                    );
                    auxhm.put(nuevoLib.getId(), nuevoLib);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auxhm;
    }

    public void añadirLibro(Libro libro) {
        try {
            String url = SERVER_PATH + "librok.php";
            String jsonInputString = String.format(
                "{\"id\":\"%s\",\"accion\":\"añadir\",\"titulo\":\"%s\",\"autor\":\"%s\",\"isbn\":\"%s\",\"anno\":%d}",
                libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getIsbn(), libro.getAnno()
            );
            String response = encargadoPeticiones.postRequest(url, jsonInputString);
            System.out.println("Respuesta: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void eliminarLibro(String id) {
        try {
            String url = SERVER_PATH + "librok.php";
            String jsonInputString = String.format("{\"accion\":\"eliminar\",\"id\":\"%s\"}", id);
            String response = encargadoPeticiones.deleteRequest(url, jsonInputString);
            System.out.println("Respuesta: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarLibro(Libro libro) {
        try {
            String url = SERVER_PATH + "librok.php";
            String jsonInputString = String.format(
                "{\"accion\":\"editar\",\"id\":\"%s\",\"titulo\":\"%s\",\"autor\":\"%s\",\"isbn\":\"%s\",\"anno\":%d}",
                libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getIsbn(), libro.getAnno()
            );
            String response = encargadoPeticiones.putRequest(url, jsonInputString);
            System.out.println("Respuesta: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Libro buscarLibro(String id) {
        Libro libro = null;
        try {
            String url = SERVER_PATH + "librok.php?accion=buscar&id=" + id;
            String response = encargadoPeticiones.getRequest(url);
            System.out.println("Respuesta del servidor: " + response); // Añadir traza para depuración

            JSONObject respuesta = (JSONObject) JSONValue.parse(response);

            if (respuesta != null && "ok".equals(respuesta.get("estado"))) {
                JSONObject row = (JSONObject) respuesta.get("libro");
                libro = new Libro(
                    row.get("id").toString(),
                    row.get("titulo").toString(),
                    row.get("autor").toString(),
                    row.get("isbn").toString(),
                    Integer.parseInt(row.get("anno").toString())
                );
            } else {
                System.out.println("No se encontró el libro con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return libro;
    }

}
