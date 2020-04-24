package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.FacturaDao;
import co.edu.udea.edatos.ejemplo.dao.impl.FacturaDaoNio;
import co.edu.udea.edatos.ejemplo.exception.DuplicateKeyException;
import co.edu.udea.edatos.ejemplo.model.Factura;

import java.util.List;
import java.util.Optional;

public class FacturaBsn {

    private static FacturaDao repository = new FacturaDaoNio();

    public void save(Factura factura) throws Exception {
        Optional<Factura> search = repository.read(factura.getId());
        if (search.isPresent())
            throw new DuplicateKeyException();
        repository.create(factura);
    }

    public Optional<Factura> findById(int id) {
        return repository.read(id);
    }

    public Optional<Factura> getFacturaBySolicitudId(int id) {
        List<Factura> facturas = repository.findAll();
        Optional<Factura> response = Optional.empty();
        for (Factura factura : facturas) {
            if (factura.getSolicitudeId() == id)
                response.of(factura);
        }
        return response;
    }

    public List<Factura> getAll() {
        return repository.findAll();
    }

}