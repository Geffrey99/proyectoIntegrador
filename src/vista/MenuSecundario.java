package vista;


import java.util.Scanner;

import modelo.AManagerInterface;

import modelo.*;


public class MenuSecundario {
    Scanner sc=new Scanner(System.in);
    private int cambioArchivo;
    public void menuSecundario(String dataType, AManagerInterface fileManager, MenuPrincipal menuPrincipal) {
        Scanner sc = new Scanner(System.in);
        int option = 0;

        while (option != 6) {
            System.out.println("\n*** Menú CRUD (" + dataType + ") ***");
            System.out.println("SELECCIONE LA OPCIÓN QUE DESEA REALIZAR:");
            System.out.println("========================================");
            System.out.println("\n1. Mostrar todos los registros");
            System.out.println("2. Insertar un registro");
            System.out.println("3. Modificar un registro");
            System.out.println("4. Borrar un registro");
            System.out.println("5. Buscar un registro");
            //  System.out.println("6. Trasladar a otro tipo de archivo"); //TODO
            System.out.println("7. Volver al menú principal");

            try {
                option = Integer.parseInt(sc.nextLine());

                switch (option) {
                    case 1:
                        System.out.println(fileManager.mostrarTodos());
                        break;
                    case 2:
                        System.out.println("Insertar un nuevo libro.");
                        Libro nuevoLibro = crearLibroDesdeInput();
                        fileManager.insertarUno(nuevoLibro);
                        break;
                    case 3:
                        System.out.println("Modificar un libro.");
                        Libro modificarLibro = crearLibroDesdeInput();
                        fileManager.modificarUno(modificarLibro);
                        break;
                    case 4:
                        System.out.println("Borrar un libro.");
                        System.out.println("Introduzca el ID del libro que desea borrar:");
                        String idBorrar = sc.nextLine();
                        fileManager.borrarUno(idBorrar);
                        break;
                    case 5:
                        System.out.println("Buscar un libro.");
                        System.out.println("Introduzca el ID del libro que desea buscar:");
                        String idBuscar = sc.nextLine();
                        fileManager.buscarUno(idBuscar);
                        break;
                   /*TODO case 6:
                    	System.out.println("Cambiar a oto archivo");
                    //	AManagerInterface miObjeto1=null;
                   	AManagerAbstr ObjectoAcambiar=null;
                    	int cambioArchivo=menuTer(option);
                    	switch(cambioArchivo) {
                    	case 1:
                    		ObjectoAcambiar=new TextManager2();
                    		ObjectoAcambiar.guardarLibros(ObjectoAcambiar.recorrer());
    						break;
    					case 2:
    						ObjectoAcambiar=new FileBinaryManager();
    						ObjectoAcambiar.guardarLibros(ObjectoAcambiar.recorrer());
    						break;
    					case 3:
    						ObjectoAcambiar=new FileXMLManager2();
    						ObjectoAcambiar.guardarLibros(ObjectoAcambiar.recorrer());
    						break;
    					case 4:
    						//ObjectoAcambiar=new TextManager();
    						ObjectoAcambiar.guardarLibros(ObjectoAcambiar.recorrer());

    						break;
    					case 5:
    						//ObjectoAcambiar=new TextManager();
    						ObjectoAcambiar.guardarLibros(ObjectoAcambiar.recorrer());
    						break;
    					case 6:
    					//	ObjectoAcambiar=new TextManager();
    						ObjectoAcambiar.guardarLibros(ObjectoAcambiar.recorrer());

    						break;
    					}
    					Thread.sleep(1500);
                    	break;
                    	*/

                    case 7:
                        // System.out.println("Volviendo al menú principal...\n");
                        // Invocamos el menú principal de nuevo  ?? o volvemos al inicio ??
                        controlador.Main.main(null);
                        //  menuPrincipal.menuPrincipal();  // Volvemos al Menu1 --> se rompe el programa al volver
                        return;  // Termina el ciclo para salir de este menú
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida.");
            }
        }
    }

    // Método auxiliar para crear un libro a partir de la entrada del usuario
    private Libro crearLibroDesdeInput() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca el ID del libro:");
        String id = sc.nextLine();

        System.out.println("Introduzca el título del libro:");
        String titulo = sc.nextLine();

        System.out.println("Introduzca el autor del libro:");
        String autor = sc.nextLine();

        System.out.println("Introduzca el ISBN del libro:");
        String isbn = sc.nextLine();

        System.out.println("Introduzca el año de publicación del libro:");
        int anno = sc.nextInt();
        sc.nextLine();

        return new Libro(id, titulo, autor, isbn, anno);
    }

    private Libro modificarLibroDesdeInput() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca el ID del libro:");
        String id = sc.nextLine();

        System.out.println("Introduzca el título del libro:");
        String titulo = sc.nextLine();

        System.out.println("Introduzca el autor del libro:");
        String autor = sc.nextLine();

        System.out.println("Introduzca el ISBN del libro:");
        String isbn = sc.nextLine();

        System.out.println("Introduzca el año de publicación del libro:");
        int anno = sc.nextInt();
        sc.nextLine();

        return new Libro(id, titulo, autor, isbn, anno);
    }

    public int menuTer(int option) {
        System.out.println("Introduce el tipo de archivo al que quieres convertir: \n1.A texto\n2.A binario\n3.A XML\n4.Base de datos\n5.Hibernate\n6.SQLite");
        cambioArchivo=sc.nextInt();
        return cambioArchivo;
    }
}