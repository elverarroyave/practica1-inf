package co.edu.udea.edatos.ejemplo.model;


public class EquipoComponent implements Comparable<EquipoComponent> {

    private int id;
    private int equipoId;
    private int componentId;

    public EquipoComponent() {
    }

    public EquipoComponent(int id, int equipoId, int componentId) {
        this.id = id;
        this.equipoId = equipoId;
        this.componentId = componentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int id) {
        this.equipoId = id;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int id) {
        this.componentId = id;
    }

    @Override
    public int compareTo(EquipoComponent o) {
        return id - o.getId();
    }
}