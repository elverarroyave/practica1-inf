package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.EmpleadoDAO;
import co.edu.udea.edatos.ejemplo.dao.impl.EmpleadoDAOList;
import co.edu.udea.edatos.ejemplo.model.Empleado;

public class EmpleadoBsn {

    EmpleadoDAO empleadoDAO = new EmpleadoDAOList();

    public void registraEmpleado(Empleado empleado) {
        empleadoDAO.guardarEmpleado(empleado);
    }
}
