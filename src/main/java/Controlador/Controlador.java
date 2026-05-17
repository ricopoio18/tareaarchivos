package Controlador;

import Vista.Vista;
import Modelo.*;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import java.io.File;

public class Controlador {

    private Vista vista;
    private EditorNotas modeloEditor;
    private EstadisticasTexto modeloEstadisticas;
    private LectorCSV modeloCSV;
    private boolean csvEventosAsignados = false;

    private File archivoActual;

    public Controlador(Vista vista, EditorNotas editor, EstadisticasTexto estadisticas, LectorCSV modeloCSV) {
        this.vista = vista;
        this.modeloEditor = editor;
        this.modeloEstadisticas = estadisticas;
        this.modeloCSV = new LectorCSV();

        inicializarSelectorEjercicios();
    }

    private void inicializarSelectorEjercicios() {

        vista.btnEditor.setOnAction(e -> {
            vista.contenedorContenido.getChildren().clear();
            vista.contenedorContenido.getChildren().add(
                    vista.crearVistaEditorNotas()
            );
            configurarEventoAbrirArchivoEditor();
            configurarEventoGuardarArchivoEditor();
        });

        vista.btnEstadisticas.setOnAction(e -> {
            vista.contenedorContenido.getChildren().clear();
            vista.contenedorContenido.getChildren().add(
                    vista.crearVistaEstadisticasTexto()
            );
            configurarEventoCargarTxtEstadisticas();
        });

        vista.btnCSV.setOnAction(e -> {

            vista.contenedorContenido.getChildren().clear();
            vista.contenedorContenido.getChildren().add(
                    vista.crearVistaCSV()
            );
            inicializarEventosCSV();
        });
    }

    private void configurarEventoAbrirArchivoEditor() {
        FileChooser fileChooser = new FileChooser();

        vista.btnAbrirArchivoEditor.setOnAction(e -> {
            File archivo = fileChooser.showOpenDialog(null);

            if (archivo != null) {
                archivoActual = archivo;

                String contenido = modeloEditor.leerArchivo(archivo);
                vista.areaTextoEditor.setText(contenido);
            }
        });
    }

    private void configurarEventoGuardarArchivoEditor() {
        FileChooser fileChooser = new FileChooser();

        vista.btnGuardarArchivoEditor.setOnAction(e -> {

            if (archivoActual != null) {
                modeloEditor.guardarArchivo(archivoActual,
                        vista.areaTextoEditor.getText());
            } else {
                File archivo = fileChooser.showSaveDialog(null);

                if (archivo != null) {
                    archivoActual = archivo;
                    modeloEditor.guardarArchivo(archivo,
                            vista.areaTextoEditor.getText());
                }
            }
        });
    }

    private void configurarEventoCargarTxtEstadisticas() {

        FileChooser fileChooser = new FileChooser();

        vista.btnCargarTxtEstadisticas.setOnAction(e -> {

            File archivo = fileChooser.showOpenDialog(null);

            if (archivo != null) {

                String[] resultados =
                        modeloEstadisticas.calcularEstadisticas(archivo);

                vista.tablaEstadisticas.getItems().clear();

                vista.tablaEstadisticas.getItems().addAll(
                        new String[]{"Líneas", resultados[0]},
                        new String[]{"Palabras", resultados[1]},
                        new String[]{"Caracteres", resultados[2]}
                );
            }
        });
    }

    private void configurarEventoCargarCSV() {

        FileChooser fc = new FileChooser();

        vista.btnCargarCSV.setOnAction(e -> {

            File archivo = fc.showOpenDialog(null);

            if (archivo != null) {

                java.util.List<String[]> datos =
                        modeloCSV.leerCSV(archivo);

                vista.mostrarCSVEnTabla(datos);
            }
        });
    }
    private void inicializarEventosCSV() {
        if (csvEventosAsignados) return;
        configurarEventoCargarCSV();
        csvEventosAsignados = true;
    }

}