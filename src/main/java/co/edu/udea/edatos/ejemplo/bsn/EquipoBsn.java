package co.edu.udea.edatos.ejemplo.bsn;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.edatos.ejemplo.dao.ComponentDao;
import co.edu.udea.edatos.ejemplo.dao.EquipoComponentDao;
import co.edu.udea.edatos.ejemplo.dao.EquipoDao;
import co.edu.udea.edatos.ejemplo.dao.impl.ComponentDaoNio;
import co.edu.udea.edatos.ejemplo.dao.impl.EquipoComponentDaoNio;
import co.edu.udea.edatos.ejemplo.dao.impl.EquipoDaoNio;
import co.edu.udea.edatos.ejemplo.model.Component;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import co.edu.udea.edatos.ejemplo.model.EquipoComponent;

public class EquipoBsn {

    static EquipoComponentDao equipoComponentDao = new EquipoComponentDaoNio();
    static ComponentDao componentDao = new ComponentDaoNio();
    static EquipoDao equipoDao = new EquipoDaoNio();

    public void registrarEquipo(Equipo equipo){
        equipoDao.guardar(equipo);
    }

    public void registrarComponent(Component component) {
        componentDao.guardar(component);
    }

    public void registrarComponenteEnUnEquipo(Equipo equipo, Component component) {
        EquipoComponent ec = new EquipoComponent();
        ec.setEquipoId(equipo.getId());
        ec.setComponentId(component.getId());
        equipoComponentDao.guardar(ec);
    }

    public List<Component> listarComponentes() {
        return componentDao.findAll();
    }

    public List<Equipo> listarEquipos() {
        return equipoDao.findAll();
    }

    public List<Component> getComponentsOfEquipo(Equipo equipo) {
        List<EquipoComponent> relation = equipoComponentDao.getComponentByEquipoId(equipo.getId());
        List<Component> components = new ArrayList<>();
        for (EquipoComponent ec : relation) {
            components.add(componentDao.findById(ec.getComponentId()));
        }
        return components;
    }
    
} 