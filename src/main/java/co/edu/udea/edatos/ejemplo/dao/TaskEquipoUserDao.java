package co.edu.udea.edatos.ejemplo.dao;

import co.edu.udea.edatos.ejemplo.model.TaskEquipoUser;

import java.util.List;
import java.util.Optional;

public interface TaskEquipoUserDao {

    TaskEquipoUser create(TaskEquipoUser taskEquipoUser);
    Optional<TaskEquipoUser> read(int id);
    void update(TaskEquipoUser taskEquipoUser);
    void destroy(int id);
    List<TaskEquipoUser> findAll();

}
