package co.edu.udea.edatos.ejemplo.controller;

import co.edu.udea.edatos.ejemplo.bsn.ClienteBsn;
import co.edu.udea.edatos.ejemplo.model.Cliente;

import javafx.fxml.FXML;
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
    }

}
