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

import co.edu.udea.edatos.ejemplo.dao.EquipoDao;
import co.edu.udea.edatos.ejemplo.model.Equipo;

import static java.nio.file.StandardOpenOption.APPEND;

public class EquipoDaoNio implements EquipoDao {

    private final static int LONGITUD_REGISTRO = 80;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_BRAND = 20;
    private final static int LONGITUD_MODEL = 40;

    private final static String NOMBRE_ARCHIVO = "equipos";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public EquipoDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardar(Equipo equipo) {
        String registroDireccion = parseEquipo(equipo);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<Equipo> getEquipoByUserId(int userId) {
        List<Equipo> equipos = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Equipo equipo = parseRegistro(registro);
                if (equipo.getPcOwner() == userId) {
                    equipos.add(equipo);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return equipos;
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
    public Equipo getEquipoById(int id) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Equipo equipo = parseRegistro(registro);
                if (equipo.getId() == id) {
                    return equipo;
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
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
