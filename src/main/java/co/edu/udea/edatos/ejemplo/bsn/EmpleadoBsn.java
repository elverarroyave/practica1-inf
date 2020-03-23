package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.EmpleadoDao;
import co.edu.udea.edatos.ejemplo.dao.impl.EmpleadoDaoNio;
import co.edu.udea.edatos.ejemplo.model.Empleado;

public class EmpleadoBsn {

    static EmpleadoDao repository = new EmpleadoDaoNio();

    public void registraEmpleado(Empleado empleado) {
        repository.guardar(empleado);
    }
}
