package co.edu.udea.edatos.ejemplo.controller;

import co.edu.udea.edatos.ejemplo.dao.*;
import co.edu.udea.edatos.ejemplo.dao.impl.*;
import co.edu.udea.edatos.ejemplo.model.Empleado;
import co.edu.udea.edatos.ejemplo.model.TaskEquipoUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ContenedorPrincipalController {

    @FXML
    private BorderPane contenedorPrincipal;

    public ContenedorPrincipalController() {
        ClienteDaoNio.crearIndice();
        ComponentDaoNio.crearIndice();
        EmpleadoDaoNio.crearIndice();
        EquipoComponentDaoNio.crearIndice();
        EquipoDaoNio.crearIndice();
        FacturaDaoNio.crearIndice();
        FacturaTaskDaoNio.crearIndice();
        SolicitudeDaoNio.crearIndice();
        SolicitudTaskDaoNio.crearIndice();
        TaskDaoNio.crearIndice();
        TaskEquipoUserDaoNio.crearIndice();
    }

    public void mnuSalir(){ System.exit(0);}

    public void mnuAddEmployee(){
        try{
            AnchorPane addEmployee = FXMLLoader.load(getClass().getClassLoader().getResource( "view/userManager/employee/add-employee.fxml"));
            contenedorPrincipal.setCenter(addEmployee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuAddClient(){
        try{
            AnchorPane addClient = FXMLLoader.load(getClass().getClassLoader().getResource("view/userManager/client/add-client.fxml"));
            contenedorPrincipal.setCenter(addClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuListClient(){
        try{
            AnchorPane listClient = FXMLLoader.load(getClass().getClassLoader().getResource("view/userManager/client/client-list.fxml"));
            contenedorPrincipal.setCenter(listClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuListEmployee(){
        try {
            AnchorPane listEmployee = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/employee/employee-list.fxml")));
            contenedorPrincipal.setCenter(listEmployee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuAddComponent(){
        try {
            AnchorPane addComponent = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/equipo/add-component.fxml")));
            contenedorPrincipal.setCenter(addComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuAddEquipo(){
        try {
            AnchorPane addEquipo = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/equipo/add-equipo.fxml")));
            contenedorPrincipal.setCenter(addEquipo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuAddEquipoComponent(){
        try {
            AnchorPane addComponentEquipo = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/equipo/add-equipo-component.fxml")));
            contenedorPrincipal.setCenter(addComponentEquipo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuAddTask(){
        try {
            AnchorPane addTask = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/task/add-task.fxml")));
            contenedorPrincipal.setCenter(addTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuListTask(){
        try {
            AnchorPane addComponentEquipo = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/task/task-list.fxml")));
            contenedorPrincipal.setCenter(addComponentEquipo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuAddSolicitud(){
        try {
            AnchorPane addSolicitud = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/solicitud/add-solicitud.fxml")));
            contenedorPrincipal.setCenter(addSolicitud);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuAddSolicitudTask(){
        try {
            AnchorPane addSolicitudTask = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/solicitud/add-task-solicitud.fxml")));
            contenedorPrincipal.setCenter(addSolicitudTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuGenerateFacutura(){
        try {
            AnchorPane generateFactura = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/factura/generate-factura.fxml")));
            contenedorPrincipal.setCenter(generateFactura);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuGenerateReport(){
        try {
            AnchorPane generateReport = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/report/generate-report.fxml")));
            contenedorPrincipal.setCenter(generateReport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tree(){
        try {
            AnchorPane tree = FXMLLoader.load((getClass().getClassLoader().getResource("view/userManager/tree/red-black-tree-view.fxml")));
            contenedorPrincipal.setCenter(tree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
