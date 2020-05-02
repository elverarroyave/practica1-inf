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

import co.edu.udea.edatos.ejemplo.dao.FacturaDao;
import co.edu.udea.edatos.ejemplo.model.Equipo;
import co.edu.udea.edatos.ejemplo.model.Factura;
import co.edu.udea.edatos.ejemplo.util.RedBlackTree;

import static java.nio.file.StandardOpenOption.APPEND;

public class FacturaDaoNio implements FacturaDao {

    private final static int LONGITUD_REGISTRO = 100;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_PAYMENT = 20;
    private final static int LONGITUD_DESCRIPTION = 20;
    private final static int LONGITUD_DATE = 40;

    private final static String NOMBRE_ARCHIVO = "facturas";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    private static final RedBlackTree indice = new RedBlackTree();
    private static int direccion = 0;

    public FacturaDaoNio() {
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

                Factura factura = parseRegistro(registro);
                Factura toSave = new Factura();
                toSave.setId(factura.getId());
                toSave.setDirection(direccion++);

                System.out.println(String.format("%s -> %s", factura.getId(), direccion));
                indice.insert(toSave);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Factura create(Factura factura) {
        String registroDireccion = parseFactura(factura);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Factura toInsert = new Factura();
        toInsert.setId(factura.getId());
        toInsert.setDirection(direccion++);
        indice.insert(toInsert);
        return factura;
    }

    @Override
    public Optional<Factura> read(int id) {
        Factura search = new Factura();
        search.setId(id);
        Factura find = (Factura) indice.find(search);

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
            Factura usuario = parseRegistro(registro);
            buffer.flip();
            return Optional.of(usuario);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void update(Factura cliente) {
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Factura dbClient = parseRegistro(registro);
                if (dbClient.getId() == cliente.getId()) {
                    String clienteToUpdate = parseFactura(cliente);
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
    public List<Factura> findAll() {
        List<Factura> clientes = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Factura cliente = parseRegistro(registro);
                clientes.add(cliente);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return clientes;
    }

    private static Factura parseRegistro(CharBuffer registro) {
        Factura factura = new Factura();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        factura.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String payment = registro.subSequence(0, LONGITUD_PAYMENT).toString().trim();
        factura.setPayment(Double.parseDouble(payment));
        registro.position(LONGITUD_PAYMENT);
        registro = registro.slice();
        
        String description = registro.subSequence(0, LONGITUD_DESCRIPTION).toString().trim();
        factura.setDescription(description);
        registro.position(LONGITUD_DESCRIPTION);
        registro = registro.slice();

        String solicitudeId = registro.subSequence(0, LONGITUD_ID).toString().trim();
        factura.setId(Integer.parseInt(solicitudeId));
       
        return factura;
    }

    private String parseFactura(Factura factura) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(factura.getId()), LONGITUD_ID))
                .append(ajustarCampo(String.valueOf(factura.getPayment()), LONGITUD_PAYMENT))
                .append(ajustarCampo(factura.getDescription(), LONGITUD_DESCRIPTION))
                .append(ajustarCampo(factura.getReturnDate(), LONGITUD_DATE))
                .append(ajustarCampo(String.valueOf(factura.getId()), LONGITUD_ID));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }

}