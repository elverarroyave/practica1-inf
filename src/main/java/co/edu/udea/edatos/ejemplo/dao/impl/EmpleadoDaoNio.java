package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.EmpleadoDao;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import co.edu.udea.edatos.ejemplo.model.Component;
import co.edu.udea.edatos.ejemplo.model.Empleado;
import co.edu.udea.edatos.ejemplo.util.RedBlackTree;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.READ;


public class EmpleadoDaoNio implements EmpleadoDao {

    private final static int LONGITUD_REGISTRO = 205;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_NOMBRE = 20;
    private final static int LONGITUD_PASSWORD = 20;
    private final static int LONGITUD_DATE = 40;
    private final static int LONGITUD_EMAIL = 30;
    private final static int LONGITUD_ADRESS = 30;
    private final static int LONGITUD_NUMBER = 15;

    public final static String NOMBRE_ARCHIVO = "empleados";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public static final RedBlackTree indice = new RedBlackTree();
    private static int direccion = 0;

    public EmpleadoDaoNio() {
    }

    public static void crearIndice() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        System.out.println("Creando índice");
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);

                Empleado empleado = parseRegistro(registro);
                Empleado toSave = new Empleado();
                toSave.setId(empleado.getId());
                toSave.setDirection(direccion++);

                System.out.println(String.format("%s -> %s", empleado.getId(), direccion));
                indice.insert(toSave);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Empleado create(Empleado empleado) {
        String registroDireccion = parseEmpleado(empleado);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Empleado toInsert = new Empleado();
        toInsert.setId(empleado.getId());
        toInsert.setDirection(direccion++);
        indice.insert(toInsert);
        return empleado;
    }

    @Override
    public void update(Empleado empleado) {
        try (FileChannel sbc = FileChannel.open(ARCHIVO, APPEND, READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Empleado dbClient = parseRegistro(registro);
                if (dbClient.getId() == empleado.getId()) {
                    String clienteToUpdate = parseEmpleado(empleado);
                    byte[] by = clienteToUpdate.getBytes();
                    buffer.wrap(by);
                    sbc.write(buffer);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public List<Empleado> findAll() {
        List<Empleado> empleados = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Empleado empleado = parseRegistro(registro);
                empleados.add(empleado);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return empleados;
    }

    @Override
    public Optional<Empleado> read(int id) {
        Empleado search = new Empleado();
        search.setId(id);
        Empleado find = (Empleado) indice.find(search);

        if (Objects.isNull(find)) {
            System.out.println("El usuario no se encontró en el índice, por ende no existe en el archivo");
            return Optional.empty();
        }

        Integer direccionRegistro = find.getDirection();
        System.out.println("El usuario fue encontrado en el índice y se va a la dirección: " + direccionRegistro);

        System.out.println("(" + direccionRegistro * LONGITUD_REGISTRO + ")");
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            sbc.position(direccionRegistro * LONGITUD_REGISTRO);
            sbc.read(buffer);
            buffer.rewind();
            CharBuffer registro = Charset.defaultCharset().decode(buffer);
            Empleado usuario = parseRegistro(registro);
            buffer.flip();
            return Optional.of(usuario);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void destroy(int id) {

    }
    private static Empleado parseRegistro(CharBuffer registro) {
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
        empleado.setDateBorn(born);
        registro.position(LONGITUD_DATE);
        registro = registro.slice();

        String email = registro.subSequence(0, LONGITUD_EMAIL).toString().trim();
        empleado.setEmail(email);
        registro.position(LONGITUD_EMAIL);
        registro = registro.slice();

        String adress = registro.subSequence(0, LONGITUD_ADRESS).toString().trim();
        empleado.setAdress(adress);
        registro.position(LONGITUD_ADRESS);
        registro = registro.slice();

        String number = registro.subSequence(0, LONGITUD_NUMBER).toString().trim();
        empleado.setNumberPhone(number);

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
