package controlador;

import vista.*;
import modelo.*;
import utils.HibernateUtil;


import java.awt.*;

import org.hibernate.Session;

import javax.swing.*;

public class MainInterface {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalGUI menuPrincipalGUI = new MenuPrincipalGUI();
            menuPrincipalGUI.setVisible(true);
        });
    }
}

class MenuPrincipalGUI extends JFrame {
    private JComboBox<String> comboBox;
    private JButton submitButton;
    private JLabel imageLabel;

    public MenuPrincipalGUI() {
        setTitle("Menu Principal");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        comboBox = new JComboBox<>(new String[]{"Fichero", "XML", "Binario", "MySql", "Hibernate", "SqLite", "Php"}); //baseX, dbo, SQLITE
        submitButton = new JButton("Seleccionar");

        submitButton.addActionListener(e -> {
            String dataType = (String) comboBox.getSelectedItem();
            handleSelection(dataType);
        });

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/JUST.jpg")); // Asegúrate de que la imagen esté en la carpeta "resources/images" imageLabel = new JLabel(imageIcon); imageLabel.setPreferredSize(new Dimension(100, 100)); // Ajustar el tamaño del JLabel
        imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(100, 100));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comboBox, BorderLayout.NORTH);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);


        add(panel);
    }

    private void handleSelection(String dataType) {
        AManagerInterface fileManager = null;

        switch (dataType) {
            case "Fichero":
                fileManager = new FileTextManager("Libros.txt");
                break;
            case "XML":
                fileManager = new FileXMLManager("Libros.xml");
                break;
            case "Binario":
                fileManager = new FileBinaryManager();
                break;
            case "MySql":
                fileManager = new FileMysqlManager();
                break;
            case "Hibernate":
                HibernateUtil util = new HibernateUtil();
                Session session = util.getSessionFactory().openSession();
                fileManager = new HibernateManager(session);
                break;
            case "SqLite":
                System.out.println("En proceso...");
                break;
            case "Php":
                fileManager = new FilePhpManager();
                break;
            default:
                System.out.println("Saliendo del programa...");
                return;
        }

        MenuSInterface menuSecundarioGUI = new MenuSInterface(dataType, fileManager,null);
        menuSecundarioGUI.setVisible(true);
        dispose();
    }
}
