package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.EquipoComponentDao;
import co.edu.udea.edatos.ejemplo.dao.impl.EquipoComponentDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.model.Component;
import co.edu.udea.edatos.ejemplo.model.EquipoComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EquipoComponentBsn {

    private static EquipoComponentDao repository = new EquipoComponentDaoNio();
    private static ComponentBsn componentBsn = new ComponentBsn();


    public void save(EquipoComponent equipoComponent) throws Exception {
        Optional<EquipoComponent> search = repository.read(equipoComponent.getId());
        if (search.isPresent() ||
                isThereRelationship(equipoComponent.getEquipoId(), equipoComponent.getComponentId()))
            throw new DuplicateKeyException();
        repository.create(equipoComponent);
    }

    public List<Component> getAllComponentsByEquipoId(int id) {
        List<EquipoComponent> relations = getAllRelationsByEquipoId(id);
        List<Component> components = new ArrayList<>();
        for (EquipoComponent relation : relations) {
            if (relation.getEquipoId() == id) {
                Optional<Component> component = componentBsn.findById(relation.getComponentId());
                if (component.isPresent())
                    components.add(component.get());
            }
        }
        return components;
    }

    private boolean isThereRelationship(int equipoId, int componentId) {
        return !getAllRelationsByEquipoId(equipoId).stream()
                .filter(relation -> relation.getComponentId() == componentId)
                .collect(Collectors.toList())
                .isEmpty();
    }

    private List<EquipoComponent> getAllRelationsByEquipoId(int id) {
        return repository.findAll().stream()
                .filter(relation -> relation.getEquipoId() == id)
                .collect(Collectors.toList());
    }

}
