package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.FacturaTaskDao;
import co.edu.udea.edatos.ejemplo.dao.impl.FacturaTaskDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.model.FacturaTask;
import co.edu.udea.edatos.ejemplo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class FacturaTaskBsn {

    private static FacturaTaskDao repository = new FacturaTaskDaoNio();
    private FacturaBsn facturaBsn = new FacturaBsn();
    private TaskBsn taskBsn = new TaskBsn();

    public void save(FacturaTask facturaTask) throws Exception {
        Optional<FacturaTask> search = repository.read(facturaTask.getId());
        if (search.isPresent()) {
            throw new DuplicateKeyException();
        }
        repository.create(facturaTask);
    }

    public Optional<FacturaTask> findById(int id) {
        return repository.read(id);
    }

    public List<Task> getAllTaskOfFacturaById(int id) {
        List<FacturaTask> relations = getAllRelationsByFacturaId(id);
        List<Task> tasks = new ArrayList<>();
        for (FacturaTask ft : relations) {
            Optional<Task> task = taskBsn.findById(ft.getTaskId());
            if (task.isPresent()) {
                tasks.add(task.get());
            }
        }
        return tasks;
    }

    public Double getTotalPaymentOfFactura(int id) {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        List<Task> tasks = getAllTaskOfFacturaById(id);
        tasks.stream().forEach(task -> total.updateAndGet(v -> v + task.getPayment()));
        return total.get();
    }

    private List<FacturaTask> getAllRelationsByFacturaId(int id) {
        List<FacturaTask> relations = repository.findAll();
        List<FacturaTask> filtered = new ArrayList<>();
        for (FacturaTask ft : relations) {
            if (ft.getFacturaId() == id)
            filtered.add(ft);
        }
        return filtered;
    }

}
