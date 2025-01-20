package vista;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import modelo.AManagerInterface;
import modelo.Libro;
import net.bytebuddy.asm.Advice;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MenuSInterface extends JFrame {
    private JTable table;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton showAllButton, insertButton, modifyButton, deleteButton, searchButton, backButton;

    public MenuSInterface(String dataType, AManagerInterface fileManager, MenuPrincipal menuPrincipal) {
        setTitle("Menú CRUD (" + dataType + ")");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Titulo","Autor"}, 0));
        scrollPane = new JScrollPane(table);
        scrollPane.setVisible(false); //Inicialmente invisible
       // textArea = new JTextArea(10, 30);
        showAllButton = new JButton("Mostrar todos");
        insertButton = new JButton("Insertar");
        modifyButton = new JButton("Modificar");
        deleteButton = new JButton("Borrar");
        searchButton = new JButton("Buscar");
        backButton = new JButton("Volver");

        showAllButton.addActionListener(e -> {
           // HashMap<String, Libro> todosLosLibros = fileManager.mostrarTodos();
           // DefaultTableModel model = (DefaultTableModel) table.getModel();
           //model.setRowCount(0); //LIMPIA FILAS EXISTENTES
          // todosLosLibros.forEach((id, libro)-> model.addRow(new Object[]{id,libro.getTitulo(), libro.getAutor()}));
            actualizarTabla(fileManager.mostrarTodos());
            scrollPane.setVisible(true); //Hacer visible la tabla
           // textArea.setText(convertirHashMapAString(todosLosLibros));
        });

        //-------------------------INSERTAR
        insertButton.addActionListener(e -> {
            Libro nuevoLibro = crearLibroDesdeInput();
            fileManager.insertarUno(nuevoLibro);
          actualizarTabla(fileManager.mostrarTodos());
            //  textArea.setText("Libro insertado: " + nuevoLibro.toString());
        });
        //------------------------MODIFICAR
        modifyButton.addActionListener(e -> {
            Libro modificarLibro = crearLibroDesdeInput();
            fileManager.modificarUno(modificarLibro);
            actualizarTabla(fileManager.mostrarTodos());
            // textArea.setText("Libro modificado: " + modificarLibro.toString());
        });
        //-----------------------DELETE
        deleteButton.addActionListener(e -> {
            String idBorrar = JOptionPane.showInputDialog("Introduzca el ID del libro que desea borrar:");
            fileManager.borrarUno(idBorrar);
            actualizarTabla(fileManager.mostrarTodos());
            // textArea.setText("Libro borrado con ID: " + idBorrar);
        });
        //----------------------BUSCAR
        searchButton.addActionListener(e -> {
            String idBuscar = JOptionPane.showInputDialog("Introduzca el ID del libro que desea buscar:");
            Libro resultado = fileManager.buscarUno(idBuscar);
            if (resultado != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Limpiar filas existentes
                model.addRow(new Object[]{resultado.getId(), resultado.getTitulo(), resultado.getAutor()});
                scrollPane.setVisible(true); // Asegurarse de que la tabla sea visible
            } else {
                JOptionPane.showMessageDialog(null, "Libro no encontrado");
            }
        });
        //--------------------Button de regresaar
        backButton.addActionListener(e -> {
            controlador.MainInterface.main(null);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(showAllButton);
        panel.add(insertButton);
        panel.add(modifyButton);
        panel.add(deleteButton);
        panel.add(searchButton);
        panel.add(backButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        //panel.add(new JScrollPane(textArea));

        //add(panel);
    }

    private void actualizarTabla(HashMap<String,Libro> todosLosLibros){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); //Limpiar filas
        todosLosLibros.forEach((id,libro) -> model.addRow(new Object[]{id,libro.getTitulo(), libro.getAutor()}));
    }

    private Libro crearLibroDesdeInput() {
        String id = JOptionPane.showInputDialog("Introduzca el ID del libro:");
        String titulo = JOptionPane.showInputDialog("Introduzca el título del libro:");
        String autor = JOptionPane.showInputDialog("Introduzca el autor del libro:");
        String isbn = JOptionPane.showInputDialog("Introduzca el ISBN del libro:");
        int anno = Integer.parseInt(JOptionPane.showInputDialog("Introduzca el año de publicación del libro:"));

        return new Libro(id, titulo, autor, isbn, anno);
    }

    private String convertirHashMapAString(HashMap<String, Libro> hashMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Libro> entry : hashMap.entrySet()) {
            sb.append("ID: ").append(entry.getKey()).append(", Libro: ").append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }
}
