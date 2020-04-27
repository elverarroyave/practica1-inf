package co.edu.udea.edatos.ejemplo.dao;


import co.edu.udea.edatos.ejemplo.model.FacturaTask;

import java.util.List;
import java.util.Optional;

public interface FacturaTaskDao {

    FacturaTask create(FacturaTask facturaTask);
    Optional<FacturaTask> read(int id);
    void update(FacturaTask facturaTask);
    void destroy(int id);
    List<FacturaTask> findAll();

}
