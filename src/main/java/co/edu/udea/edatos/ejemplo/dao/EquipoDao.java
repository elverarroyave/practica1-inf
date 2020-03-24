package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;

import co.edu.udea.edatos.ejemplo.model.Equipo;

public interface EquipoDao {

    public void guardar(Equipo equipo);
    public Equipo getEquipoById(int id);
    public List<Equipo> getEquipoByUserId(int userId);
    public List<Equipo> findAll();

}