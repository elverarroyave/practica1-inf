package co.edu.udea.edatos.ejemplo.model;


public class Solicitud {

    private int id;
    private boolean state;
    private String receptionDate;
    private int idEquipo;
    private int idClienteOwner;

    public Solicitud() {

    }

    public Solicitud(int id, boolean state, String receptionDate, int idEquipo, int idClienteOwner) {
        this.id = id;
        this.state = state;
        this.receptionDate = receptionDate;
        this.idEquipo = idEquipo;
        this.idClienteOwner = idClienteOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(String date) {
        this.receptionDate = date;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdClienteOwner() {
        return idClienteOwner;
    }

    public void setIdClienteOwner(int idClienteOwner) {
        this.idClienteOwner = idClienteOwner;
    }

}
