package modelo;

import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class FileSQliteManager implements AManagerInterface {

    private static final String DATABASE_URL = "jdbc:sqlite:libros.db";

    public FileSQliteManager() {
        createDatabase();
        createTable();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            if (conn != null) {
                System.out.println("Ya existe la base de datos ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS libros (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " titulo TEXT NOT NULL,\n"
                + " autor TEXT NOT NULL,\n"
                + " isbn TEXT NOT NULL UNIQUE,\n"
                + " anno INTEGER NOT NULL\n"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'libros' created or already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public HashMap<String, Libro> mostrarTodos() {
        String sql = "SELECT * FROM libros";
        HashMap<String, Libro> libros = new HashMap<>();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getString("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAnno(rs.getInt("anno"));
                libros.put(libro.getId(), libro);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return libros;
    }

    @Override
    public Libro insertarUno(Libro libro) {
        String sql = "INSERT INTO libros(titulo, autor, isbn, anno) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getIsbn());
            pstmt.setInt(4, libro.getAnno());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                libro.setId(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return libro;
    }

    @Override
    public Libro modificarUno(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, anno = ? WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getIsbn());
            pstmt.setInt(4, libro.getAnno());
            pstmt.setString(5, libro.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return libro;
    }

    @Override
    public void borrarUno(String id) {
        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Libro buscarUno(String id) {
        String sql = "SELECT * FROM libros WHERE id = ?";
        Libro libro = null;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                libro = new Libro();
                libro.setId(rs.getString("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAnno(rs.getInt("anno"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
}