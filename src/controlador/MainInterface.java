package controlador;

import vista.*;
import modelo.*;
import utils.HibernateUtil;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.hibernate.Session;

import javax.swing.*;

/**
 *
 */
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
        setTitle("GESTIÓN DE DATOS 'G.F.R.Y' "); //
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //Mi icono en el frrame
        ImageIcon miIcono = new ImageIcon(getClass().getResource("/images/JUST.jpg"));
        setIconImage(miIcono.getImage());

        comboBox = new JComboBox<>(new String[]{"Fichero", "XML", "Binario", "MySql", "Hibernate", "SqLite", "Php", "MongoDB", "ObjectDB", "BaseX"});
        comboBox.setBackground(Color.BLACK);
        comboBox.setForeground(Color.GREEN);
        comboBox.setFont(new Font("Monospaced", Font.BOLD,15));

        submitButton = new JButton("Seleccionar");
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.white);
        submitButton.setFont(new Font("Monospaced", Font.BOLD,20));


       /* submitButton.addActionListener(e -> {
            String dataType = (String) comboBox.getSelectedItem();
            handleSelection(dataType);
        });
*/


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Solicitar clave
                JPanel panel = new JPanel(new BorderLayout());
                JLabel label = new JLabel("CONTRASEÑA : ");
                label.setFont(new Font("Arial", Font.PLAIN, 10));
                //JPasswordField pass = new JPasswordField(10);
                JPasswordField pass = new JPasswordField();
                pass.setPreferredSize(new Dimension(pass.getPreferredSize().width, 20));
                panel.add(label, BorderLayout.WEST);
                panel.add(pass, BorderLayout.CENTER);
                int option = JOptionPane.showConfirmDialog(MenuPrincipalGUI.this, panel, "Autenticación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                //if ("accesoadatos".equals(clave)) {
                  if (option == JOptionPane.OK_OPTION) {
                      String miClave = new String(pass.getPassword());
                      if ("datos".equals(miClave)) {
                          JOptionPane.showMessageDialog(MenuPrincipalGUI.this, "Clave correcta ✅");
                          // new SiguientePantalla().setVisible(true);
                          String dataType = (String) comboBox.getSelectedItem();
                          handleSelection(dataType);
                      } else {
                          // Si la clave es incorrecta
                          JOptionPane.showMessageDialog(MenuPrincipalGUI.this, "Clave incorrecta. Inténtelo nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                      }
                  }else {
                   JOptionPane.showMessageDialog(MenuPrincipalGUI.this,"Hasta pronto!!", "G.F.R.Y", JOptionPane.INFORMATION_MESSAGE);
                      }
            }
        });


        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/librooo.png")); //
        imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setOpaque(false); //fondo transpp

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.black); //el color del fondo
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
                fileManager = new FileSQliteManager();
                break;
            case "Php":
                fileManager = new FilePhpManager();
                break;
            case "MongoDB":
                fileManager = new FileMongoDBManager();
                break;
            case "ObjectDB":
                fileManager = new ObjectDBManager();
                break;
            case "BaseX":
                fileManager = new FileBaseXManager("Libros.xml");
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
