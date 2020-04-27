package co.edu.udea.edatos.ejemplo.controller.solicitud;

import co.edu.udea.edatos.ejemplo.bsn.EquipoBsn;
import co.edu.udea.edatos.ejemplo.bsn.SolicitudBsn;
import co.edu.udea.edatos.ejemplo.bsn.TaskBsn;
import co.edu.udea.edatos.ejemplo.model.Component;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import co.edu.udea.edatos.ejemplo.model.Solicitud;
import co.edu.udea.edatos.ejemplo.model.Task;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Objects;

public class SolicitudController {
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtDate;

    @FXML
    private ComboBox<Equipo> cmbEquipo;

    EquipoBsn equipoBsn = new EquipoBsn();
    SolicitudBsn solicitudBsn = new SolicitudBsn();

    @FXML
    private void initialize() {
        List<Equipo> equipos = equipoBsn.getAll();
        ObservableList<Equipo> equiposObservables = FXCollections.observableArrayList(equipos);
        this.cmbEquipo.setItems(equiposObservables);
    }

    public void create() {
        Equipo equipoSeleccionado = cmbEquipo.getValue();
        if (Objects.isNull(equipoSeleccionado)) {
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        boolean status = true;
        String receptionDate = txtDate.getText();
        int idEquipo = equipoSeleccionado.getId();
        int idCliente = 0; // El id del dueño lo obtenemos a partir de idEquipo

        Solicitud solicitud = new Solicitud(id, status, receptionDate, idEquipo, idCliente);
        try {
            solicitudBsn.save(solicitud);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
