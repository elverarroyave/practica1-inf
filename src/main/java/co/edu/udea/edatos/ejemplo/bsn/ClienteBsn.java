package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.ClienteDao;
import co.edu.udea.edatos.ejemplo.dao.impl.ClienteDAOList;
import co.edu.udea.edatos.ejemplo.model.Cliente;

public class ClienteBsn {

    ClienteDao repository = new ClienteDAOList();

    public void registrarCliente(Cliente cliente){
        repository.guardar(cliente);
    }
    
}
