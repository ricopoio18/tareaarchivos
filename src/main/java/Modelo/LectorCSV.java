package Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    public List<String[]> leerCSV(File archivo) {

        List<String[]> datos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    String[] fila = linea.split(",");
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
