package co.edu.udea.edatos.ejemplo.model;

<<<<<<< Updated upstream
public class FacturaTask {
=======
public class FacturaTask implements Comparable<FacturaTask> {
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
=======

    @Override
    public int compareTo(FacturaTask o) {
        return id - o.getId();
    }
>>>>>>> Stashed changes
}
