package Modelo;

import java.io.*;

/**
 * Clase EditorNotas
 *
 * Esta clase permite realizar operaciones básicas de manejo de archivos de texto.
 *
 * FUNCIONALIDADES:
 * - Leer el contenido de un archivo de texto.
 * - Guardar texto en un archivo.
 *
 * USO:
 * Es utilizada por el controlador para interactuar con archivos desde la interfaz gráfica.
 *
 * NOTA:
 * - Utiliza FileReader y BufferedReader para lectura.
 * - Utiliza FileWriter para escritura.
 * - No maneja codificaciones específicas (usa la del sistema por defecto).
 *
 * @author
 */
public class EditorNotas {

    /**
     * Lee el contenido completo de un archivo de texto.
     *
     * El archivo se procesa línea por línea y se reconstruye el contenido
     * agregando saltos de línea.
     *
     * @param archivo Archivo de texto a leer
     * @return Contenido completo del archivo como String
     */
    public String leerArchivo(File archivo) {

        StringBuilder contenido = new StringBuilder();

        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);

            String linea;

            // Leer línea por línea
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }

        return contenido.toString();
    }

    /**
     * Guarda texto en un archivo.
     *
     * Si el archivo ya existe, se sobrescribe completamente.
     * Si no existe, se crea automáticamente.
     *
     * @param archivo Archivo donde se guardará el texto
     * @param texto Contenido a escribir en el archivo
     */
    public void guardarArchivo(File archivo, String texto) {

        try {
            FileWriter fw = new FileWriter(archivo);

            // Escribir contenido
            fw.write(texto);

            fw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar el archivo");
            e.printStackTrace();
        }
    }
}
