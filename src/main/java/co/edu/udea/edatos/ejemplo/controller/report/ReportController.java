package co.edu.udea.edatos.ejemplo.controller.report;

import co.edu.udea.edatos.ejemplo.bsn.ReportBsn;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ReportController {

    @FXML
    private TextField txtCliente;
    @FXML
    private TextField txtEmpleado;
    @FXML
    private TextField txtTareas;
    @FXML
    private TextField txtSolicitudes;

    private static ReportBsn reportBsn = new ReportBsn();

    public void generate() {
        int clientes = reportBsn.numberOfClients();
        int empleados = reportBsn.numberOfEmpleados();
        int solicitudes = reportBsn.numberOfSolicitudes();
        int task = reportBsn.numberOfTareas();

        txtCliente.setText(String.valueOf(clientes));
        txtEmpleado.setText(String.valueOf(empleados));
        txtSolicitudes.setText(String.valueOf(solicitudes));
        txtTareas.setText(String.valueOf(task));
    }

}
