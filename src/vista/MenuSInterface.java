package vista;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import com.sun.xml.bind.v2.runtime.Name;
import modelo.AManagerInterface;
import modelo.Libro;
import net.bytebuddy.asm.Advice;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class MenuSInterface extends JFrame {
    private JTable table;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton showAllButton, insertButton, modifyButton, deleteButton, searchButton, backButton, saveButton;
    private JPanel formPanel;
    private JTextField idField, tituloField, autorField, isbnField, annoField;
    private boolean isModifying = false; // Variable para saber si estamos modificando

    private JLabel actionLabel;

    public MenuSInterface(String dataType, AManagerInterface fileManager, MenuPrincipal menuPrincipal) {
        setTitle("Menú CRUD (" + dataType + ")");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        String text = "Gestión de datos : " + dataType;
       // actionLabel = new JLabel("Bienvenido al programa 'Gestion de Datos' : " + dataType);
        actionLabel = new JLabel(text);
        actionLabel.setFont(new Font("Arial", Font.PLAIN, 20));




        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Titulo", "Autor"}, 0));
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.setRowHeight(30); //altura de las filas





// Cambiar color de las celdas
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);// Alternar colores de filas
                cell.setForeground(Color.BLUE); // Cambiar color de texto return cell;
                return cell;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);


        scrollPane = new JScrollPane(table);
      //  scrollPane.setVisible(false); //Inicialmente invisible
        scrollPane.setVisible(true);

        // textArea = new JTextArea(10, 30);
        showAllButton = new JButton("Mostrar todos", new ImageIcon(getClass().getResource("/images/global.png")));
        showAllButton.setFont(new Font("Arial", Font.PLAIN, 20));
        // showAllButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Añadir borde de color
        showAllButton.setBackground(Color.LIGHT_GRAY);

        insertButton = new JButton("Insertar", new ImageIcon(getClass().getResource("/images/libro.png")));
        insertButton.setFont(new Font("Arial", Font.PLAIN, 20));
        insertButton.setBackground(Color.LIGHT_GRAY);

        modifyButton = new JButton("Modificar", new ImageIcon(getClass().getResource("/images/editar.png")));
        modifyButton.setFont(new Font("Arial", Font.PLAIN, 20));
        modifyButton.setBackground(Color.LIGHT_GRAY);

        deleteButton = new JButton("Borrar", new ImageIcon(getClass().getResource("/images/borrar-cuenta.png")));
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 20));
        deleteButton.setBackground(Color.LIGHT_GRAY);

        searchButton = new JButton("Buscar", new ImageIcon(getClass().getResource("/images/buscar.png")));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 20));
        // searchButton.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
        searchButton.setBackground(Color.LIGHT_GRAY);

        backButton = new JButton("Volver", new ImageIcon(getClass().getResource("/images/pagina-de-inicio.png")));
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setBackground(Color.LIGHT_GRAY);

        // Añadir MouseListener para cambiar el cursor y el color del botón al pasar el mouse por encima
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Color originalColor;

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                originalColor = button.getBackground();
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.setBackground(Color.pink); // Cambiar color al pasar el mouse por encima
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                button.setBackground(originalColor); // Restaurar color original
            }
        };

        showAllButton.addMouseListener(mouseAdapter);
        insertButton.addMouseListener(mouseAdapter);
        modifyButton.addMouseListener(mouseAdapter);
        deleteButton.addMouseListener(mouseAdapter);
        searchButton.addMouseListener(mouseAdapter);
        backButton.addMouseListener(mouseAdapter);

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
        //insertButton.addActionListener(e -> {
        //  Libro nuevoLibro = crearLibroDesdeInput();
        // fileManager.insertarUno(nuevoLibro);
        //actualizarTabla(fileManager.mostrarTodos());
        //  textArea.setText("Libro insertado: " + nuevoLibro.toString());
        //});

        insertButton.addActionListener(e -> {
            isModifying = false; // ----inserta

            actionLabel.setText(text+" ¿Añadir nuevo libro ?");
            //mostrarFormulario();
            habilitarFormulario();
        });

        modifyButton.addActionListener(e -> {
            isModifying = true; // --actualiza

            actionLabel.setText("¿Quieres modificar un Libro?");
            //mostrarFormulario();
            habilitarFormulario();
        });


        //------------------------MODIFICAR
/*        modifyButton.addActionListener(e -> {
            Libro modificarLibro = crearLibroDesdeInput();
            fileManager.modificarUno(modificarLibro);
            actualizarTabla(fileManager.mostrarTodos());
            // textArea.setText("Libro modificado: " + modificarLibro.toString());
        });
  */
        //-----------------------DELETE
        deleteButton.addActionListener(e -> {
            inhabilitarFormulario();

            actionLabel.setText(text);

            String idBorrar = JOptionPane.showInputDialog("Introduzca el ID del libro que desea borrar:");
            fileManager.borrarUno(idBorrar);
            actualizarTabla(fileManager.mostrarTodos());
            // textArea.setText("Libro borrado con ID: " + idBorrar);

        });
        //----------------------BUSCAR
        searchButton.addActionListener(e -> {
            inhabilitarFormulario();
            actionLabel.setText(text);

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

        //setLayout(new BorderLayout());


        //JPanel topPanel = new JPanel(new BorderLayout());
        //topPanel.add(actionLabel, BorderLayout.NORTH);
        //  topPanel.add(scrollPane, BorderLayout.CENTER);
        //dd(topPanel, BorderLayout.CENTER);


        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(actionLabel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.add(showAllButton);
        panel.add(insertButton);
        panel.add(modifyButton);
        panel.add(deleteButton);
        panel.add(searchButton);
        panel.add(backButton);


        add(panel, BorderLayout.SOUTH);

        //--------------------------------
        formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Título:"));
        tituloField = new JTextField();
        formPanel.add(tituloField);

        formPanel.add(new JLabel("Autor:"));
        autorField = new JTextField();
        formPanel.add(autorField);

        formPanel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        formPanel.add(isbnField);

        formPanel.add(new JLabel("Año:"));
        annoField = new JTextField();
        formPanel.add(annoField);

        saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> {
            if (!idField.getText().isEmpty() && !tituloField.getText().isEmpty() && !autorField.getText().isEmpty() && !isbnField.getText().isEmpty() && !annoField.getText().isEmpty()) {
                Libro libro = new Libro(
                        idField.getText(),
                        tituloField.getText(),
                        autorField.getText(),
                        isbnField.getText(),
                        Integer.parseInt(annoField.getText())
                );
                if (isModifying) {
                    fileManager.modificarUno(libro);
                } else {
                    fileManager.insertarUno(libro);
                }
                actualizarTabla(fileManager.mostrarTodos());
              //  ocultarFormulario();
                inhabilitarFormulario();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            }
        });
        formPanel.add(saveButton);

        add(formPanel, BorderLayout.NORTH);
        //formPanel.setVisible(false); // Inicialmente invisible
        formPanel.setVisible(true);
        limpiarFormulario();
        inhabilitarFormulario();
    }
        //panel.add(new JScrollPane(textArea));

        //add(panel);

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

 //   private void mostrarFormulario() {
   //     formPanel.setVisible(true);
    //}

   // private void ocultarFormulario() {
     //   formPanel.setVisible(false);
    //}


    private void habilitarFormulario() {
        idField.setEnabled(true);
        tituloField.setEnabled(true);
        autorField.setEnabled(true);
        isbnField.setEnabled(true);
        annoField.setEnabled(true);
        saveButton.setEnabled(true);
    }

    private void inhabilitarFormulario() {
        idField.setEnabled(false);
        tituloField.setEnabled(false);
        autorField.setEnabled(false);
        isbnField.setEnabled(false);
        annoField.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void limpiarFormulario() {
        idField.setText("");
        tituloField.setText("");
        autorField.setText("");
        isbnField.setText("");
        annoField.setText("");
    }


}
