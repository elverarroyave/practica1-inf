package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.SolicitudTaskDao;
import co.edu.udea.edatos.ejemplo.dao.TaskDao;
import co.edu.udea.edatos.ejemplo.dao.impl.SolicitudTaskDaoNio;
import co.edu.udea.edatos.ejemplo.dao.impl.TaskDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.model.Component;
import co.edu.udea.edatos.ejemplo.model.EquipoComponent;
import co.edu.udea.edatos.ejemplo.model.SolicitudTask;
import co.edu.udea.edatos.ejemplo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SolicitudTaskBsn {

    private static SolicitudTaskDao repository = new SolicitudTaskDaoNio();
    private static TaskDao taskRepository = new TaskDaoNio();

    public SolicitudTaskBsn() {

    }

    public void save(SolicitudTask equipoComponent) throws Exception {
        Optional<SolicitudTask> search = repository.read(equipoComponent.getId());
        if (search.isPresent() ||
                isThereRelationship(equipoComponent.getSolicitudId(), equipoComponent.getTaskId()))
            throw new DuplicateKeyException();
        repository.create(equipoComponent);
    }

    public List<Task> getAllTasksBySolicitudId(int id) {
        List<SolicitudTask> relations = getAllRelationsBySolicitudId(id);
        List<Task> components = new ArrayList<>();
        for (SolicitudTask relation : relations) {
            if (relation.getSolicitudId() == id) {
                Optional<Task> component = taskRepository.read(relation.getTaskId());
                if (component.isPresent())
                    components.add(component.get());
            }
        }
        return components;
    }

    private boolean isThereRelationship(int solicitudId, int taskId) {
        return !getAllRelationsBySolicitudId(solicitudId).stream()
                .filter(relation -> relation.getTaskId() == taskId)
                .collect(Collectors.toList())
                .isEmpty();
    }

    private List<SolicitudTask> getAllRelationsBySolicitudId(int id) {
        return repository.findAll().stream()
                .filter(relation -> relation.getSolicitudId() == id)
                .collect(Collectors.toList());
    }

}
