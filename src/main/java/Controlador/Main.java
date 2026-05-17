package Controlador;

import Modelo.EditorNotas;
import Modelo.EstadisticasTexto;
import Modelo.LectorCSV;
import Vista.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Vista vista = new Vista();
        vista.iniciarVistaPrincipal(stage);

        EditorNotas modeloEditorNotas = new EditorNotas();
        EstadisticasTexto modeloEstadisticasTexto = new EstadisticasTexto();
        LectorCSV modeloLectorCSV = new LectorCSV();

        new Controlador(vista, modeloEditorNotas, modeloEstadisticasTexto,modeloLectorCSV );
    }

    public static void main(String[] args) {
        launch(args);
    }
}