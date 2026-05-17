package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase EncriptadorXOR
 *
 * Esta clase permite encriptar y desencriptar archivos utilizando
 * el algoritmo XOR (Exclusive OR).
 *
 * FUNCIONAMIENTO:
 * - Lee el archivo byte por byte.
 * - Aplica la operación XOR con una clave numérica.
 * - Escribe el resultado en un nuevo archivo.
 *
 * IMPORTANTE:
 * - El mismo método sirve para encriptar y desencriptar.
 * - Si aplicas XOR dos veces con la misma clave, recuperas el archivo original.
 *
 * PROPIEDAD DEL XOR:
 * (A ^ B) ^ B = A
 *
 * EJEMPLO:
 * - Byte original: 65 (A)
 * - Clave: 3
 * - Encriptado: 65 ^ 3 = 66
 * - Desencriptado: 66 ^ 3 = 65
 *
 * NOTA:
 * Este método es educativo y no es seguro para cifrado real.
 *
 * @author
 */
public class EncriptadorXOR {

    /**
     * Procesa un archivo aplicando cifrado XOR byte por byte.
     *
     * Puede utilizarse tanto para:
     * - Encriptar un archivo
     * - Desencriptar un archivo (usando la misma clave)
     *
     * @param origen Archivo de entrada (archivo original)
     * @param destino Archivo de salida (archivo encriptado o desencriptado)
     * @param clave Clave numérica utilizada para la operación XOR
     */
    public void procesarArchivo(File origen, File destino, int clave) {

        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {

            int c;

            // Leer byte por byte
            while ((c = in.read()) != -1) {

                // Aplicar operación XOR
                int encriptado = c ^ clave;

                // Escribir resultado
                out.write(encriptado);
            }

            System.out.println("Proceso terminado.");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
