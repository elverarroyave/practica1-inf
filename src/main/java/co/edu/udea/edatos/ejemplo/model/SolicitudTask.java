package co.edu.udea.edatos.ejemplo.model;

public class SolicitudTask implements Comparable<SolicitudTask> {
    private int id;
    private int solicitudId;
    private int taskId;

    private Integer direction;

    public SolicitudTask() {

    }

    public SolicitudTask(int id, int solicitudId, int taskId) {
        this.id = id;
        this.solicitudId = solicitudId;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(int solicitudId) {
        this.solicitudId = solicitudId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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
    public int compareTo(SolicitudTask o) {
        return id - o.getId();
    }
}
