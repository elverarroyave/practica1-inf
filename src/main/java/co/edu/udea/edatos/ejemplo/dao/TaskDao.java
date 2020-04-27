package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.model.Task;

public interface TaskDao {

    Task create(Task task);
    Optional<Task> read(int id);
    void update(Task task);
    void destroy(int id);
    List<Task> findAll();

}