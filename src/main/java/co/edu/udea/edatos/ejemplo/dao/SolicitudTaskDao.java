package co.edu.udea.edatos.ejemplo.dao;


import co.edu.udea.edatos.ejemplo.model.SolicitudTask;

import java.util.List;
import java.util.Optional;

public interface SolicitudTaskDao {

    SolicitudTask create(SolicitudTask st);
    Optional<SolicitudTask> read(int id);
    void update(SolicitudTask st);
    void destroy(int id);
    List<SolicitudTask> findAll();
}
