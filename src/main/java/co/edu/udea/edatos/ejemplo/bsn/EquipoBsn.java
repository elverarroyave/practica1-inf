package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.EquipoDao;
import co.edu.udea.edatos.ejemplo.dao.impl.EquipoDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.model.Equipo;

import java.util.List;
import java.util.Optional;

public class EquipoBsn {
    private static EquipoDao repository = new EquipoDaoNio();

    public void save(Equipo equipo) throws Exception {
        Optional<Equipo> search = repository.read(equipo.getId());
        if (search.isPresent()) {
            throw new DuplicateKeyException();
        }
        repository.create(equipo);
    }

    public Optional<Equipo> findById(int id) {
        return repository.read(id);
    }

    public List<Equipo> getAll() {
        return repository.findAll();
    }

}
