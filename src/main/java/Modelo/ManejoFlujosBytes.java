package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase ManejoFlujosBytes
 *
 * Esta clase permite trabajar con archivos a nivel de bytes utilizando flujos
 * de entrada (FileInputStream).
 *
 * Funcionalidades principales:
 * - Leer bytes individuales de un archivo.
 * - Leer los primeros 8 bytes de un archivo.
 * - Convertir bytes a representación hexadecimal.
 * - Detectar el formato de un archivo basado en su firma (magic numbers).
 *
 * FORMATOS SOPORTADOS:
 * - PDF
 * - JPEG (JPG)
 * - PNG
 * - ZIP (incluye DOCX, XLSX, JAR)
 * - GIF
 *
 * NOTA:
 * La detección de formato se basa en los primeros bytes del archivo,
 * lo cual es una técnica común en análisis de archivos binarios.
 *
 * @author
 */
public class ManejoFlujosBytes {

    /** Archivo a procesar */
    File nombreArchivo;

    /**
     * Constructor de la clase.
     *
     * @param nombreArchivo Ruta del archivo a procesar
     */
    public ManejoFlujosBytes(String nombreArchivo){
        this.nombreArchivo = new File(nombreArchivo);
    }

    /**
     * Método para escribir un byte en el archivo.
     *
     * NOTA: Actualmente no está implementado.
     *
     * @param c Byte a escribir
     */
    public void escribirByte(int c){
        // Método pendiente de implementación
    }

    /**
     * Lee el primer byte del archivo.
     *
     * @return El valor del byte leído (0-255) o -1 si no se pudo leer
     */
    public int leerByte() {
        int unByte = -1;
        FileInputStream in = null;

        try {
            in = new FileInputStream(nombreArchivo);

            if ((unByte = in.read()) != -1) {
                System.out.println(unByte);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try{
                    in.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return unByte;
    }

    /**
     * Lee los primeros 8 bytes del archivo.
     *
     * @return Lista con los valores enteros de los bytes leídos
     */
    public ArrayList<Integer> leer8Bytes(){

        int unByte = -1;
        int contador = 0;
        ArrayList<Integer> ochoBytes = new ArrayList<>();
        FileInputStream in = null;

        try {
            in = new FileInputStream(nombreArchivo);

            while((unByte = in.read()) != -1 && (contador < 8)) {
                ochoBytes.add(unByte);
                System.out.println(unByte);
                contador++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try{
                    in.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return ochoBytes;
    }

    /**
     * Verifica el formato del archivo comparando los bytes leídos
     * con firmas conocidas (PDF y JPG).
     *
     * NOTA: Método limitado (solo revisa el primer byte).
     * Se recomienda usar detectarFormatoArchivo() para mayor precisión.
     *
     * @param ochoBytes Lista de bytes leídos
     * @return Tipo de archivo ("PDF", "JPG" o vacío si no coincide)
     */
    public String verificarFormato(ArrayList<Integer> ochoBytes) {

        String formato = "";

        // Firma PDF → 25 50 44 46 20
        ArrayList<Integer> formatoPDF = new ArrayList<>();
        formatoPDF.add(Integer.parseInt("25", 16));
        formatoPDF.add(Integer.parseInt("50", 16));
        formatoPDF.add(Integer.parseInt("44", 16));
        formatoPDF.add(Integer.parseInt("46", 16));
        formatoPDF.add(Integer.parseInt("20", 16));

        // Firma JPG → FF D8 FF E0 / FF D8 FF E1
        ArrayList<Integer> formatoJPG = new ArrayList<>();
        formatoJPG.add(Integer.parseInt("FF", 16));
        formatoJPG.add(Integer.parseInt("D8", 16));
        formatoJPG.add(Integer.parseInt("FF", 16));
        formatoJPG.add(Integer.parseInt("E0", 16));
        formatoJPG.add(Integer.parseInt("E1", 16));

        int valor = ochoBytes.get(0);

        if (valor == formatoPDF.get(0)) formato = "PDF";
        if (valor == formatoJPG.get(0)) formato = "JPG";

        return formato;
    }

    /**
     * Detecta el formato del archivo utilizando los primeros bytes (magic numbers).
     *
     * Comparaciones realizadas:
     * - PDF  → 25 50 44 46
     * - JPEG → FF D8 FF
     * - PNG  → 89 50 4E 47
     * - ZIP  → 50 4B 03 04
     * - GIF  → 47 49 46 38
     *
     * @return Nombre del formato detectado o "Formato desconocido"
     */
    public String detectarFormatoArchivo() {

        ArrayList<Integer> bytes = leer8Bytes();

        if (bytes.size() < 4) return "Desconocido";

        // PDF
        if (bytes.get(0) == 0x25 &&
                bytes.get(1) == 0x50 &&
                bytes.get(2) == 0x44 &&
                bytes.get(3) == 0x46) {
            return "PDF";
        }

        // JPEG
        if (bytes.get(0) == 0xFF &&
                bytes.get(1) == 0xD8 &&
                bytes.get(2) == 0xFF) {
            return "JPEG";
        }

        // PNG
        if (bytes.get(0) == 0x89 &&
                bytes.get(1) == 0x50 &&
                bytes.get(2) == 0x4E &&
                bytes.get(3) == 0x47) {
            return "PNG";
        }

        // ZIP
        if (bytes.get(0) == 0x50 &&
                bytes.get(1) == 0x4B &&
                bytes.get(2) == 0x03 &&
                bytes.get(3) == 0x04) {
            return "ZIP / DOCX / XLSX / JAR";
        }

        // GIF
        if (bytes.get(0) == 0x47 &&
                bytes.get(1) == 0x49 &&
                bytes.get(2) == 0x46 &&
                bytes.get(3) == 0x38) {
            return "GIF";
        }

        return "Formato desconocido";
    }

    /**
     * Convierte una lista de bytes a una cadena en formato hexadecimal.
     *
     * Ejemplo:
     * Entrada → [255, 216, 255]
     * Salida  → "FF D8 FF "
     *
     * @param bytes Lista de bytes
     * @return Cadena en formato hexadecimal
     */
    public String bytesAHex(ArrayList<Integer> bytes) {

        StringBuilder hex = new StringBuilder();

        for (int b : bytes) {
            hex.append(String.format("%02X ", b));
        }

        return hex.toString();
    }
}
