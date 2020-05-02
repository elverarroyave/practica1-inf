package co.edu.udea.edatos.ejemplo.model;

public class TaskEquipoUser implements Comparable<TaskEquipoUser> {

    private int id;
    private int equipoId;
    private int taskId;
    private int empleadoId;
    private int clienteId;

    private Integer direction;

    public TaskEquipoUser() {
    }

    public TaskEquipoUser(int id, int equipoId, int taskId, int empleadoId, int clienteId) {
        this.id = id;
        this.equipoId = equipoId;
        this.taskId = taskId;
        this.empleadoId = empleadoId;
        this.clienteId = clienteId;
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

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return String.format("id: %s", id);
    }

    @Override
    public int compareTo(TaskEquipoUser o) {
        return id - o.getId();
    }

}
