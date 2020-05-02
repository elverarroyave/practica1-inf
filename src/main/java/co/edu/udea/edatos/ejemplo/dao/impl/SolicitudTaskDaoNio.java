package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.SolicitudTaskDao;
import co.edu.udea.edatos.ejemplo.model.SolicitudTask;
<<<<<<< Updated upstream
=======
import co.edu.udea.edatos.ejemplo.util.RedBlackTree;
>>>>>>> Stashed changes

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

public class SolicitudTaskDaoNio implements SolicitudTaskDao {

    private final static int LONGITUD_REGISTRO = 30;
    private final static int LONGITUD_ID = 10;

    private final static String NOMBRE_ARCHIVO = "tareas_solicitud";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

<<<<<<< Updated upstream
=======
    private final RedBlackTree indice = new RedBlackTree();
    private static int direccion = 0;

>>>>>>> Stashed changes
    public SolicitudTaskDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
<<<<<<< Updated upstream
=======
        crearIndice();
    }

    private void crearIndice() {
        System.out.println("Creando índice");
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                SolicitudTask solicitudTask = parseRegistro(registro);
                System.out.println(String.format("%s -> %s", solicitudTask.getId(), direccion));
                indice.insert(solicitudTask);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
>>>>>>> Stashed changes
    }

    @Override
    public SolicitudTask create(SolicitudTask task) {
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
    public Optional<SolicitudTask> read(int id) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                SolicitudTask task = parseRegistro(registro);
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
    public void update(SolicitudTask cliente) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                SolicitudTask dbClient = parseRegistro(registro);
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
    public List<SolicitudTask> findAll() {
        List<SolicitudTask> tasks = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                SolicitudTask task = parseRegistro(registro);
                tasks.add(task);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return tasks;
    }

    private SolicitudTask parseRegistro(CharBuffer registro) {
        SolicitudTask task = new SolicitudTask();

        String id = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setId(Integer.parseInt(id));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        id = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setSolicitudId(Integer.parseInt(id));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        id = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setTaskId(Integer.parseInt(id));

        return task;
    }

    private String parseTask(SolicitudTask taskEquipoUser) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(taskEquipoUser.getId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(taskEquipoUser.getSolicitudId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(taskEquipoUser.getTaskId()), LONGITUD_ID));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }
}
