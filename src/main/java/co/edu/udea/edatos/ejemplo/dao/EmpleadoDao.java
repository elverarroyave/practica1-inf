package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.model.Empleado;

public interface EmpleadoDao {

    Empleado create(Empleado empleado);
    Optional<Empleado> read(int id);
    void update(Empleado empleado);
    void destroy(int id);
    List<Empleado> findAll();

}
