package co.edu.udea.edatos.ejemplo.controller.equipo;

import co.edu.udea.edatos.ejemplo.bsn.ClienteBsn;
import co.edu.udea.edatos.ejemplo.bsn.EquipoBsn;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

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

    private static EquipoBsn equipoBs = new EquipoBsn();
    private static ClienteBsn clienteBsn = new ClienteBsn();

    @FXML
    public void initialize() {
        List<Cliente> clientes = clienteBsn.getAll();
        ObservableList<Cliente> equiposObservables = FXCollections.observableArrayList(clientes);
        this.cmbClientes.setItems(equiposObservables);
    }

    public void list() {
        List<Cliente> clientes = clienteBsn.getAll();
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
        try {
            equipoBs.save(equipo);
            System.out.println("[INFO] Component serial: "+ id +" Component name: " + brand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}