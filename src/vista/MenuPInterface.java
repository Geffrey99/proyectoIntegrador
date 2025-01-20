package vista;

import java.util.Scanner;

import modelo.*;
//MENU 1
import java.util.Scanner;

public class MenuPInterface {

    public String menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        String dataType = null;

        do {
            System.out.println("\n_____WELCOME MY PROGRAM!!!___ My name is Geff ");
            System.out.println("===================================================");
            System.out.println("*** Selecciona el tipo de datos que desea utilizar :\n");
            System.out.println("1. Fichero (TXT)");
            System.out.println("2. XML");
            System.out.println("3. Binario");
            System.out.println("4. MySQL");
            System.out.println("5. Hibernate");
            System.out.println("6. SqLite");
            System.out.println("7. Php");
            System.out.println("8. Salir");

            try {
                option = Integer.parseInt(sc.nextLine());

                switch (option) {
                    case 1:
                        System.out.println("Ha seleccionado la opción «Fichero».");
                        dataType = "Fichero";
                        break;
                    case 2:
                        System.out.println("Ha seleccionado la opción «XML».");
                        dataType = "XML";
                        break;
                    case 3:
                        System.out.println("Ha seleccionado la opción «Binario».");
                        dataType = "Binario";
                        break;
                    case 4:
                        System.out.println("Ha seleccionado la opción «MySQL».");
                        dataType = "MySql";
                        break;
                    case 5:
                        System.out.println("Ha seleccionado la opción «Hibernate».");
                        dataType = "Hibernate";
                        break;
                    case 6:
                        System.out.println("Ha seleccionado la opción «SQLite».");
                        dataType = "SqLite";
                        break;
                    case 7:
                        System.out.println("Ha seleccionado la opción «PHP».");
                        dataType = "Php";
                        break;
                    case 8:
                        System.out.println("Está abandonando el programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, vuelva a intentarlo.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduzca un número.");
            }
        } while (option != 8 && dataType == null);

        return dataType;
    }
}
