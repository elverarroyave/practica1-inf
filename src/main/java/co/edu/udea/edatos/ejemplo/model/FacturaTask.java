package co.edu.udea.edatos.ejemplo.model;

public class FacturaTask implements Comparable<FacturaTask> {

    private int id;
    private int facturaId;
    private int taskId;

    public FacturaTask() {

    }

    public FacturaTask(int id, int facturaId, int taskId) {
        this.id = id;
        this.facturaId = facturaId;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public int compareTo(FacturaTask o) {
        return id - o.getId();
    }
}
