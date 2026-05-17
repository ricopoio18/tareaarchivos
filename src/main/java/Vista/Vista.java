package Vista;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

/**
 * Clase Vista
 *
 * Esta clase representa la capa de presentación en el patrón MVC.
 * Se encarga de construir y gestionar todos los elementos gráficos de la aplicación
 * utilizando JavaFX.
 *
 * Funcionalidades principales:
 * - Crear la ventana principal.
 * - Generar vistas para diferentes módulos:
 *   - Editor de notas
 *   - Estadísticas de texto
 *   - Visualizador CSV
 *   - Encriptador XOR
 *   - Detector de archivos
 * - Mostrar datos en tablas (CSV y estadísticas).
 *
 * NOTA:
 * Esta clase NO contiene lógica de negocio, solo define la interfaz gráfica.
 * La lógica se maneja en el Controlador.
 *
 * @author
 */
public class Vista {

    /** Contenedor donde se cargan dinámicamente las vistas */
    public VBox contenedorContenido;

    /** Vista del editor de notas */
    public VBox vistaEditorNotas;

    /** Botones del menú principal */
    public Button btnEditor;
    public Button btnEstadisticas;
    public Button btnCSV;
    public Button btnXOR;
    public Button btnDetector;

    /** Componentes del editor */
    public TextArea areaTextoEditor;
    public Button btnAbrirArchivoEditor;
    public Button btnGuardarArchivoEditor;

    /** Componentes de estadísticas */
    public Button btnCargarTxtEstadisticas;
    public TableView<String[]> tablaEstadisticas;

    /** Componentes de CSV */
    public Button btnCargarCSV;
    public TableView<String[]> tablaCSV;
    public VBox vistaCSV;

    /** Componentes de XOR */
    public Button btnSeleccionarArchivo;
    public Button btnGuardarArchivo;
    public TextField campoClave;

    /** Componentes del detector */
    public Button btnSeleccionarArchivo2;
    public Label lblResultado;
    public Label lblHex;

    /**
     * Inicializa la ventana principal de la aplicación.
     *
     * Contiene:
     * - Un título
     * - Menú con botones para seleccionar funcionalidades
     * - Contenedor dinámico para cargar vistas
     *
     * @param stage Ventana principal de JavaFX
     */
    public void iniciarVistaPrincipal(Stage stage) {

        Label titulo = new Label("Selecciona un ejercicio");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        btnEditor = new Button("Editor de Notas");
        btnEstadisticas = new Button("Estadísticas de Texto");
        btnCSV = new Button("Visualizador CSV");
        btnXOR = new Button("Encriptador XOR");
        btnDetector = new Button("Detector de Archivos");

        btnEditor.setPrefWidth(250);
        btnEstadisticas.setPrefWidth(250);
        btnCSV.setPrefWidth(250);
        btnXOR.setPrefWidth(250);
        btnDetector.setPrefWidth(250);

        VBox menu = new VBox(15, btnEditor, btnEstadisticas, btnCSV, btnXOR, btnDetector);
        menu.setAlignment(Pos.CENTER);

        contenedorContenido = new VBox();
        contenedorContenido.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, titulo, menu, contenedorContenido);
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 700, 500);

        stage.setTitle("Aplicación de Archivos");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Crea la vista del editor de notas.
     *
     * Incluye:
     * - Área de texto para editar contenido
     * - Botones para abrir y guardar archivos
     *
     * @return VBox con la interfaz del editor
     */
    public VBox crearVistaEditorNotas() {

        if (vistaEditorNotas != null) {
            return vistaEditorNotas;
        }

        areaTextoEditor = new TextArea();
        btnAbrirArchivoEditor = new Button("Abrir");
        btnGuardarArchivoEditor = new Button("Guardar");

        HBox barra = new HBox(10, btnAbrirArchivoEditor, btnGuardarArchivoEditor);
        barra.setAlignment(Pos.CENTER);

        vistaEditorNotas = new VBox(10, barra, areaTextoEditor);

        return vistaEditorNotas;
    }

    /**
     * Crea la vista para mostrar estadísticas de texto.
     *
     * Incluye:
     * - Botón para cargar archivo TXT
     * - Tabla para mostrar:
     *   - Líneas
     *   - Palabras
     *   - Caracteres
     *
     * @return VBox con la interfaz de estadísticas
     */
    public VBox crearVistaEstadisticasTexto() {

        btnCargarTxtEstadisticas = new Button("Cargar TXT");
        tablaEstadisticas = new TableView<>();

        TableColumn<String[], String> colTipo = new TableColumn<>("Tipo");
        TableColumn<String[], String> colCantidad = new TableColumn<>("Cantidad");

        colTipo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[0]));

        colCantidad.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[1]));

        tablaEstadisticas.getColumns().addAll(colTipo, colCantidad);

        return new VBox(10, btnCargarTxtEstadisticas, tablaEstadisticas);
    }

    /**
     * Muestra los datos de un archivo CSV en la tabla.
     *
     * Crea dinámicamente las columnas según el número de campos del CSV.
     *
     * @param datos Lista de filas del CSV, donde cada fila es un arreglo de Strings
     */
    public void mostrarCSVEnTabla(List<String[]> datos) {

        tablaCSV.getItems().clear();
        tablaCSV.getColumns().clear();

        if (datos.size() == 0) return;

        int columnas = datos.get(0).length;

        for (int i = 0; i < columnas; i++) {

            final int indice = i;

            TableColumn<String[], String> columna =
                    new TableColumn<>("Col " + (i + 1));

            columna.setCellValueFactory(celda ->
                    new SimpleStringProperty(
                            celda.getValue()[indice]
                    )
            );

            tablaCSV.getColumns().add(columna);
        }

        tablaCSV.getItems().addAll(datos);
    }

    /**
     * Crea la vista para visualizar archivos CSV.
     *
     * Incluye:
     * - Botón para cargar CSV
     * - Tabla para mostrar los datos
     *
     * @return VBox con la interfaz CSV
     */
    public VBox crearVistaCSV() {

        tablaCSV = new TableView<>();
        btnCargarCSV = new Button("Cargar CSV");

        VBox layout = new VBox(10, btnCargarCSV, tablaCSV);

        return layout;
    }

    /**
     * Crea la vista del encriptador XOR.
     *
     * Incluye:
     * - Botón para seleccionar archivo
     * - Campo para ingresar clave numérica
     * - Botón para guardar resultado
     *
     * @return VBox con la interfaz XOR
     */
    public VBox crearVistaXOR() {

        btnSeleccionarArchivo = new Button("Seleccionar Archivo");
        btnGuardarArchivo = new Button("Guardar Resultado");

        campoClave = new TextField();
        campoClave.setPromptText("Clave (número)");

        return new VBox(10, btnSeleccionarArchivo, campoClave, btnGuardarArchivo);
    }

    /**
     * Crea la vista del detector de archivos.
     *
     * Permite:
     * - Seleccionar un archivo
     * - Mostrar sus primeros bytes en hexadecimal
     * - Mostrar el formato detectado
     *
     * @return VBox con la interfaz del detector
     */
    public VBox crearVistaDetector() {

        btnSeleccionarArchivo2 = new Button("Seleccionar Archivo");
        lblHex = new Label("Hex: ");
        lblResultado = new Label("Formato: ");

        return new VBox(15,
                btnSeleccionarArchivo,
                lblHex,
                lblResultado
        );
    }
}