package co.edu.udea.edatos.ejemplo.controller;

import co.edu.udea.edatos.ejemplo.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLOutput;

public class RegistrarClienteController {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtBornDate;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAdress;
    @FXML
    private TextField txtNumberPhone;

    public void addClient(){

        String name = txtName.getText();
        int id = Integer.parseInt(txtId.getText());
        String lastName = txtLastName.getText();
        String bornDate =txtBornDate.getText();
        String email = txtEmail.getText();
        String adress = txtAdress.getText();
        String numberPhone = txtNumberPhone.getText();

        Cliente cliente = new Cliente(id,name,lastName,bornDate,email,adress,numberPhone);

        System.out.println("Registramos Cliente con nombre "+ name +" y identificacion " + txtId.getText());

    }

}
