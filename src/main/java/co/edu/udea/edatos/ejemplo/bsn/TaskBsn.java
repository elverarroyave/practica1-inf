package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.TaskDao;
import co.edu.udea.edatos.ejemplo.dao.impl.TaskDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.model.Task;

import java.util.List;
import java.util.Optional;

public class TaskBsn {
    private static TaskDao repository = new TaskDaoNio();

    public TaskBsn() { }

    public void save(Task task) throws Exception {
        Optional<Task> search = repository.read(task.getId());
        if (search.isPresent()) {
            throw new DuplicateKeyException();
        }
        repository.create(task);
    }

    public Optional<Task> findById(int id) {
        return repository.read(id);
    }

    public List<Task> getAll() {
        return repository.findAll();
    }
}
