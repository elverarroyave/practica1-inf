package co.edu.udea.edatos.ejemplo.controller.user;

import co.edu.udea.edatos.ejemplo.bsn.EmpleadoBsn;
import co.edu.udea.edatos.ejemplo.model.Empleado;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class EmpleadoController {

    @FXML
    public AnchorPane editEmployee;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtIdEmployee;
    @FXML
    private TextField txtPassWord;
    @FXML
    private TextField txtBornDate;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAdress;
    @FXML
    private TextField txtNumberPhone;

    static EmpleadoBsn empleadoBsn = new EmpleadoBsn();

    public void create(){
        String name = txtName.getText();
        int id = Integer.parseInt(txtId.getText());
        String lastName = txtLastName.getText();
        String idEmployee = txtIdEmployee.getText();
        String passWord = txtPassWord.getText();
        String bornDate =txtBornDate.getText();
        String email = txtEmail.getText();
        String adress = txtAdress.getText();
        String numberPhone = txtNumberPhone.getText();
        if (name.equals("")
            || lastName.equals("")
            || idEmployee.equals("")) {
            return;
        }

        Empleado empleado = new Empleado(id,name,lastName,idEmployee,passWord,bornDate,email,adress,numberPhone);
        try {
            empleadoBsn.save(empleado);
        //    Empleado newEmpleado = new Empleado(
        //            empleado.getId(),
        //            "s",
        //            "s",
        //            "s",
        //            "s",
        //            "s",
        //            "s",
        //            "s",
        //            "s"
        //    );
        //    empleadoBsn.update(newEmpleado);
            confirm();
        } catch (Exception e) {
            e.printStackTrace();
            abort();
        }
    }

    private void vaciarCampos(){
        txtId.clear();
        txtName.clear();
        txtLastName.clear();
        txtIdEmployee.clear();
        txtPassWord.clear();
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

    private void abort(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operacion");
        alert.setHeaderText("Guardar usuario");
        alert.setContentText("Usuario no almacenado");
        alert.showAndWait();
        vaciarCampos();
    }

    public void btnEditUser(){
        try{
            AnchorPane edit = FXMLLoader.load(getClass().getClassLoader().getResource("view/userManager/employee/edit-employee.fxml"));
            editEmployee.setVisible(false);
            edit.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void edit(){

    }

}
