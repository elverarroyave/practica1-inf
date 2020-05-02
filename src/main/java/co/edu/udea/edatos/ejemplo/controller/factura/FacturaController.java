package co.edu.udea.edatos.ejemplo.controller.factura;

import co.edu.udea.edatos.ejemplo.bsn.FacturaBsn;
import co.edu.udea.edatos.ejemplo.bsn.SolicitudBsn;
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
import java.util.Optional;
import java.util.OptionalInt;

public class FacturaController {

    @FXML
    private TextField txtCliente;
    @FXML
    private TextField txtEquipo;
    @FXML
    private TextField txtTareas;
    @FXML
    private TextField txtTotal;
    @FXML
    private ComboBox<Solicitud> cmbSolicitud;

    @FXML
    private TableView<Task> tblTask;
    @FXML
    private TableColumn<Task, String> clmName;
    @FXML
    private TableColumn<Task, String> clmDescription;
    @FXML
    private TableColumn<Task, String> clmPayment;

    private static SolicitudBsn solicitudBsn = new SolicitudBsn();
    private static FacturaBsn facturaBsn = new FacturaBsn();

    @FXML
    public void initialize() {
        List<Solicitud> solicituds = solicitudBsn.getAllAvailableSolicitudes();
        ObservableList<Solicitud> equiposObservables = FXCollections.observableArrayList(solicituds);
        this.cmbSolicitud.setItems(equiposObservables);
    }

    public void cmbSolicitud_action() {

    }

    public void generate() {
        if (Objects.isNull(cmbSolicitud.getValue())) {
           return;
        }

        int id = cmbSolicitud.getValue().getId();
        Optional<Equipo> equipo = facturaBsn.getEquipo(id);
        Optional<Cliente> cliente = facturaBsn.getCliente(id);
        int tasks = facturaBsn.getNumberOfTask(id);
        Double total = facturaBsn.totalToPay(id);

        txtCliente.setText(cliente.get().toString());
        txtEquipo.setText(equipo.get().toString());
        txtTareas.setText(String.valueOf(tasks));
        txtTotal.setText(String.valueOf(total));

        List<Task> taskList = facturaBsn.getTasks(id);
        ///se crea una lista observable para hacer binding con la tabla
        ObservableList<Task> componentsObservables = FXCollections.observableList(taskList);
        ///se asigna la lista observable a la tabla
        tblTask.setItems(componentsObservables);
        //se especifican los métodos que deben invocarse para obtener los valores a mostrar en las celdas
        clmName.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getName()));
        clmDescription.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getDescription()));
        clmPayment.setCellValueFactory(cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getPayment())));
    }

}
