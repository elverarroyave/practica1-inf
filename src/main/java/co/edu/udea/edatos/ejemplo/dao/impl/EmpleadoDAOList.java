package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.EmpleadoDao;
import co.edu.udea.edatos.ejemplo.model.Empleado;

import javax.swing.*;
import java.util.ArrayList;

public class EmpleadoDAOList implements EmpleadoDao {

    private static ArrayList<Empleado> bdEmpleados = new ArrayList<>();

    @Override
    public void guardar(Empleado empleado) {
        bdEmpleados.add(empleado);
        JOptionPane.showMessageDialog(null,"Empleado almacenado correctamente", "Base de datos empleado", JOptionPane.INFORMATION_MESSAGE  );
    }
}
