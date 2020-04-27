package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.EmpleadoDao;
import co.edu.udea.edatos.ejemplo.dao.impl.EmpleadoDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.exception.NotFoundEntityException;
import co.edu.udea.edatos.ejemplo.model.Empleado;

import java.util.List;
import java.util.Optional;

public class EmpleadoBsn {

    private static EmpleadoDao repository = new EmpleadoDaoNio();

    public void save(Empleado empleado) throws Exception {
        Optional<Empleado> search = repository.read(empleado.getId());
        if (search.isPresent())
           throw new DuplicateKeyException();
        repository.create(empleado);
    }

    public void update(Empleado empleado) throws Exception {
        Optional search = repository.read(empleado.getId());
        if (search.isPresent())
            repository.update(empleado);
        else
           throw new NotFoundEntityException();
    }

    public Optional<Empleado> findById(int id) {
        return repository.read(id);
    }

    public List<Empleado> getAll() {
        return repository.findAll();
    }

}
