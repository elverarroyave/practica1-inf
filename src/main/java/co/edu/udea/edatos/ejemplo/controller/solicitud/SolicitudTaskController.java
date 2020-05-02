package co.edu.udea.edatos.ejemplo.controller.solicitud;

import co.edu.udea.edatos.ejemplo.bsn.SolicitudBsn;
import co.edu.udea.edatos.ejemplo.bsn.SolicitudTaskBsn;
import co.edu.udea.edatos.ejemplo.bsn.TaskBsn;
import co.edu.udea.edatos.ejemplo.model.*;
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

public class SolicitudTaskController {
    @FXML
    private TextField txtRelationId;

    @FXML
    private ComboBox<Solicitud> cmbSolicitud;
    @FXML
    private ComboBox<Task> cmbTask;

    @FXML
    private TableView<Task> tblTask;
    @FXML
    private TableColumn<Task, String> clmId;
    @FXML
    private TableColumn<Task, String> clmName;
    @FXML
    private TableColumn<Task, String> clmPayment;

    static SolicitudTaskBsn relationBsn = new SolicitudTaskBsn();
    static SolicitudBsn solicitudBsn = new SolicitudBsn();
    static TaskBsn taskBsn = new TaskBsn();

    @FXML
    private void initialize() {
        List<Solicitud> solicitudes = solicitudBsn.getAllAvailableSolicitudes();
        ObservableList<Solicitud> equiposObservables = FXCollections.observableArrayList(solicitudes);
        this.cmbSolicitud.setItems(equiposObservables);

        List<Task> tasks = taskBsn.getAll();
        ObservableList<Task> componentsObservables = FXCollections.observableArrayList(tasks);
        this.cmbTask.setItems(componentsObservables);
    }

    public void cmbTask_action() {
        Solicitud solicitudSeleccionado = cmbSolicitud.getValue();
        if(Objects.isNull(solicitudSeleccionado )){
            return;
        }
        //se consultan las direcciones que ya existen
        List<Task> tasks = relationBsn.getAllTasksBySolicitudId(solicitudSeleccionado.getId());


        ///se crea una lista observable para hacer binding con la tabla
        ObservableList<Task> componentsObservables = FXCollections.observableList(tasks);
        ///se asigna la lista observable a la tabla
        tblTask.setItems(componentsObservables);
        //se especifican los métodos que deben invocarse para obtener los valores a mostrar en las celdas
        clmId.setCellValueFactory(cellData->new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
        clmName.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getName()));
        clmPayment.setCellValueFactory(cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getPayment())));
    }

    public void create() {
        Solicitud solicitudSeleccionado = cmbSolicitud.getValue();
        Task taskSeleccionado = cmbTask.getValue();
        int id = Integer.parseInt(txtRelationId.getText()); // For now

        SolicitudTask relation = new SolicitudTask(id, solicitudSeleccionado.getId(), taskSeleccionado.getId());
        if (solicitudSeleccionado != null && taskSeleccionado != null) {
            try {
                relationBsn.save(relation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cmbTask_action();
    }
}
