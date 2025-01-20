package controlador;

import vista.*;
import modelo.*;
import utils.HibernateUtil;


import java.io.File;

import org.hibernate.Session;

public class Main {

    public static void main(String[] args) {
        MenuPrincipal menu1 = new MenuPrincipal();
        String dataType = menu1.menuPrincipal();

        if (dataType == null) {
            //System.out.println("No se seleccionó un tipo de datos válido. Saliendo del programa...");

            System.out.println("--CLOSED--");
            return;

        }
        AManagerInterface fileManager = null;
        File inputFile=null;

        switch (dataType) {
            case "Fichero":
                fileManager = new FileTextManager("Libros.txt");
                break;
            case "XML":
                fileManager = new FileXMLManager("Libros.xml");
                break;
            case "Binario":
                fileManager = new FileBinaryManager();
                inputFile = new File("Ficheros/libros.bin");
                break;
            case "MySql":
                fileManager = new FileMysqlManager();
                //	BadatManager = new BadatManager("Libro.mysql");
                break;
            case "Hibernate":
                HibernateUtil util = new HibernateUtil();
                Session session = util.getSessionFactory().openSession();
                fileManager = new HibernateManager(session);
                break;
            case "SqLite":
                System.out.println("En proceso...**");
                break;
            case "Php":
                fileManager = new FilePhpManager();
                break;
            default:
                System.out.println("Saliendo del programa...");
                return;
        }

        MenuSecundario menu2 = new MenuSecundario();
        menu2.menuSecundario(dataType, fileManager, menu1);
    }
}