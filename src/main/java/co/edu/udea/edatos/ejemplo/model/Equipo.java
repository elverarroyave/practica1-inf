package co.edu.udea.edatos.ejemplo.model;

public class Equipo {

    private int id;
    private String brand;
    private String model;
    private String pcOwner;

    public Equipo(){

    }

    public Equipo(int id, String brand, String model, String pcOwner) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.pcOwner = pcOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPcOwner() {
        return pcOwner;
    }

    public void setPcOwner(String pcOwner) {
        this.pcOwner = pcOwner;
    }
}
