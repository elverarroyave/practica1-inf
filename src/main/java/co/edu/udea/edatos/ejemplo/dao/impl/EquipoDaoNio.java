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

import co.edu.udea.edatos.ejemplo.dao.EquipoDao;
import co.edu.udea.edatos.ejemplo.model.Cliente;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import co.edu.udea.edatos.ejemplo.model.EquipoComponent;
import co.edu.udea.edatos.ejemplo.util.RedBlackTree;

import static java.nio.file.StandardOpenOption.APPEND;

public class EquipoDaoNio implements EquipoDao {

    private final static int LONGITUD_REGISTRO = 80;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_BRAND = 20;
    private final static int LONGITUD_MODEL = 40;

    private final static String NOMBRE_ARCHIVO = "equipos";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    private final RedBlackTree indice = new RedBlackTree();
    private static int direccion = 0;

    public EquipoDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        crearIndice();
    }

    private void crearIndice() {
        System.out.println("Creando índice");
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);

                Equipo equipo = parseRegistro(registro);
                Equipo toSave = new Equipo();
                toSave.setId(equipo.getId());
                toSave.setDirection(direccion++);

                System.out.println(String.format("%s -> %s", equipo.getId(), direccion));
                indice.insert(toSave);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Equipo create(Equipo equipo) {
        String registroDireccion = parseEquipo(equipo);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return equipo;
    }

    @Override
    public void update(Equipo cliente) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Equipo dbClient = parseRegistro(registro);
                if (dbClient.getId() == cliente.getId()) {
                    String clienteToUpdate = parseEquipo(cliente);
                    byte[] by = clienteToUpdate.getBytes();
                    buffer.put(by);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<Equipo> findAll() {
        List<Equipo> equipos = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Equipo equipo = parseRegistro(registro);
                equipos.add(equipo);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return equipos;
    }

    @Override
    public Optional<Equipo> read(int id) {
        Equipo search = new Equipo();
        search.setId(id);
        Equipo find = (Equipo) indice.find(search);

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
            Equipo usuario = parseRegistro(registro);
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

    private Equipo parseRegistro(CharBuffer registro) {
        Equipo equipo = new Equipo();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        equipo.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String brand = registro.subSequence(0, LONGITUD_BRAND).toString().trim();
        equipo.setBrand(brand);
        registro.position(LONGITUD_BRAND);
        registro = registro.slice();

        String model = registro.subSequence(0, LONGITUD_MODEL).toString().trim();
        equipo.setModel(model);
        registro.position(LONGITUD_MODEL);
        registro = registro.slice();

        String owner = registro.subSequence(0, LONGITUD_ID).toString().trim();
        equipo.setPcOwner(Integer.parseInt(owner));
        return equipo;
    }


    private String parseEquipo(Equipo equipo) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(equipo.getId()), LONGITUD_ID))
                .append(ajustarCampo(equipo.getBrand(), LONGITUD_BRAND))
                .append(ajustarCampo(equipo.getModel(), LONGITUD_MODEL))
                .append(ajustarCampo(String.valueOf(equipo.getPcOwner()), LONGITUD_ID));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }
}
