package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.TaskEquipoUserDao;
import co.edu.udea.edatos.ejemplo.model.Task;
import co.edu.udea.edatos.ejemplo.model.TaskEquipoUser;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.APPEND;

public class TaskEquipoUserDaoNio implements TaskEquipoUserDao {

    private final static int LONGITUD_REGISTRO = 50;
    private final static int LONGITUD_ID = 10;

    public final static String NOMBRE_ARCHIVO = "tareas_equipos_users";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public static final RedBlackTree indice = new RedBlackTree();
    private static int direccion = 0;

    public TaskEquipoUserDaoNio() {
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

                TaskEquipoUser teu = parseRegistro(registro);
                TaskEquipoUser toSave = new TaskEquipoUser();
                toSave.setId(teu.getId());
                toSave.setDirection(direccion++);

                System.out.println(String.format("%s -> %s", teu.getId(), direccion));
               indice.insert(toSave);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public TaskEquipoUser create(TaskEquipoUser task) {
        String registroDireccion = parseTask(task);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        TaskEquipoUser toInsert = new TaskEquipoUser();
        toInsert.setId(task.getId());
        toInsert.setDirection(direccion++);
        indice.insert(toInsert);
        return task;
    }

    @Override
    public Optional<TaskEquipoUser> read(int id) {
        TaskEquipoUser search = new TaskEquipoUser();
        search.setId(id);
        TaskEquipoUser find = (TaskEquipoUser) indice.find(search);

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
            TaskEquipoUser usuario = parseRegistro(registro);
            buffer.flip();
            return Optional.of(usuario);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void update(TaskEquipoUser cliente) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                TaskEquipoUser dbClient = parseRegistro(registro);
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
    public List<TaskEquipoUser> findAll() {
        List<TaskEquipoUser> tasks = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                TaskEquipoUser task = parseRegistro(registro);
                tasks.add(task);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return tasks;
    }

    private static TaskEquipoUser parseRegistro(CharBuffer registro) {
        TaskEquipoUser task = new TaskEquipoUser();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setEquipoId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setTaskId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setEmpleadoId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        task.setClienteId(Integer.parseInt(identificacion));

        return task;
    }

    private String parseTask(TaskEquipoUser taskEquipoUser) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(taskEquipoUser.getId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(taskEquipoUser.getEquipoId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(taskEquipoUser.getTaskId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(taskEquipoUser.getEmpleadoId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(taskEquipoUser.getClienteId()), LONGITUD_ID));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }
}
