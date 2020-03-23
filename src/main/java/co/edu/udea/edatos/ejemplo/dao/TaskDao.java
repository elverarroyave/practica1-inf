package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;

import co.edu.udea.edatos.ejemplo.model.Task;

public interface TaskDao {

    public void guardar(Task task);
    public List<Task> findAll();
    public Task findById(int id);

}