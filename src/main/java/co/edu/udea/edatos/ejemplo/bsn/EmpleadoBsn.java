package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.EmpleadoDao;
import co.edu.udea.edatos.ejemplo.dao.impl.EmpleadoDAOList;
import co.edu.udea.edatos.ejemplo.model.Empleado;

public class EmpleadoBsn {

    EmpleadoDao repository = new EmpleadoDAOList();

    public void registraEmpleado(Empleado empleado) {
        repository.guardar(empleado);
    }
}
