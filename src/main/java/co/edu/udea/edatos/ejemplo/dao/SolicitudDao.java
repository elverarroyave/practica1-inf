package co.edu.udea.edatos.ejemplo.dao;

import java.util.List;

import co.edu.udea.edatos.ejemplo.model.Solicitud;

public interface SolicitudDao {

    public void guardar(Solicitud Solicitud);
    public List<Solicitud> getSolicitudesByIncompleteState();
    public List<Solicitud> getSolicitudesByCompleteState();
    public List<Solicitud> getSolicitudesByUserId(int userId);
    public List<Solicitud> getSolicitudesByEquipoId(int equipoId);

}
