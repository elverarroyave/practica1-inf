package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.model.Solicitud;

public interface SolicitudDao {

    Solicitud create(Solicitud factura);
    Optional<Solicitud> read(int id);
    void update(Solicitud factura);
    void destroy(int id);
    List<Solicitud> findAll();

}
