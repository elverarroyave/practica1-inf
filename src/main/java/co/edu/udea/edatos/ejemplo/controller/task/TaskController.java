package co.edu.udea.edatos.ejemplo.controller.task;

import co.edu.udea.edatos.ejemplo.bsn.TaskBsn;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import co.edu.udea.edatos.ejemplo.model.Task;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class TaskController {

    @FXML
    private TableView<Task> tblTask;
    @FXML
    private TableColumn<Task, String> clmId;
    @FXML
    private TableColumn<Task, String> clmName;
    @FXML
    private TableColumn<Task, String> clmDescription;
    @FXML
    private TableColumn<Task, String> clmPayment;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtPayment;

    static TaskBsn taskBsn = new TaskBsn();

    public void listar() {
        List<Task> tasks = taskBsn.getAll();
        ObservableList<Task> tasksObservables = FXCollections.observableList(tasks);
        ///se asigna la lista observable a la tabla
        tblTask.setItems(tasksObservables);
        //se especifican los métodos que deben invocarse para obtener los valores a mostrar en las celdas
        clmId.setCellValueFactory(cellData->new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
        clmName.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getName()));
        clmDescription.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getDescription()));
        clmPayment.setCellValueFactory(cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getPayment())));
    }

    public void create() {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String description = txtDescription.getText();
        Double payment = Double.parseDouble(txtPayment.getText());
        Task task = new Task(id, name, description, payment);
        try {
            taskBsn.save(task);
            System.out.println("[INFO] Component serial: "+ id +" Component name: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
