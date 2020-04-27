package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.SolicitudDao;
import co.edu.udea.edatos.ejemplo.dao.impl.SolicitudeDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.model.Solicitud;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class SolicitudBsn {

    static SolicitudDao repository = new SolicitudeDaoNio();

    public void save(Solicitud solicitud) throws Exception {
        Optional<Solicitud> search = repository.read(solicitud.getId());
        if (search.isPresent())
            throw new DuplicateKeyException();
        repository.create(solicitud);
    }

    public List<Solicitud> getAllAvailableSolicitudes() {
        return repository.findAll().stream()
                .filter(solicitud -> solicitud.getState())
                .collect(Collectors.toList());
    }

}