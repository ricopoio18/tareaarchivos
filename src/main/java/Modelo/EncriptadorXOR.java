package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EncriptadorXOR {

    public void procesarArchivo(File origen, File destino, int clave) {

        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {
            int c;
            while ((c = in.read()) != -1) {

                int encriptado = c ^ clave;
                out.write(encriptado);
            }
            System.out.println("Proceso terminado.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
