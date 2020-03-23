package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.EquipoDao;
import co.edu.udea.edatos.ejemplo.dao.impl.EquipoDaoNio;
import co.edu.udea.edatos.ejemplo.model.Equipo;

public class EquipoBsn {

    EquipoDao repository = new EquipoDaoNio();

    public void registrarEquipo(Equipo equipo){
        repository.guardar(equipo);
    }
    
} 