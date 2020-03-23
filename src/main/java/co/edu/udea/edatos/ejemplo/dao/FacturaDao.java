package co.edu.udea.edatos.ejemplo.dao;

import co.edu.udea.edatos.ejemplo.model.Factura;

public interface FacturaDao {

    public void guardar(Factura factura);
    public Factura getFacturaBySolicitudeId(int id);

}