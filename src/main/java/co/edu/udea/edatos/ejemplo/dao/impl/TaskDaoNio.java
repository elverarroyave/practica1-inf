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

import co.edu.udea.edatos.ejemplo.dao.TaskDao;
import co.edu.udea.edatos.ejemplo.model.Task;

import static java.nio.file.StandardOpenOption.APPEND;

public class TaskDaoNio implements TaskDao {

    private final static int LONGITUD_REGISTRO = 80;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_NAME = 20;
    private final static int LONGITUD_DESCRIPTION = 40;
    private final static int LONGITUD_PAYMENT = 10;

    private final static String NOMBRE_ARCHIVO = "tareas";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public TaskDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardar(Task task) {
        String registroDireccion = parseTask(task);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Task task = parseRegistro(registro);
                tasks.add(task);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task findById(int id) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Task task = parseRegistro(registro);
                if (task.getId() == id) {
                    return task;
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }


    private Task parseRegistro(CharBuffer registro) {
        Task task = new Task();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String name = registro.subSequence(0, LONGITUD_NAME).toString().trim();
        task.setName(name);
        registro.position(LONGITUD_NAME);
        registro = registro.slice();

        String description = registro.subSequence(0, LONGITUD_DESCRIPTION).toString().trim();
        task.setDescription(description);
        registro.position(LONGITUD_DESCRIPTION);
        registro = registro.slice();

        String payment = registro.subSequence(0, LONGITUD_PAYMENT).toString().trim();
        task.setPayment(Double.parseDouble(payment));
        registro.position(LONGITUD_PAYMENT);
        registro = registro.slice();

        return task;
    }


    private String parseTask(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(task.getId()), LONGITUD_ID))
                .append(ajustarCampo(task.getName(), LONGITUD_NAME))
                .append(ajustarCampo(task.getDescription(), LONGITUD_DESCRIPTION))
                .append(ajustarCampo(String.valueOf(task.getPayment()), LONGITUD_PAYMENT));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }
}