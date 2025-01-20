package modelo;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "premios")
public class Premio implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "anno", nullable = false)
    private int anno;

    @ManyToOne
    @Column(name = "libro_id", nullable = false)
    private Libro Libro;


    public Premio() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public Libro getLibro() {
        return Libro;
    }

    public void setLibro(Libro libro) {
        Libro = libro;
    }
}