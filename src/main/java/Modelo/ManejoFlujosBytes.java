package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ManejoFlujosBytes {

    File nombreArchivo;
    public ManejoFlujosBytes(String nombreArchivo){
        this.nombreArchivo = new File(nombreArchivo);
    }

    public void escribirByte(int c){

    }

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
        }
        finally {
            if(in !=null){
                try{
                    in.close();
                }catch (IOException e){
                e.printStackTrace();}
            }
        }

             return unByte;
    }

    public ArrayList<Integer> leer8Bytes(){
        int unByte = -1;
        int contador = 0;
        ArrayList<Integer> ochoBytes = new ArrayList<>();
        FileInputStream in = null;
        try {
            in = new FileInputStream(nombreArchivo);
            while((unByte = in.read()) != -1 && (contador <8)) {
                ochoBytes.add(unByte);
                System.out.println(unByte);
                contador++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(in !=null){
                try{
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();}
            }
        }
        return ochoBytes;
    }

    public String verificarFormato(ArrayList<Integer> ochoBytes) {
        String formato = "";
        // 25 50 44 46 20
        ArrayList<Integer> formatoPDF = new ArrayList<>();
        formatoPDF.add(Integer.parseInt("25", 16));
        formatoPDF.add(Integer.parseInt("50", 16));
        formatoPDF.add(Integer.parseInt("44", 16));
        formatoPDF.add(Integer.parseInt("46", 16));
        formatoPDF.add(Integer.parseInt("20", 16));

        // FF D8 FF E0 o FF D8 FF E1
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

    public String detectarFormatoArchivo() {

        ArrayList<Integer> bytes = leer8Bytes();

        if (bytes.size() < 4) return "Desconocido";

        // PDF → 25 50 44 46
        if (bytes.get(0) == 0x25 &&
                bytes.get(1) == 0x50 &&
                bytes.get(2) == 0x44 &&
                bytes.get(3) == 0x46) {
            return "PDF";
        }

        // JPEG → FF D8 FF
        if (bytes.get(0) == 0xFF &&
                bytes.get(1) == 0xD8 &&
                bytes.get(2) == 0xFF) {
            return "JPEG";
        }

        // PNG → 89 50 4E 47
        if (bytes.get(0) == 0x89 &&
                bytes.get(1) == 0x50 &&
                bytes.get(2) == 0x4E &&
                bytes.get(3) == 0x47) {
            return "PNG";
        }

        // ZIP → 50 4B 03 04
        if (bytes.get(0) == 0x50 &&
                bytes.get(1) == 0x4B &&
                bytes.get(2) == 0x03 &&
                bytes.get(3) == 0x04) {
            return "ZIP / DOCX / XLSX / JAR";
        }

        // GIF → 47 49 46 38
        if (bytes.get(0) == 0x47 &&
                bytes.get(1) == 0x49 &&
                bytes.get(2) == 0x46 &&
                bytes.get(3) == 0x38) {
            return "GIF";
        }

        return "Formato desconocido";
    }

    public String bytesAHex(ArrayList<Integer> bytes) {

        StringBuilder hex = new StringBuilder();

        for (int b : bytes) {
            hex.append(String.format("%02X ", b));
        }

        return hex.toString();
    }
}
