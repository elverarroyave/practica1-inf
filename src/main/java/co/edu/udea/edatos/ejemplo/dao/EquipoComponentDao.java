package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.model.EquipoComponent;

public interface EquipoComponentDao {

    EquipoComponent create(EquipoComponent equipoComponent);
    Optional<EquipoComponent> read(int id);
    void update(EquipoComponent equipoComponent);
    void destroy(int id);
    List<EquipoComponent> findAll();

}