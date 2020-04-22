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
import java.util.Optional;

import co.edu.udea.edatos.ejemplo.dao.ComponentDao;
import co.edu.udea.edatos.ejemplo.model.Component;

import static java.nio.file.StandardOpenOption.APPEND;

public class ComponentDaoNio implements ComponentDao {

    private final static int LONGITUD_REGISTRO = 70;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_PRODUCER = 20;
    private final static int LONGITUD_DESCRIPTION = 40;

    private final static String NOMBRE_ARCHIVO = "components";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public ComponentDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void update(Component component) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Component dbClient = parseRegistro(registro);
                if (dbClient.getId() == component.getId()) {
                    String clienteToUpdate = parseComponent(component);
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
    public Component create(Component component) {
        String registroDireccion = parseComponent(component);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return component;
    }

    @Override
    public List<Component> findAll() {
        List<Component> components = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Component component = parseRegistro(registro);
                components.add(component);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return components;
    }

    @Override
    public Optional<Component> read(int id) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Component component = parseRegistro(registro);
                if (component.getId() == id) {
                    return Optional.of(component);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void destroy(int id) {

    }

    private Component parseRegistro(CharBuffer registro) {
        Component component = new Component();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        component.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String producer = registro.subSequence(0, LONGITUD_PRODUCER).toString().trim();
        component.setProducer(producer);
        registro.position(LONGITUD_PRODUCER);
        registro = registro.slice();

        String description = registro.subSequence(0, LONGITUD_DESCRIPTION).toString().trim();
        component.setDescription(description);

        return component;
    }

    private String parseComponent(Component component) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(component.getId()), LONGITUD_ID))
                .append(ajustarCampo(component.getProducer(), LONGITUD_PRODUCER))
                .append(ajustarCampo(component.getDescription(), LONGITUD_DESCRIPTION));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }
}

