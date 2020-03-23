package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;

import co.edu.udea.edatos.ejemplo.model.Cliente;

public interface ClienteDao {

    public void guardar(Cliente cliente);
    public List<Cliente> findAll();
    public Cliente findById(int id);

}
