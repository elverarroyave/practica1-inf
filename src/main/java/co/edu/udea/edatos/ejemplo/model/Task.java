package co.edu.udea.edatos.ejemplo.model;

public class Task {

    private int id;
    private String name;
    private String description;
    private Double payment;

    public Task() {

    }

    public Task(int id, String name, String description, Double payment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

}