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

import co.edu.udea.edatos.ejemplo.dao.EquipoComponentDao;
import co.edu.udea.edatos.ejemplo.model.EquipoComponent;

import static java.nio.file.StandardOpenOption.APPEND;

public class EquipoComponentDaoNio implements EquipoComponentDao {

    private final static int LONGITUD_REGISTRO = 20;
    private final static int LONGITUD_ID = 10;

    private final static String NOMBRE_ARCHIVO = "equipo_component";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public EquipoComponentDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardar(EquipoComponent equipoComponent) {
        String registroDireccion = parseEquipoComponent(equipoComponent);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public List<EquipoComponent> getComponentByEquipoId(int id) {
        List<EquipoComponent> equipoComponents = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                EquipoComponent equipoComponent = parseRegistro(registro);
                if (equipoComponent.getEquipoId() == id) {
                    equipoComponents.add(equipoComponent);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return equipoComponents;
    }

    private EquipoComponent parseRegistro(CharBuffer registro) {
        EquipoComponent equipoComponent = new EquipoComponent();

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
        sb.append(ajustarCampo(String.valueOf(equipoComponent.getEquipoId()), LONGITUD_ID))
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

