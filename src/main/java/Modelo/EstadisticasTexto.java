package Modelo;

import java.io.*;

/**
 * Clase EstadisticasTexto
 *
 * Esta clase permite analizar un archivo de texto y calcular estadísticas básicas.
 *
 * FUNCIONALIDADES:
 * - Contar número de líneas
 * - Contar número de palabras
 * - Contar número de caracteres
 *
 * MÉTODO DE CÁLCULO:
 * - Líneas: se incrementa por cada línea leída.
 * - Palabras: se separan usando espacios en blanco (expresión regular "\\s+").
 * - Caracteres: se cuenta la longitud de cada línea (sin incluir saltos de línea).
 *
 * NOTA:
 * - Las líneas vacías no cuentan palabras.
 * - Los saltos de línea no se incluyen en el conteo de caracteres.
 *
 * FORMATO DE SALIDA:
 * Devuelve un arreglo de Strings con:
 * [0] → número de líneas
 * [1] → número de palabras
 * [2] → número de caracteres
 *
 * @author
 */
public class EstadisticasTexto {

    /**
     * Calcula estadísticas básicas de un archivo de texto.
     *
     * @param archivo Archivo de texto a analizar
     * @return Arreglo de Strings con:
     *         - Líneas
     *         - Palabras
     *         - Caracteres
     */
    public String[] calcularEstadisticas(File archivo) {

        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            // Leer archivo línea por línea
            while ((linea = br.readLine()) != null) {

                // Contar líneas
                lineas++;

                // Contar caracteres (sin incluir salto de línea)
                caracteres += linea.length();

                // Contar palabras (si la línea no está vacía)
                if (!linea.trim().isEmpty()) {
                    palabras += linea.trim().split("\\s+").length;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }

        return new String[]{
                String.valueOf(lineas),
                String.valueOf(palabras),
                String.valueOf(caracteres)
        };
    }
}