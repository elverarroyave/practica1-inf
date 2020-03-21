package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.EmpleadoDAO;
import co.edu.udea.edatos.ejemplo.model.Empleado;

import javax.swing.*;
import java.util.ArrayList;

public class EmpleadoDAOList implements EmpleadoDAO {

    private static ArrayList<Empleado> bdEmpleados = new ArrayList<>();

    @Override
    public void guardarEmpleado(Empleado empleado) {
        bdEmpleados.add(empleado);
        JOptionPane.showMessageDialog(null,"Empleado almacenado correctamente", "Base de datos empleado", JOptionPane.INFORMATION_MESSAGE  );
    }
}
