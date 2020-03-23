package co.edu.udea.edatos.ejemplo.controller.user;

import co.edu.udea.edatos.ejemplo.bsn.ClienteBsn;
import co.edu.udea.edatos.ejemplo.model.Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class ClienteController {

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

    ClienteBsn clienteBsn = new ClienteBsn();

    public void create(){
        String name = txtName.getText();
        int id = Integer.parseInt(txtId.getText());
        String lastName = txtLastName.getText();
        String bornDate =txtBornDate.getText();
        String email = txtEmail.getText();
        String adress = txtAdress.getText();
        String numberPhone = txtNumberPhone.getText();
        Cliente cliente = new Cliente(id,name,lastName,bornDate,email,adress,numberPhone);
        clienteBsn.registrarCliente(cliente);

        confirm();
    }

    private void vaciarCampos(){
        txtId.clear();
        txtName.clear();
        txtLastName.clear();
        txtBornDate.clear();
        txtEmail.clear();
        txtAdress.clear();
        txtNumberPhone.clear();

    }

    private void confirm(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operacion");
        alert.setHeaderText("Guardar usuario");
        alert.setContentText("Usuario almacenado correctamente");
        alert.showAndWait();

        vaciarCampos();
    }



}
