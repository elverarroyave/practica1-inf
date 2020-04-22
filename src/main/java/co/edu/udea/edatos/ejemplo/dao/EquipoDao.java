package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.model.Equipo;

public interface EquipoDao {

    Equipo create(Equipo equipo);
    Optional<Equipo> read(int id);
    void update(Equipo equipo);
    void destroy(int id);
    List<Equipo> findAll();

}