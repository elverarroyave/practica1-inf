package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.model.Component;

public interface ComponentDao {

    Component create(Component component);
    Optional<Component> read(int id);
    void update(Component component);
    void destroy(int id);
    List<Component> findAll();

}