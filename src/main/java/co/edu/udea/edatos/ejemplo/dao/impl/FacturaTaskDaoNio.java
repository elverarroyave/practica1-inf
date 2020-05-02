package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.FacturaTaskDao;
import co.edu.udea.edatos.ejemplo.model.FacturaTask;

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

import static java.nio.file.StandardOpenOption.APPEND;

public class FacturaTaskDaoNio implements FacturaTaskDao {

    private final static int LONGITUD_REGISTRO = 30;
    private final static int LONGITUD_ID = 10;

    private final static String NOMBRE_ARCHIVO = "factura_tareas";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public FacturaTaskDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public FacturaTask create(FacturaTask task) {
        String registroDireccion = parseTask(task);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return task;
    }

    @Override
    public Optional<FacturaTask> read(int id) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                FacturaTask task = parseRegistro(registro);
                if (task.getId() == id) {
                    return Optional.of(task);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(FacturaTask cliente) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                FacturaTask dbClient = parseRegistro(registro);
                if (dbClient.getId() == cliente.getId()) {
                    String clienteToUpdate = parseTask(cliente);
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
    public List<FacturaTask> findAll() {
        List<FacturaTask> tasks = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                FacturaTask task = parseRegistro(registro);
                tasks.add(task);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return tasks;
    }

    private FacturaTask parseRegistro(CharBuffer registro) {
        FacturaTask task = new FacturaTask();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setFacturaId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setTaskId(Integer.parseInt(identificacion));

        return task;
    }

    private String parseTask(FacturaTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(task.getId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(task.getFacturaId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(task.getTaskId()), LONGITUD_ID));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }
}