package co.edu.udea.edatos.ejemplo.controller.equipo;

import co.edu.udea.edatos.ejemplo.bsn.ComponentBsn;
import co.edu.udea.edatos.ejemplo.bsn.EquipoBsn;
import co.edu.udea.edatos.ejemplo.bsn.EquipoComponentBsn;
import co.edu.udea.edatos.ejemplo.model.Component;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import co.edu.udea.edatos.ejemplo.model.EquipoComponent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Objects;

public class EquipoComponentController {
    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<Equipo> cmbEquipos;
    @FXML
    private ComboBox<Component> cmbComponents;
    @FXML
    private TableView<Component> tblComponents;
    @FXML
    private TableColumn<Component, String> clmId;
    @FXML
    private TableColumn<Component, String> clmProducer;
    @FXML
    private TableColumn<Component, String> clmDescription;

    EquipoBsn equipoBs = new EquipoBsn();
    ComponentBsn componentBsn = new ComponentBsn();
    EquipoComponentBsn relationBsn = new EquipoComponentBsn();

    @FXML
    private void initialize() {
        List<Equipo> equipos = equipoBs.getAll();
        ObservableList<Equipo> equiposObservables = FXCollections.observableArrayList(equipos);
        this.cmbEquipos.setItems(equiposObservables);

        List<Component> components = componentBsn.getAll();
        ObservableList<Component> componentsObservables = FXCollections.observableArrayList(components);
        this.cmbComponents.setItems(componentsObservables);
    }

    public void cmbEquipos_action(){
        Equipo equipoSeleccionado = cmbEquipos.getValue();
        if(Objects.isNull(equipoSeleccionado)){
            return;
        }
        //se consultan las direcciones que ya existen
        List<Component> components = relationBsn.getAllComponentsByEquipoId(equipoSeleccionado.getId());

        ///se crea una lista observable para hacer binding con la tabla
        ObservableList<Component> componentsObservables = FXCollections.observableList(components);
        ///se asigna la lista observable a la tabla
        tblComponents.setItems(componentsObservables);
        //se especifican los métodos que deben invocarse para obtener los valores a mostrar en las celdas
        clmId.setCellValueFactory(cellData->new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
        clmProducer.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getProducer()));
        clmDescription.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getDescription()));
    }

    public void create() {
        Component componentSeleccionado = cmbComponents.getValue();
        Equipo equipoSeleccionado = cmbEquipos.getValue();
        int id = Integer.parseInt(txtId.getText()); // For now

        EquipoComponent relation = new EquipoComponent(id, equipoSeleccionado.getId(), componentSeleccionado.getId());
        if (componentSeleccionado != null && equipoSeleccionado != null) {
            try {
                relationBsn.save(relation);
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
        cmbEquipos_action();
    }
}
