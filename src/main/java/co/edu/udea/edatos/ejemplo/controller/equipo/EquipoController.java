package co.edu.udea.edatos.ejemplo.controller.equipo;

import co.edu.udea.edatos.ejemplo.bsn.ClienteBsn;
import co.edu.udea.edatos.ejemplo.bsn.EquipoBsn;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import co.edu.udea.edatos.ejemplo.model.Component;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Objects;

public class EquipoController {

    @FXML
    private TextField txtEquipoSerial;
    @FXML
    private TextField txtBrand;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtPcOwner;
    @FXML
    private ComboBox<Cliente> cmbClientes;

    EquipoBsn equipoBs = new EquipoBsn();
    ClienteBsn clienteBsn = new ClienteBsn();

    @FXML
    public void initialize() {
        List<Cliente> clientes = clienteBsn.listarClientes();
        ObservableList<Cliente> equiposObservables = FXCollections.observableArrayList(clientes);
        this.cmbClientes.setItems(equiposObservables);
    }

    public void cmbUpdate() {
        List<Cliente> clientes = clienteBsn.listarClientes();
        ObservableList<Cliente> equiposObservables = FXCollections.observableArrayList(clientes);
        this.cmbClientes.setItems(equiposObservables);
    }

    public void create() {
        if (cmbClientes.getValue() == null)
            return;
        int id = Integer.parseInt(txtEquipoSerial.getText());
        String brand = txtBrand.getText();
        String model = txtModel.getText();
        int pcOwnerId = cmbClientes.getValue().getId();

            Equipo equipo = new Equipo();
            equipo.setId(id);
            equipo.setBrand(brand);
            equipo.setModel(model);
            equipo.setPcOwner(pcOwnerId);

            equipoBs.registrarEquipo(equipo);
            System.out.println("[INFO] Component serial: "+ id +" Component name: " + brand);

    }
}