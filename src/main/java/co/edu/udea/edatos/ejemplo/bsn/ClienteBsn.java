package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.ClienteDAO;
import co.edu.udea.edatos.ejemplo.dao.impl.ClienteDAOList;
import co.edu.udea.edatos.ejemplo.model.Cliente;

public class ClienteBsn {

    ClienteDAO clienteDAO = new ClienteDAOList();

    public void registrarCliente(Cliente cliente){
        clienteDAO.guardarCliente(cliente);
    }
}
