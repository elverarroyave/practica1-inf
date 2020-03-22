package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.FacturaDao;
import co.edu.udea.edatos.ejemplo.dao.impl.FacturaDaoNio;
import co.edu.udea.edatos.ejemplo.model.Factura;

public class FacturaBsn {

    FacturaDao repository = new FacturaDaoNio();

    public void guardar(Factura factura) {
        repository.guardar(factura);
    }

}