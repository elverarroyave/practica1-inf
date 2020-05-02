package co.edu.udea.edatos.ejemplo.bsn;

import co.edu.udea.edatos.ejemplo.dao.impl.*;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTreeBsn {

    private final String a = ComponentDaoNio.NOMBRE_ARCHIVO;
    private final String b = EmpleadoDaoNio.NOMBRE_ARCHIVO;
    private final String c = EquipoComponentDaoNio.NOMBRE_ARCHIVO;
    private final String d = EquipoDaoNio.NOMBRE_ARCHIVO;
    private final String e = FacturaDaoNio.NOMBRE_ARCHIVO;
    private final String f = FacturaTaskDaoNio.NOMBRE_ARCHIVO;
    private final String g = SolicitudeDaoNio.NOMBRE_ARCHIVO;
    private final String h = SolicitudTaskDaoNio.NOMBRE_ARCHIVO;
    private final String i = TaskDaoNio.NOMBRE_ARCHIVO;
    private final String j = TaskEquipoUserDaoNio.NOMBRE_ARCHIVO;
    private final String k = ClienteDaoNio.NOMBRE_ARCHIVO;

    public List<String> getAllFiles() {
        List<String> files = new ArrayList<>() {{
            add(a);
            add(b);
            add(c);
            add(d);
            add(e);
            add(f);
            add(g);
            add(h);
            add(i);
            add(j);
            add(k);
        }};
        return files;
    }

    public String execute(String file) {
        String response = "";
        switch (file) {
            case a:
                response = ComponentDaoNio.indice.printTree();
                break;
            case b:
                response =EmpleadoDaoNio.indice.printTree();
                break;
            case c:
                response =EquipoComponentDaoNio.indice.printTree();
                break;
            case d:
                response =EquipoDaoNio.indice.printTree();
                break;
            case e:
                response =FacturaDaoNio.indice.printTree();
                break;
            case f:
                response =FacturaTaskDaoNio.indice.printTree();
                break;
            case g:
                response =SolicitudeDaoNio.indice.printTree();
                break;
            case h:
                response =SolicitudTaskDaoNio.indice.printTree();
                break;
            case i:
                response =TaskDaoNio.indice.printTree();
                break;
            case j:
                response =TaskEquipoUserDaoNio.indice.printTree();
                break;
            case k:
                response =ClienteDaoNio.indice.printTree();
                break;
        }
        return response;
    }
}
