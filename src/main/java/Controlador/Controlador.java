package Controlador;

import Vista.Vista;
import Modelo.*;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * Clase Controlador
 *
 * Esta clase actúa como intermediario entre la Vista y los Modelos en una aplicación
 * basada en el patrón MVC (Modelo-Vista-Controlador).
 *
 * Se encarga de:
 * - Gestionar eventos de la interfaz gráfica (botones).
 * - Coordinar la interacción entre la vista y los distintos modelos.
 * - Controlar la carga de diferentes funcionalidades (editor, estadísticas, CSV, XOR, detector).
 *
 * Modelos utilizados:
 * - EditorNotas: manejo de archivos de texto (leer/guardar).
 * - EstadisticasTexto: cálculo de estadísticas de texto.
 * - LectorCSV: lectura y visualización de archivos CSV.
 * - EncriptadorXOR: encriptación/desencriptación de archivos.
 * - ManejoFlujosBytes: análisis de archivos binarios.
 *
 * Vista:
 * - Proporciona los elementos gráficos y métodos para generar las vistas.
 *
 * @author
 */
public class Controlador {

    /** Referencia a la vista principal */
    private Vista vista;

    /** Modelo para edición de notas */
    private EditorNotas modeloEditor;

    /** Modelo para estadísticas de texto */
    private EstadisticasTexto modeloEstadisticas;

    /** Modelo para lectura de archivos CSV */
    private LectorCSV modeloCSV;

    /** Bandera para evitar asignar eventos CSV múltiples veces */
    private boolean csvEventosAsignados = false;

    /** Archivo actualmente abierto en el editor */
    private File archivoActual;

    /**
     * Constructor de la clase Controlador.
     * Inicializa los modelos y configura los eventos principales de la vista.
     *
     * @param vista Vista principal de la aplicación
     * @param editor Modelo de edición de notas
     * @param estadisticas Modelo de estadísticas de texto
     * @param modeloCSV Modelo de lectura CSV
     */
    public Controlador(Vista vista, EditorNotas editor,
                       EstadisticasTexto estadisticas,
                       LectorCSV modeloCSV) {

        this.vista = vista;
        this.modeloEditor = editor;
        this.modeloEstadisticas = estadisticas;
        this.modeloCSV = new LectorCSV();

        inicializarSelectorEjercicios();
    }

    /**
     * Inicializa los botones principales de la interfaz para cambiar entre vistas.
     */
    private void inicializarSelectorEjercicios() {
        // Editor de notas
        vista.btnEditor.setOnAction(e -> {
            vista.contenedorContenido.getChildren().clear();
            vista.contenedorContenido.getChildren().add(
                    vista.crearVistaEditorNotas()
            );
            configurarEventoAbrirArchivoEditor();
            configurarEventoGuardarArchivoEditor();
        });

        // Estadísticas de texto
        vista.btnEstadisticas.setOnAction(e -> {
            vista.contenedorContenido.getChildren().clear();
            vista.contenedorContenido.getChildren().add(
                    vista.crearVistaEstadisticasTexto()
            );
            configurarEventoCargarTxtEstadisticas();
        });

        // CSV
        vista.btnCSV.setOnAction(e -> {
            vista.contenedorContenido.getChildren().clear();
            vista.contenedorContenido.getChildren().add(
                    vista.crearVistaCSV()
            );
            inicializarEventosCSV();
        });

        // XOR
        vista.btnXOR.setOnAction(e -> {
            vista.contenedorContenido.getChildren().clear();
            vista.contenedorContenido.getChildren().add(
                    vista.crearVistaXOR()
            );
            inicializarEventosXOR();
        });

        // Detector de archivos
        vista.btnDetector.setOnAction(e -> {
            vista.contenedorContenido.getChildren().clear();
            vista.contenedorContenido.getChildren().add(
                    vista.crearVistaDetector()
            );
            inicializarEventosDetector();
        });
    }

    /**
     * Configura el evento para abrir un archivo en el editor.
     * Permite seleccionar un archivo y cargar su contenido en el área de texto.
     */
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

    /**
     * Configura el evento para guardar un archivo desde el editor.
     * Guarda en el archivo actual o permite elegir uno nuevo.
     */
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

    /**
     * Configura el evento para cargar un archivo de texto y calcular estadísticas.
     * Muestra líneas, palabras y caracteres en una tabla.
     */
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

    /**
     * Configura el evento para cargar un archivo CSV y mostrarlo en la tabla.
     */
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

    /**
     * Inicializa eventos del módulo CSV evitando duplicación.
     */
    private void inicializarEventosCSV() {
        if (csvEventosAsignados) return;
        configurarEventoCargarCSV();
        csvEventosAsignados = true;
    }

    /** Archivo origen para el proceso XOR */
    private File archivoOrigen;

    /**
     * Inicializa los eventos para encriptación/desencriptación XOR.
     * Permite seleccionar archivo, ingresar clave y guardar resultado.
     */
    private void inicializarEventosXOR() {

        FileChooser fc = new FileChooser();
        EncriptadorXOR modelo = new EncriptadorXOR();

        vista.btnSeleccionarArchivo.setOnAction(e -> {
            archivoOrigen = fc.showOpenDialog(null);
        });

        vista.btnGuardarArchivo.setOnAction(e -> {
            if (archivoOrigen != null) {
                File destino = fc.showSaveDialog(null);
                if (destino != null) {
                    try {
                        int clave = Integer.parseInt(vista.campoClave.getText());
                        modelo.procesarArchivo(archivoOrigen, destino, clave);
                    } catch (NumberFormatException ex) {
                        System.out.println("Clave inválida");
                    }
                }
            }
        });
    }

    /**
     * Inicializa los eventos del detector de formato de archivos.
     * Lee los primeros bytes, los convierte a hexadecimal y detecta el tipo de archivo.
     */
    private void inicializarEventosDetector() {
        FileChooser fc = new FileChooser();

        vista.btnSeleccionarArchivo.setOnAction(e -> {
            File archivo = fc.showOpenDialog(null);
            if (archivo != null) {

                ManejoFlujosBytes modelo =
                        new ManejoFlujosBytes(archivo.getAbsolutePath());

                var bytes = modelo.leer8Bytes();

                String hex = modelo.bytesAHex(bytes);
                String formato = modelo.detectarFormatoArchivo();

                vista.lblHex.setText("Hex: " + hex);
                vista.lblResultado.setText("Formato: " + formato);
            }
        });
    }
}