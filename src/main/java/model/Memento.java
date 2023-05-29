package model;

import java.io.*;
import java.util.HashMap;

/**
 * Clase que almacena una instancia del estado de la tabla de usuarios
 * @author Juliana
 * @author Juan
 */
public class Memento implements Serializable{
    HashMap<String, Usuario> tablaUsuarios;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase
     * @param tablaUsuarios
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Memento(HashMap<String, Usuario> tablaUsuarios) throws IOException, ClassNotFoundException {

        this.tablaUsuarios = new HashMap<>(tablaUsuarios);
        this.tablaUsuarios = deepCopy().tablaUsuarios;
    }

    /**
     * Realiza una copia del objeto
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public Memento deepCopy() throws  ClassNotFoundException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
        outputStrm.writeObject(this);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
        return (Memento) objInputStream.readObject();
    }

    public HashMap<String, Usuario> getTablaUsuarios() {
        return tablaUsuarios;
    }
}
