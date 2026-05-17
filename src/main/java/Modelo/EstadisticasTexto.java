package Modelo;

import java.io.*;

public class EstadisticasTexto {

    public String[] calcularEstadisticas(File archivo) {

        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                lineas++;
                caracteres += linea.length();

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