package co.edu.udea.edatos.ejemplo.dao.impl;

import co.edu.udea.edatos.ejemplo.dao.ClienteDao;
import co.edu.udea.edatos.ejemplo.model.Cliente;

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

import static java.nio.file.StandardOpenOption.APPEND;


public class ClienteDaoNio implements ClienteDao {

    private final static int LONGITUD_REGISTRO = 165;
    private final static int LONGITUD_ID = 10;
    private final static int LONGITUD_NOMBRE = 20;
    private final static int LONGITUD_DATE = 40;
    private final static int LONGITUD_EMAIL = 30;
    private final static int LONGITUD_ADRESS = 30;
    private final static int LONGITUD_NUMBER = 15;

    private final static String NOMBRE_ARCHIVO = "clientes";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);

    public ClienteDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardar(Cliente cliente) {
        String registroDireccion = parseCliente(cliente);
        byte[] datosRegistro = registroDireccion.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(ARCHIVO)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.defaultCharset().decode(buffer);
                Cliente cliente = parseRegistro(registro);
                clientes.add(cliente);
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return clientes;
    }


    private Cliente parseRegistro(CharBuffer registro) {
        Cliente cliente = new Cliente();

        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        cliente.setId(Integer.parseInt(identificacion));
        registro.position(LONGITUD_ID);
        registro = registro.slice();

        String nombre = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        cliente.setName(nombre);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();
        
        String apellido = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        cliente.setLastName(apellido);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();

        String born = registro.subSequence(0, LONGITUD_DATE).toString().trim();
        cliente.setDateBorn(born);
        registro.position(LONGITUD_DATE);
        registro = registro.slice();

        String email = registro.subSequence(0, LONGITUD_EMAIL).toString().trim();
        cliente.setEmail(email);
        registro.position(LONGITUD_EMAIL);
        registro = registro.slice();

        String adress = registro.subSequence(0, LONGITUD_ADRESS).toString().trim();
        cliente.setAdress(adress);
        registro.position(LONGITUD_ADRESS);
        registro = registro.slice();

        String number = registro.subSequence(0, LONGITUD_NUMBER).toString().trim();
        cliente.setNumberPhone(number);

        return cliente;
    }


    private String parseCliente(Cliente cliente) {
        StringBuilder sb = new StringBuilder();
        sb.append(ajustarCampo(String.valueOf(cliente.getId()), LONGITUD_ID))
                .append(ajustarCampo(cliente.getName(), LONGITUD_NOMBRE))
                .append(ajustarCampo(cliente.getLastName(), LONGITUD_NOMBRE))
                .append(ajustarCampo(cliente.getDateBorn(), LONGITUD_DATE))
                .append(ajustarCampo(cliente.getEmail(), LONGITUD_EMAIL))
                .append(ajustarCampo(cliente.getAdress(), LONGITUD_ADRESS))
                .append(ajustarCampo(cliente.getNumberPhone(), LONGITUD_NUMBER));
        return sb.toString();
    }

    private String ajustarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }

}