package co.edu.udea.edatos.ejemplo.controller.equipo;

import co.edu.udea.edatos.ejemplo.bsn.EquipoBsn;
import co.edu.udea.edatos.ejemplo.model.Component;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ComponentController {

    @FXML
    private TextField txtSerial;
    @FXML
    private TextField txtProducer;
    @FXML
    private TextField txtDescription;

    static EquipoBsn equipoBs = new EquipoBsn();

    public void create(){
        int id = Integer.parseInt(txtSerial.getText());
        String producer = txtProducer.getText();
        String description = txtDescription.getText();

        Component component = new Component();
        component.setId(id);
        component.setProducer(producer);
        component.setDescription(description);

        equipoBs.registrarComponent(component);
        System.out.println("[INFO] Component serial: "+ id +" Component name: " + producer);
    }
}
