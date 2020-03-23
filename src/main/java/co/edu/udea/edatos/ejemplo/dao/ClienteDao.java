package co.edu.udea.edatos.ejemplo.dao;

import co.edu.udea.edatos.ejemplo.model.Cliente;

import java.util.List;

public interface ClienteDao {

    public void guardar(Cliente cliente);

    public List<Cliente> findAll();

}
