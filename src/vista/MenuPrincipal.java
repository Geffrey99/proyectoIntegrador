package vista;

import java.util.Scanner;

import modelo.*;
//MENU 1
public class MenuPrincipal {

    public String menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        String dataType = null;

        do {
            System.out.println("\n_____WELCOME MY PROGRAM!!!___ My name is Geff ");
            System.out.println("===================================================");
            System.out.println("*** Selecciona el tipo de datos que desea utilizar :\n");
            System.out.println("1. (.TXT)"); //oK TODO -> TRANSFORMAR
            System.out.println("2. (.XML)"); // OK  TODO -> TRANSFORMAR
            System.out.println("3. Binario"); //OK TODO -> TRANSFORMAR
            System.out.println("4. MySQL"); //OK TODO -> TRANSFORMAR A OTROS
            System.out.println("5. Hibernate"); //OK TODO -> TRANSFORMAR A OTROS
            System.out.println("6. SqLite"); // TODO -> TRANSFORMAR
            System.out.println("7. Php");
            System.out.println("8. Salir");

            try {
                option = Integer.parseInt(sc.nextLine());

                switch (option) {
                    case 1:
                        System.out.println("Ha seleccionado la opción «Fichero».");
                        System.out.println("====================================");
                        dataType = "Fichero";
                        break;
                    case 2:
                        System.out.println("Ha seleccionado la opción «XML».");
                        System.out.println("================================");
                        dataType = "XML";
                        break;
                    case 3:
                        System.out.println("Ha seleccionado la opción «Binario».");
                        System.out.println("====================================");
                        dataType = "Binario";
                        break;
                    case 4:
                        System.out.println("Ha seleccionado la opción «MySQL».");
                        System.out.println("====================================");
                        dataType= "MySql";
                        break;
                    case 5:
                        System.out.println("Ha seleccionado la opción «Hibernate».");
                        System.out.println("====================================");
                        dataType= "Hibernate";
                        break;

                    case 6:
                        System.out.println("Ha seleccionado la opción «SQLite».");
                        System.out.println("====================================");
                        dataType= "SqLite";
                        break;

                    case 7:
                        System.out.println("Ha seleccionado la opción «PHP».");
                        System.out.println("====================================");
                        dataType= "Php";
                        break;

                    case 8:
                        System.out.println("Está abandonando el programa...");

                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, vuelva a intentarlo.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida_Por favor! introduzca un número.");
            }
        } while (option != 8 && dataType == null);

        return dataType;
    }
}
