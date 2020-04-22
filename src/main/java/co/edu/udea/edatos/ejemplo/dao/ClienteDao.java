package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.model.Cliente;

public interface ClienteDao {

    Cliente create(Cliente cliente);
    Optional<Cliente> read(int id);
    void update(Cliente cliente);
    void destroy(int id);
    List<Cliente> findAll();

}
