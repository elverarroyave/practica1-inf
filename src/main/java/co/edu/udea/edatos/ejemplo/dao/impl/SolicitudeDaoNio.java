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

import co.edu.udea.edatos.ejemplo.dao.SolicitudDao;
import co.edu.udea.edatos.ejemplo.model.Solicitud;

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

    public SolicitudeDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardar(Solicitud solicitud) {
        String registroDireccion = parseSolicitud(solicitud);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    @Override
    public List<Solicitud> getSolicitudesByIncompleteState() {
        List<Solicitud> solicitudes = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Solicitud solicitud = parseRegistro(registro);
                if (!solicitud.getState()) {
                    solicitudes.add(solicitud);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return solicitudes;
    }

    @Override
    public List<Solicitud> getSolicitudesByCompleteState() {
        List<Solicitud> solicitudes = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Solicitud solicitud = parseRegistro(registro);
                if (solicitud.getState()) {
                    solicitudes.add(solicitud);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return solicitudes;
    }

    @Override
    public List<Solicitud> getSolicitudesByUserId(int userId) {
        List<Solicitud> solicituds = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Solicitud solicitud = parseRegistro(registro);
                if (solicitud.getIdClienteOwner() == userId) {
                    solicituds.add(solicitud);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return solicituds;
    }

    @Override
    public List<Solicitud> getSolicitudesByEquipoId(int equipoId) {
        List<Solicitud> solicituds = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Solicitud solicitud = parseRegistro(registro);
                if (solicitud.getIdEquipo() == equipoId) {
                    solicituds.add(solicitud);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return solicituds;
    }

    private Solicitud parseRegistro(CharBuffer registro) {
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
        solicitud.setId(Integer.parseInt(idCliente));

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

