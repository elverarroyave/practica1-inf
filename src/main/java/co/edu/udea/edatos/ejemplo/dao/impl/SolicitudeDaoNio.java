package co.edu.udea.edatos.ejemplo.dao.impl;


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
import java.util.Objects;
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.dao.SolicitudDao;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import co.edu.udea.edatos.ejemplo.model.FacturaTask;
import co.edu.udea.edatos.ejemplo.model.Solicitud;
import co.edu.udea.edatos.ejemplo.util.RedBlackTree;

import static java.nio.file.StandardOpenOption.APPEND;

public class SolicitudeDaoNio implements SolicitudDao {

    private final static int LONGITUD_REGISTRO = 60;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_STATE = 10;
    private final static int LONGITUD_RECEPTION_DATE = 20;
    private final static int LONGITUD_EQUIPO_ID = 10;
    private final static int LONGITUD_CLIENTE_ID = 10;

    private final static String NOMBRE_ARCHIVO = "solicitudes";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    private static final RedBlackTree indice = new RedBlackTree();
    private static int direccion = 0;

    public SolicitudeDaoNio() {
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

                Solicitud solicitud = parseRegistro(registro);
                Solicitud toSave = new Solicitud();
                toSave.setId(solicitud.getId());
                toSave.setDirection(direccion++);

                System.out.println(String.format("%s -> %s", solicitud.getId(), direccion));
                indice.insert(toSave);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Solicitud create(Solicitud solicitud) {
        String registroDireccion = parseSolicitud(solicitud);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Solicitud toInsert = new Solicitud();
        toInsert.setId(solicitud.getId());
        toInsert.setDirection(direccion++);
        indice.insert(toInsert);
        return solicitud;
    }

    @Override
    public Optional<Solicitud> read(int id) {
        Solicitud search = new Solicitud();
        search.setId(id);
        Solicitud find = (Solicitud) indice.find(search);

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
            Solicitud usuario = parseRegistro(registro);
            buffer.flip();
            return Optional.of(usuario);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void update(Solicitud cliente) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Solicitud dbClient = parseRegistro(registro);
                if (dbClient.getId() == cliente.getId()) {
                    String clienteToUpdate = parseSolicitud(cliente);
                    byte[] by = clienteToUpdate.getBytes();
                    buffer.put(by);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void destroy(int id) {

    }

    @Override
    public List<Solicitud> findAll() {
        List<Solicitud> clientes = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Solicitud cliente = parseRegistro(registro);
                clientes.add(cliente);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return clientes;
    }


    private static Solicitud parseRegistro(CharBuffer registro) {
        Solicitud solicitud = new Solicitud();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        solicitud.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String state = registro.subSequence(0, LONGITUD_STATE).toString().trim();
        solicitud.setState(Boolean.valueOf(state));
        registro.position(LONGITUD_STATE);
        registro = registro.slice();

        String reception = registro.subSequence(0, LONGITUD_RECEPTION_DATE).toString().trim();
        solicitud.setReceptionDate(reception);
        registro.position(LONGITUD_RECEPTION_DATE);
        registro = registro.slice();

        String idEquipo = registro.subSequence(0, LONGITUD_ID).toString().trim();
        solicitud.setIdEquipo(Integer.parseInt(idEquipo));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String idCliente = registro.subSequence(0, LONGITUD_ID).toString().trim();
        solicitud.setIdClienteOwner(Integer.parseInt(idCliente));

        return solicitud;
    }

    private String parseSolicitud(Solicitud solicitud) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(solicitud.getId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(solicitud.getState()), LONGITUD_STATE))
                .append(ajustarCampo(solicitud.getReceptionDate(), LONGITUD_RECEPTION_DATE))
                .append(ajustarCampo(String.valueOf(solicitud.getIdEquipo()), LONGITUD_EQUIPO_ID))
                .append(ajustarCampo(String.valueOf(solicitud.getIdClienteOwner()), LONGITUD_CLIENTE_ID));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }
}

