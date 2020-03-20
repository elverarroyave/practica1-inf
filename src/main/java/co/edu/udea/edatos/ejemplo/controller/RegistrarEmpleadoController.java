package co.edu.udea.edatos.ejemplo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistrarEmpleadoController {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtIdEmployee;
    @FXML
    private TextField txtPassWord;
    @FXML
    private TextField txtBornDate;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAdress;
    @FXML
    private TextField txtNumberPhone;

    public void addEmployee(){
        String name = txtName.getText();
        String id =txtId.getText();
        System.out.println("Registramos Cliente con nombre "+ name +" y identificacion " + txtId.getText());
    }

}
