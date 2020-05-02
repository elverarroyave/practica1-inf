package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.*;
import co.edu.udea.edatos.ejemplo.dao.impl.*;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import co.edu.udea.edatos.ejemplo.model.Empleado;
import co.edu.udea.edatos.ejemplo.model.Solicitud;
import co.edu.udea.edatos.ejemplo.model.Task;

import java.util.List;

public class ReportBsn {
    private static ClienteDao clienteDao = new ClienteDaoNio();
    private static EmpleadoDao empleadoDao = new EmpleadoDaoNio();
    private static SolicitudDao solicitudDao = new SolicitudeDaoNio();
    private static TaskDao taskDao = new TaskDaoNio();

    public int numberOfTareas() {
        int count = 0;
        List<Task> empleados = taskDao.findAll();
        for (Task cliente : empleados) {
            count++;
        }
        return count;
    }

    public int numberOfSolicitudes() {
        int count = 0;
        List<Solicitud> empleados = solicitudDao.findAll();
        for (Solicitud cliente : empleados) {
            count++;
        }
        return count;
    }

    public int numberOfEmpleados() {
        int count = 0;
        List<Empleado> empleados = empleadoDao.findAll();
        for (Empleado cliente : empleados) {
            count++;
        }
        return count;
    }

    public int numberOfClients() {
        int count = 0;
        List<Cliente> clientes = clienteDao.findAll();
        for (Cliente cliente : clientes) {
            count++;
        }
        return count;
    }
}
