package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;

import co.edu.udea.edatos.ejemplo.model.Empleado;

public interface EmpleadoDao {

    public void guardar(Empleado empleado);
    public List<Empleado> findAll();
    public Empleado findById(int id);

}
