package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.ClienteDao;
import co.edu.udea.edatos.ejemplo.dao.impl.ClienteDaoNio;
import co.edu.udea.edatos.ejemplo.model.Cliente;

import java.util.List;

public class ClienteBsn {

    static ClienteDao repository = new ClienteDaoNio();

    public void registrarCliente(Cliente cliente){
        repository.guardar(cliente);
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }
    
}
