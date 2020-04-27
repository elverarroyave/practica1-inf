package co.edu.udea.edatos.ejemplo.controller.user;

import co.edu.udea.edatos.ejemplo.bsn.ClienteBsn;
import co.edu.udea.edatos.ejemplo.model.Cliente;

import co.edu.udea.edatos.ejemplo.model.Component;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class ClienteController {
    @FXML
    private TableView<Cliente> tblCliente;
    @FXML
    private TableColumn<Cliente, String> clmId;
    @FXML
    private TableColumn<Cliente, String> clmName;
    @FXML
    private TableColumn<Cliente, String> clmNumberPhone;

    @FXML
    private AnchorPane editClient;
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

    public void listar() {
        List<Cliente> clientes = clienteBsn.getAll();
        ObservableList<Cliente> componentsObservables = FXCollections.observableList(clientes);
        ///se asigna la lista observable a la tabla
        tblCliente.setItems(componentsObservables);
        //se especifican los métodos que deben invocarse para obtener los valores a mostrar en las celdas
        clmId.setCellValueFactory(cellData->new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
        clmName.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getName()));
        clmNumberPhone.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getNumberPhone()));
    }

    public void create(){
        String name = txtName.getText();
        int id = Integer.parseInt(txtId.getText());
        String lastName = txtLastName.getText();
        String bornDate =txtBornDate.getText();
        String email = txtEmail.getText();
        String adress = txtAdress.getText();
        String numberPhone = txtNumberPhone.getText();
        Cliente cliente = new Cliente(id,name,lastName,bornDate,email,adress,numberPhone);
        try {
            clienteBsn.save(cliente);
        } catch (Exception e) {
            System.out.println("Este id ya exise, por favor ingrese otro id");
        }
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

    public void btnEditUser(){
        try{
            AnchorPane edit = FXMLLoader.load(getClass().getClassLoader().getResource("view/userManager/client/edit-client.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void edit(){

    }


}
