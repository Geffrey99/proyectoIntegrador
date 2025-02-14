package vista;
import controlador.book;
import controlador.premio;
import modelo.AManagerInterface;
import modelo.Libro;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;


public class MenuSInterface extends JFrame {
    private JTable table;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton showAllButton, insertButton, modifyButton, deleteButton, searchButton, backButton, saveButton, BttTrans;
    private JPanel formPanel;
    private JTextField idField, tituloField, autorField, isbnField, annoField;
    private boolean isModifying = false; // Variable para saber si estamos modificando
    private JLabel actionLabel;

    private book bookForm;
    private premio premioForm;

    public MenuSInterface(String dataType, AManagerInterface fileManager, MenuPrincipal menuPrincipal) {
        setTitle("Menú CRUD (" + dataType + ")");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon miIcono = new ImageIcon(getClass().getResource("/images/JUST.jpg"));
        setIconImage(miIcono.getImage());

        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Elige opción", "Fichero", "XML", "Binario", "MySql", "Hibernate", "SqLite", "Php", "MongoDB", "ObjectDB", "BaseX"});
        comboBox.setBackground(Color.BLACK);
        comboBox.setForeground(Color.GREEN);
        comboBox.setFont(new Font("Monospaced", Font.BOLD, 15));

        // Deshabilitar la opción "Elige opción"
        comboBox.setSelectedIndex(-1);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == 0) {
                    renderer.setEnabled(false);
                } else {
                    renderer.setEnabled(true);
                }
                return renderer;
            }
        });


        // Crear y configurar el submitButton
        BttTrans = new JButton("Seleccionar");
        BttTrans.setBackground(Color.BLACK);
        BttTrans.setForeground(Color.GREEN);
        BttTrans.setFont(new Font("Monospaced", Font.BOLD, 15));

        // Agregar ActionListener al submitButton
        BttTrans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                if (!"Elige opción".equals(selectedOption)) {
                    handleSelection(selectedOption);
                } else {
                    JOptionPane.showMessageDialog(MenuSInterface.this, "Por favor, elige una opción válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Crear un panel para contener el comboBox y el submitButton
        JPanel panelTrans = new JPanel();
        panelTrans.setLayout(new FlowLayout());
        panelTrans.add(comboBox);
        panelTrans.add(BttTrans);

        add(panelTrans);


        String text = "Gestión de datos : " + dataType;
       // actionLabel = new JLabel("Bienvenido al programa 'Gestion de Datos' : " + dataType);
        actionLabel = new JLabel(text);
        actionLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Titulo", "Autor", "ISBN", "Año"}, 0));
        table.setFont(new Font("Monospaced", Font.PLAIN, 15));
        table.setRowHeight(30); //altura de las filas


// Cambiar color de las celdas
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setBackground(row % 2 == 0 ? Color.GRAY : Color.black);// Alternar colores de filas
                cell.setForeground(Color.white); // Cambiar color de texto return cell;
                if (value != null) {
                    setText(value.toString().toUpperCase()); // Convertir el texto a mayúsculas
                }
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

        insertButton.addActionListener(e -> {
            isModifying = false; // ----inserta
            actionLabel.setText(text+"¿Quieres añadir nuevo Libro?"); //PARA QUE VEA EN QUE ACCION ESTÁ
            //mostrarFormulario();
            habilitarFormulario();
        });

        modifyButton.addActionListener(e -> {
            isModifying = true; // --actualiza
            actionLabel.setText(text+ "¿Quieres modificar un Libro?");
            //mostrarFormulario();
            habilitarFormulario();
        });

        deleteButton.addActionListener(e -> {
            inhabilitarFormulario();
            actionLabel.setText(text);
            String idBorrar = JOptionPane.showInputDialog("Introduzca el ID del libro que desea borrar:");
            fileManager.borrarUno(idBorrar);
            actualizarTabla(fileManager.mostrarTodos());
            // textArea.setText("Libro borrado con ID: " + idBorrar);

        });
        //----------------------BUSCAR
        //    table = new JTable(new DefaultTableModel(new Object[]{"ID", "Titulo", "Autor", "ISBN", "Año"}, 0));
        searchButton.addActionListener(e -> {
            inhabilitarFormulario();
            actionLabel.setText(text);

            String idBuscar = JOptionPane.showInputDialog("Introduzca el ID del libro que desea buscar:");
            Libro resultado = fileManager.buscarUno(idBuscar);
            if (resultado != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Limpiar filas existentes
                model.addRow(new Object[]{resultado.getId(), resultado.getTitulo(), resultado.getAutor(), resultado.getIsbn(), resultado.getAnno()});
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


        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(actionLabel, BorderLayout.NORTH);
       // topPanel.add(panelTrans, BorderLayout.SOUTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(showAllButton);
        panel.add(insertButton);
        panel.add(modifyButton);
        panel.add(deleteButton);
        panel.add(searchButton);
        panel.add(backButton);
        panel.add(panelTrans);
        panel.setBackground(Color.black);

        add(panel, BorderLayout.SOUTH);


        bookForm = new book();
        premioForm = new premio();

        JPanel formPanel = new JPanel(new GridLayout(1, 2));
        formPanel.add(bookForm.getMainPanel());
        formPanel.add(premioForm.getMainPremio());

        add(formPanel, BorderLayout.NORTH); // Mostrar los formularios uno al lado del otro
        formPanel.setVisible(true); // Mostrar el formulario al abrir
        inhabilitarFormulario();
        inhabilitarFormularioPremio();

        bookForm.getGuardarButton().addActionListener(e -> {
            if (!bookForm.getTextField1().getText().isEmpty() &&
                    !bookForm.getTextField2().getText().isEmpty() &&
                    !bookForm.getTextField3().getText().isEmpty() &&
                    !bookForm.getTextField4().getText().isEmpty() &&
                    !bookForm.getTextField5().getText().isEmpty())
            {
                Libro libro = new Libro(
                        bookForm.getTextField1().getText(),
                        bookForm.getTextField2().getText(),
                        bookForm.getTextField3().getText(),
                        bookForm.getTextField4().getText(),
                        Integer.parseInt(bookForm.getTextField5().getText())
                );
                if (isModifying) {
                    fileManager.modificarUno(libro);
                } else {
                    fileManager.insertarUno(libro);
                }
                actualizarTabla(fileManager.mostrarTodos());
                limpiarFormulario(); // Limpiar el formulario
                inhabilitarFormulario();
                inhabilitarFormularioPremio();
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            }
        });

    }
    private void actualizarTabla(HashMap<String, Libro> todosLosLibros) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar filas
        todosLosLibros.forEach((id, libro) -> model.addRow(new Object[]{id, libro.getTitulo(), libro.getAutor(), libro.getIsbn(), libro.getAnno()}));
    }


//-----------------------------------------------------------------------Libro

    private void habilitarFormulario() {
        bookForm.getTextField1().setEnabled(true);
        bookForm.getTextField2().setEnabled(true);
        bookForm.getTextField3().setEnabled(true);
        bookForm.getTextField4().setEnabled(true);
        bookForm.getTextField5().setEnabled(true);
        bookForm.getGuardarButton().setEnabled(true);
        bookForm.getCancelarButton().setEnabled(true);
    }

    private void inhabilitarFormulario() {
        bookForm.getTextField1().setEnabled(false);
        bookForm.getTextField2().setEnabled(false);
        bookForm.getTextField3().setEnabled(false);
        bookForm.getTextField4().setEnabled(false);
        bookForm.getTextField5().setEnabled(false);
        bookForm.getGuardarButton().setEnabled(false);
        bookForm.getCancelarButton().setEnabled(false);

    }

    private void limpiarFormulario() {
        bookForm.getTextField1().setText("");
        bookForm.getTextField2().setText("");
        bookForm.getTextField3().setText("");
        bookForm.getTextField4().setText("");
        bookForm.getTextField5().setText("");
    }


    //-----------------------------------------------------------------------Premio
    private void habilitarFormularioPremio() {
        premioForm.getTextField1().setEnabled(true);
        premioForm.getTextField2().setEnabled(true);
        premioForm.getTextField3().setEnabled(true);
        premioForm.getComboBox1().setEnabled(true);
    }
    private void inhabilitarFormularioPremio() {
        premioForm.getTextField1().setEnabled(false);
        premioForm.getTextField2().setEnabled(false);
        premioForm.getTextField3().setEnabled(false);
        premioForm.getComboBox1().setEnabled(false);
        premioForm.getACEPTARButton().setEnabled(false);
        premioForm.getCANCELARButton().setEnabled(false);
    }


    private void limpiarFormularioPremio() {
        premioForm.getTextField1().setText("");
        premioForm.getTextField2().setText("");
        premioForm.getTextField3().setText("");
        premioForm.getComboBox1().setActionCommand("");

    }



private void handleSelection(String dataType) {
    // Aquí puedes agregar el código para manejar la selección y realizar la acción correspondiente
    JOptionPane.showMessageDialog(this, "Seleccionaste: " + dataType, "Información", JOptionPane.INFORMATION_MESSAGE);
}


}
