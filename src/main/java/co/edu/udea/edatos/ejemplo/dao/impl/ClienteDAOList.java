package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.ClienteDAO;
import co.edu.udea.edatos.ejemplo.model.Cliente;

import java.util.ArrayList;

public class ClienteDAOList implements ClienteDAO {

    private static ArrayList<Cliente> bdClientes = new ArrayList<>();

    @Override
    public void guardarCliente(Cliente cliente) {
        bdClientes.add(cliente);
    }
}
