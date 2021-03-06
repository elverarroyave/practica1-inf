package co.edu.udea.edatos.ejemplo.model;

public class Cliente implements Comparable<Cliente> {
    private int id;
    private String name;
    private String lastName;
    private String dateBorn;
    private String email;
    private String adress;
    private String numberPhone;

    private Integer direction;

    public Cliente() {

    }

    public Cliente(int id, String name, String lastName, String dateBorn, String email, String adress, String numberPhone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dateBorn = dateBorn;
        this.email = email;
        this.adress = adress;
        this.numberPhone = numberPhone;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(String dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return String.format("Id: %s Name: %s", id, name);
    }

    @Override
    public int compareTo(Cliente o) {
        return id - o.getId();
    }
}
