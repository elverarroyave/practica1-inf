package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;

import co.edu.udea.edatos.ejemplo.model.EquipoComponent;

public interface EquipoComponentDao {

    public void guardar(EquipoComponent equipoComponent);
    public List<EquipoComponent> getComponentByEquipoId(int id);

}