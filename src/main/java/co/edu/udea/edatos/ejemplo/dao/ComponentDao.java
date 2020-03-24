package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;

import co.edu.udea.edatos.ejemplo.model.Component;

public interface ComponentDao {

    public void guardar(Component component);
    public List<Component> findAll();
    public Component findById(int id);

}