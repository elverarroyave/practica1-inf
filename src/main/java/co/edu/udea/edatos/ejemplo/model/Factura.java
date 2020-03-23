package co.edu.udea.edatos.ejemplo.model;

public class Factura {

    private int id;
    private Double payment;
    private String description;
    private String returnDate;
    private int solicitudeId;

    public Factura() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSolicitudeId() {
        return solicitudeId;
    }

    public void setSolitudeId(int id) {
        this.solicitudeId = id;
    }
 
    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    
}