package co.edu.udea.edatos.ejemplo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/principal-panel.fxml")); // Carga el archivo FXML en la direccion que le estamos dando
        stage.setTitle("Practica 1"); // Damos titulo a la ventana
        stage.setScene(new Scene(root, 680, 600)); //Definimos el tamaño de la ventana
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
