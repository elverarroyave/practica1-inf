package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.EmpleadoDao;
import co.edu.udea.edatos.ejemplo.model.Empleado;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;


public class EmpleadoDaoNio implements EmpleadoDao {

    private final static int LONGITUD_REGISTRO = 165;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_NOMBRE = 20;
    private final static int LONGITUD_PASSWORD = 20;
    private final static int LONGITUD_DATE = 40;
    private final static int LONGITUD_EMAIL = 30;
    private final static int LONGITUD_ADRESS = 30;
    private final static int LONGITUD_NUMBER = 15;

    private final static String NOMBRE_ARCHIVO = "empleados";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public EmpleadoDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardar(Empleado empleado) {
        String registroDireccion = parseEmpleado(empleado);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<Empleado> findAll() {
        List<Empleado> empleados = new ArrayList();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Empleado empleado = parseRegistro(registro);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return empleados;
    }


    private Empleado parseRegistro(CharBuffer registro) {
        Empleado empleado = new Empleado();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        empleado.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String nombre = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        empleado.setName(nombre);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();
        
        String apellido = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        empleado.setLastName(apellido);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();

        String username = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        empleado.setUser(username);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();

        String password = registro.subSequence(0, LONGITUD_PASSWORD).toString().trim();
        empleado.setPassword(password);
        registro.position(LONGITUD_PASSWORD);
        registro = registro.slice();

        String born = registro.subSequence(0, LONGITUD_DATE).toString().trim();
        empleado.setPassword(born);
        registro.position(LONGITUD_DATE);
        registro = registro.slice();

        String email = registro.subSequence(0, LONGITUD_EMAIL).toString().trim();
        empleado.setPassword(email);
        registro.position(LONGITUD_EMAIL);
        registro = registro.slice();

        String adress = registro.subSequence(0, LONGITUD_ADRESS).toString().trim();
        empleado.setPassword(adress);
        registro.position(LONGITUD_ADRESS);
        registro = registro.slice();

        String number = registro.subSequence(0, LONGITUD_NUMBER).toString().trim();
        empleado.setPassword(number);

        return empleado;
    }


    private String parseEmpleado(Empleado empleado) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(empleado.getId()), LONGITUD_ID))
                .append(ajustarCampo(empleado.getName(), LONGITUD_NOMBRE))
                .append(ajustarCampo(empleado.getLastName(), LONGITUD_NOMBRE))
                .append(ajustarCampo(empleado.getUser(), LONGITUD_NOMBRE))
                .append(ajustarCampo(empleado.getPassword(), LONGITUD_PASSWORD))
                .append(ajustarCampo(empleado.getDateBorn(), LONGITUD_DATE))
                .append(ajustarCampo(empleado.getEmail(), LONGITUD_EMAIL))
                .append(ajustarCampo(empleado.getAdress(), LONGITUD_ADRESS))
                .append(ajustarCampo(empleado.getNumberPhone(), LONGITUD_NUMBER));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }

}
