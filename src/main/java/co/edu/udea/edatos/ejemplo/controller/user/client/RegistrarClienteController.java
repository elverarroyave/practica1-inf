package co.edu.udea.edatos.ejemplo.controller.user.client;

import co.edu.udea.edatos.ejemplo.bsn.ClienteBsn;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class RegistrarClienteController {


    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtBornDate;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAdress;
    @FXML
    private TextField txtNumberPhone;

    //Creamos e instanciamos la clase del negocio cliente

    ClienteBsn clienteBsn = new ClienteBsn();

    public void create(){

        //Capturamos los datos de la vista

        String name = txtName.getText();
        int id = Integer.parseInt(txtId.getText());
        String lastName = txtLastName.getText();
        String bornDate =txtBornDate.getText();
        String email = txtEmail.getText();
        String adress = txtAdress.getText();
        String numberPhone = txtNumberPhone.getText();

        //Creamos un archivo plano o un POJO que luego sera enviado entre capas para seguir el patron de MVC

        Cliente cliente = new Cliente(id,name,lastName,bornDate,email,adress,numberPhone);

        //El POJO es capturado por el negocio cliente

        clienteBsn.registrarCliente(cliente);

        System.out.println("Registramos Cliente con nombre "+ name +" y identificacion " + txtId.getText());

    }

}
