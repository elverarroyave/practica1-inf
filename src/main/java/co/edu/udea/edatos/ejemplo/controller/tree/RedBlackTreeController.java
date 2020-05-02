package co.edu.udea.edatos.ejemplo.controller.tree;

import co.edu.udea.edatos.ejemplo.bsn.RedBlackTreeBsn;
import co.edu.udea.edatos.ejemplo.model.Solicitud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Objects;

public class RedBlackTreeController {

    @FXML
    private ComboBox<String> cmbFile;

    @FXML
    private TextField txtEquipo;
    @FXML
    private TextField idDraw;

    private static RedBlackTreeBsn treeBsn = new RedBlackTreeBsn();

    @FXML
    public void initialize() {
        List<String> files = treeBsn.getAllFiles();
        ObservableList<String> ofiles = FXCollections.observableArrayList(files);
        this.cmbFile.setItems(ofiles);
    }

    public void cmbFile_action() {

    }

    public void view() {
        if (Objects.isNull(cmbFile.getValue())) {
            return;
        }
        String tree = treeBsn.execute(cmbFile.getValue());
        idDraw.setText(tree);
    }

}
