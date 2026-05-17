package Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase LectorCSV
 *
 * Esta clase permite leer archivos en formato CSV (Comma-Separated Values)
 * y convertir su contenido en una estructura de datos manejable en Java.
 *
 * FUNCIONAMIENTO:
 * - Lee el archivo línea por línea.
 * - Divide cada línea en columnas usando la coma (,) como separador.
 * - Almacena cada fila como un arreglo de Strings.
 * - Devuelve una lista de filas.
 *
 * ESTRUCTURA DEL RESULTADO:
 * List<String[]> donde:
 * - Cada elemento representa una fila del archivo CSV.
 * - Cada String[] contiene las columnas de esa fila.
 *
 * NOTA:
 * Este lector es básico:
 * - No maneja comillas ("") ni comas dentro de campos.
 * - No valida formatos complejos de CSV.
 *
 * @author
 */
public class LectorCSV {

    /**
     * Lee un archivo CSV y devuelve su contenido en forma de lista de arreglos.
     *
     * @param archivo Archivo CSV a leer
     * @return Lista de filas, donde cada fila es un arreglo de Strings
     */
    public List<String[]> leerCSV(File archivo) {

        List<String[]> datos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            // Leer línea por línea
            while ((linea = br.readLine()) != null) {

                try {
                    // Separar columnas por coma
                    String[] fila = linea.split(",");

                    // Agregar fila a la lista
                    datos.add(fila);

                } catch (Exception e) {
                    System.out.println("Error en línea: " + linea);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer CSV");
            e.printStackTrace();
        }

        return datos;
    }
}