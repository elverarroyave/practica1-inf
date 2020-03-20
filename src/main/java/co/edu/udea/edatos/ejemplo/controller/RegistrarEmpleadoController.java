package co.edu.udea.edatos.ejemplo.controller;

import co.edu.udea.edatos.ejemplo.model.Empleado;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistrarEmpleadoController {

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

    public void addEmployee(){
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

        System.out.println("Registramos empleado con nombre "+ name +" y identificacion " + txtId.getText());
    }

}
