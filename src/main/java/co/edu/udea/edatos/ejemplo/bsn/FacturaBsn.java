package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.SolicitudDao;
import co.edu.udea.edatos.ejemplo.dao.impl.SolicitudeDaoNio;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import co.edu.udea.edatos.ejemplo.model.Solicitud;
import co.edu.udea.edatos.ejemplo.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class FacturaBsn {

    private static SolicitudTaskBsn relation = new SolicitudTaskBsn();
    private static SolicitudDao solicitudRepository = new SolicitudeDaoNio();

    private static ClienteBsn clienteBsn = new ClienteBsn();
    private static EquipoBsn equipoBsn = new EquipoBsn();

    public FacturaBsn() {}

    public Optional<Cliente> getCliente(int id) {
        Optional<Solicitud> solicitud = solicitudRepository.read(id);
        Optional<Equipo> equipo = equipoBsn.findById(solicitud.get().getIdEquipo());
        Optional<Cliente> cliente = clienteBsn.findById(equipo.get().getPcOwner());
        return cliente;
    }

    public Optional<Equipo> getEquipo(int id) {
        Optional<Solicitud> solicitud = solicitudRepository.read(id);
        Optional<Equipo> equipo = equipoBsn.findById(solicitud.get().getIdEquipo());
        return equipo;
    }

    public List<Task> getTasks(int id) {
        List<Task> tasks = relation.getAllTasksBySolicitudId(id);
        return tasks;
    }

    public int getNumberOfTask(int id) {
        List<Task> tasks = relation.getAllTasksBySolicitudId(id);
        int count = 0;
        for (Task task : tasks)
            count++;
        return count;
    }

    public Double totalToPay(int id) {
        List<Task> tasks = relation.getAllTasksBySolicitudId(id);
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        tasks.stream().forEach(n -> total.updateAndGet(v -> v + n.getPayment()));
        return total.get();
    }

}