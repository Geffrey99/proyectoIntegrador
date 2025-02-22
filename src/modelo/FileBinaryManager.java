package modelo;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


	public class FileBinaryManager implements AManagerInterface, Serializable {
		private static final long serialVersionUID = 1L;
		File binario = new File("Ficheros/libros.bin");
		HashMap<String, Libro> libros= new HashMap<>();

    public HashMap<String, Libro> recorrer() {
        HashMap<String, Libro> librosLeidos = new HashMap<>();
        
        if (binario.length()==0) {
        	System.out.println("El archivo esta vacio, no contiene libros");
        	return librosLeidos;
        }
        
        try (FileInputStream fis = new FileInputStream(binario);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Libro libro = (Libro) ois.readObject();
                    String clave = libro.getId();
                    librosLeidos.put(clave, libro);
                } catch (EOFException e) {
                    // Fin del archivo alcanzado
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        }
        return librosLeidos;
    }


	//sobreescribir el fichero binario
			/*public void guardarLibros(HashMap<String, Libro> libros) {
				try {
			        // Abrimos el archivo en modo de escritura, sobrescribe el archivo existente
			        FileOutputStream fos = new FileOutputStream(binario);
			        ObjectOutputStream oos = new ObjectOutputStream(fos);

			        // Iteramos sobre el HashMap y escribimos cada libro en el archivo
			        for (Map.Entry<String, Libro> entry : libros.entrySet()) {
			            oos.writeObject(entry.getValue());  // Escribimos el objeto 'Libro'
			        }

			        // Cerramos el ObjectOutputStream para asegurarnos de que todo se ha escrito
			        oos.close();

			    } catch (IOException e) {
			        System.out.println("Error al sobrescribir el archivo: " + e.getMessage());
			        e.printStackTrace();
			    }
			}
			*/
			
			public void guardarLibros(HashMap<String, Libro> libros) {
				try (FileOutputStream fos = new FileOutputStream(binario);
						ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					for (Map.Entry<String, Libro> entry : libros.entrySet()) {
						oos.writeObject(entry.getValue()); // Escribimos el objeto 'Libro' 
						}
					}
				catch (IOException e) {
					System.out.println("Error al sobrescribir el archivo: " + e.getMessage()); e.printStackTrace(); 
					} 
				}




		public Libro buscarUno(String impreso) {
			    System.out.println("Buscando el libro con ID: " + impreso);
			    HashMap<String, Libro> libros = recorrer();
			    System.out.println("Libros leídos: " + libros.size());
			    if (libros.containsKey(impreso)) {
			        System.out.println("Libro encontrado: " + libros.get(impreso).getTitulo());
			        return libros.get(impreso);
			    } else {
			        System.out.println("El libro con ID " + impreso + " no se encontró.");
			        return null;
			    }
			}


			//terminado buscar uno ! 
			
			public HashMap<String, Libro> mostrarTodos() {
				HashMap <String, Libro> libros=recorrer();
				return libros;
			}
			//terminado
			
			public Libro insertarUno(Libro libro) {
				HashMap <String, Libro> libros=recorrer();
				boolean todoOk=false;
				try {
					libros.put(libro.getId(), libro);
					guardarLibros(libros);
					todoOk=true;
				}catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				return libro;
			}
			
			
			public Libro modificarUno(Libro libroEditado) {
				HashMap <String, Libro> libros=recorrer();
				boolean todoOk=false;
				try {
					libros.put(libroEditado.getId(), libroEditado);
					guardarLibros(libros);
					todoOk=true;
				}catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				return libroEditado;
			}

			
			public void borrarUno(String libroBorrado) {
				HashMap <String, Libro> libros=recorrer();
				boolean todoOk=false;
				try {
					if(libros.containsKey(libroBorrado)) {
						libros.remove(libroBorrado);
						guardarLibros(libros);
						todoOk=true;
					}
				}catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				System.out.println("Ok eliminado");

				// TODO Auto-generated method stub

			}

		//		@Override
//		public void hacerCopia(String tipoFichero) {
//
//		}
	}