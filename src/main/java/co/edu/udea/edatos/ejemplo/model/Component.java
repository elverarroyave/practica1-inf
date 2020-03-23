package co.edu.udea.edatos.ejemplo.model;


public class Component {

    private int id;
    private String producer;
    private String description;

    public Component() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Id: %s Producer: %s Description: %s", id, producer, description);
    }

}