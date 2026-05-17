package Modelo;

import java.io.*;

public class EditorNotas {

    // Método para leer un archivo
    public String leerArchivo(File archivo) {
        StringBuilder contenido = new StringBuilder();

        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);

            String linea;

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

    // Método para guardar texto en un archivo
    public void guardarArchivo(File archivo, String texto) {
        try {
            FileWriter fw = new FileWriter(archivo);
            fw.write(texto);
            fw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar el archivo");
            e.printStackTrace();
        }
    }
}
