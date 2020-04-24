package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.ClienteDao;
import co.edu.udea.edatos.ejemplo.dao.impl.ClienteDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.exception.NotFoundEntityException;
import co.edu.udea.edatos.ejemplo.model.Cliente;

import java.util.List;
import java.util.Optional;

public class ClienteBsn {

    private static ClienteDao repository = new ClienteDaoNio();

    public void save(Cliente cliente) throws Exception {
        Optional<Cliente> search = repository.read(cliente.getId());
        if (search.isPresent())
            throw new DuplicateKeyException();
        repository.create(cliente);
    }

    public void update(Cliente cliente) throws Exception {
        Optional search = repository.read(cliente.getId());
        if (search.isPresent())
            repository.update(cliente);
        else
            throw new NotFoundEntityException();
    }

    public Optional<Cliente> findById(int id) {
        return repository.read(id);
    }

    public List<Cliente> getAll() {
        return repository.findAll();
    }

}
