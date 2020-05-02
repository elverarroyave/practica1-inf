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

import co.edu.udea.edatos.ejemplo.dao.EquipoComponentDao;
import co.edu.udea.edatos.ejemplo.model.Empleado;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import co.edu.udea.edatos.ejemplo.model.EquipoComponent;
import co.edu.udea.edatos.ejemplo.util.RedBlackTree;

import static java.nio.file.StandardOpenOption.APPEND;

public class EquipoComponentDaoNio implements EquipoComponentDao {

    private final static int LONGITUD_REGISTRO = 30;
    private final static int LONGITUD_ID = 10;

    private final static String NOMBRE_ARCHIVO = "equipo_component";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    private final RedBlackTree indice = new RedBlackTree();
    private static int direccion = 0;

    public EquipoComponentDaoNio() {
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

                EquipoComponent equipoComponent = parseRegistro(registro);
                EquipoComponent toSave = new EquipoComponent();
                toSave.setId(equipoComponent.getComponentId());
                toSave.setDirection(direccion++);

                System.out.println(String.format("%s -> %s", equipoComponent.getId(), direccion));
                indice.insert(toSave);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public EquipoComponent create(EquipoComponent equipoComponent) {
        String registroDireccion = parseEquipoComponent(equipoComponent);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return equipoComponent;
    }

    @Override
    public void update(EquipoComponent equipoComponent) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                EquipoComponent dbClient = parseRegistro(registro);
                if (dbClient.getId() == equipoComponent.getId()) {
                    String clienteToUpdate = parseEquipoComponent(equipoComponent);
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
    public Optional<EquipoComponent> read(int id) {
        EquipoComponent search = new EquipoComponent();
        search.setId(id);
        EquipoComponent find = (EquipoComponent) indice.find(search);

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
            EquipoComponent usuario = parseRegistro(registro);
            buffer.flip();
            return Optional.of(usuario);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<EquipoComponent> findAll() {
        List<EquipoComponent> clientes = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                EquipoComponent cliente = parseRegistro(registro);
                clientes.add(cliente);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return clientes;
    }

    @Override
    public void destroy(int id) {

    }

    private EquipoComponent parseRegistro(CharBuffer registro) {
        EquipoComponent equipoComponent = new EquipoComponent();

        String id = registro.subSequence(0, LONGITUD_ID).toString().trim();
        equipoComponent.setId(Integer.parseInt(id));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String equipoId = registro.subSequence(0, LONGITUD_ID).toString().trim();
        equipoComponent.setEquipoId(Integer.parseInt(equipoId));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String componentId = registro.subSequence(0, LONGITUD_ID).toString().trim();
        equipoComponent.setComponentId(Integer.parseInt(componentId));

        return equipoComponent;
    }

    private String parseEquipoComponent(EquipoComponent equipoComponent) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(equipoComponent.getId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(equipoComponent.getEquipoId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(equipoComponent.getComponentId()), LONGITUD_ID));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }
}

