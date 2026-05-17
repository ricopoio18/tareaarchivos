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
}
