package co.edu.udea.edatos.ejemplo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ContenedorPrincipalController {

    @FXML
    private BorderPane contenedorPrincipal;


    public void mnuSalir(){ System.exit(0);}

    public void mnuAddEmployee(){
        try{
            AnchorPane addEmployee = FXMLLoader.load(getClass().getClassLoader().getResource( "view/userManager/employee/add-employee.fxml"));
            contenedorPrincipal.setCenter(addEmployee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
