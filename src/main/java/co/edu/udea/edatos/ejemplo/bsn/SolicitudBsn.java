package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.SolicitudDao;
import co.edu.udea.edatos.ejemplo.dao.impl.SolicitudeDaoNio;
import co.edu.udea.edatos.ejemplo.model.Solicitud;

public class SolicitudBsn {

    static SolicitudDao repository = new SolicitudeDaoNio();

    public void guardar(Solicitud solicitud) {
        repository.guardar(solicitud);
    }

}