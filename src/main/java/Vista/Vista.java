package Vista;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

public class Vista {

    public VBox contenedorContenido;
    public VBox vistaEditorNotas;
    public Button btnEditor;
    public Button btnEstadisticas;
    public Button btnCSV;
    public TextArea areaTextoEditor;
    public Button btnAbrirArchivoEditor;
    public Button btnGuardarArchivoEditor;
    public Button btnCargarTxtEstadisticas;
    public TableView<String[]> tablaEstadisticas;
    public Button btnCargarCSV;
    public TableView<String[]> tablaCSV;
    public VBox vistaCSV;

    public void iniciarVistaPrincipal(Stage stage) {

        Label titulo = new Label("Selecciona un ejercicio");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        btnEditor = new Button("Editor de Notas");
        btnEstadisticas = new Button("Estadísticas de Texto");
        btnCSV = new Button("Visualizador CSV");

        // 🔹 Estilo botones
        btnEditor.setPrefWidth(250);
        btnEstadisticas.setPrefWidth(250);
        btnCSV.setPrefWidth(250);

        VBox menu = new VBox(15, btnEditor, btnEstadisticas, btnCSV);
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

    public VBox crearVistaEditorNotas() {

        if (vistaEditorNotas != null) {
            return vistaEditorNotas;
        }
        areaTextoEditor = new TextArea();
        btnAbrirArchivoEditor = new Button("Abrir");
        btnGuardarArchivoEditor = new Button("Guardar");
        HBox barra = new HBox(10, btnAbrirArchivoEditor, btnGuardarArchivoEditor);
        barra.setAlignment(javafx.geometry.Pos.CENTER);

        vistaEditorNotas = new VBox(10, barra, areaTextoEditor);

        return vistaEditorNotas;
    }

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
                    new javafx.beans.property.SimpleStringProperty(
                            celda.getValue()[indice]
                    )
            );
            tablaCSV.getColumns().add(columna);
        }
        tablaCSV.getItems().addAll(datos);
    }
    public VBox crearVistaCSV() {

        tablaCSV = new TableView<>();
        btnCargarCSV = new Button("Cargar CSV");
        VBox layout = new VBox(10, btnCargarCSV, tablaCSV);
        return layout;
    }
}