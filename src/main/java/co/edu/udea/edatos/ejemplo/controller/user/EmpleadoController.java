package co.edu.udea.edatos.ejemplo.controller.user;

import co.edu.udea.edatos.ejemplo.bsn.EmpleadoBsn;
import co.edu.udea.edatos.ejemplo.model.Empleado;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EmpleadoController {

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

    EmpleadoBsn empleadoBsn = new EmpleadoBsn();

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
        Empleado empleado = new Empleado(id,name,lastName,idEmployee,passWord,bornDate,email,adress,numberPhone);
        empleadoBsn.registraEmpleado(empleado);

        confirm();





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

}
