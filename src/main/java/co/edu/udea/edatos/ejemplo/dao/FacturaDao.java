package co.edu.udea.edatos.ejemplo.dao;

import co.edu.udea.edatos.ejemplo.model.Factura;

import java.util.List;
import java.util.Optional;

public interface FacturaDao {

    Factura create(Factura factura);
    Optional<Factura> read(int id);
    void update(Factura factura);
    void destroy(int id);
    List<Factura> findAll();

}