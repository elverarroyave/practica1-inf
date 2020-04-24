package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.ComponentDao;
import co.edu.udea.edatos.ejemplo.dao.impl.ComponentDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.exception.NotFoundEntityException;
import co.edu.udea.edatos.ejemplo.model.Component;

import java.util.List;
import java.util.Optional;

public class ComponentBsn {

    private static ComponentDao repository = new ComponentDaoNio();

    public ComponentBsn() { }

    public void save(Component component) throws Exception {
        Optional<Component> search = repository.read(component.getId());
        if (search.isPresent())
            throw new DuplicateKeyException();
    }

    public void update(Component component) throws Exception {
        Optional search = repository.read(component.getId());
        if (search.isPresent())
            repository.update(component);
        else
            throw new NotFoundEntityException();
    }

    public Optional<Component> findById(int id) {
        return repository.read(id);
    }

    public List<Component> getAll() {
        return repository.findAll();
    }

}
