package model;


import java.io.IOException;
import java.io.Serializable;
import java.util.Stack;

/**
 * Clase que se encarga de registrar los cambios
 */
public class Carataker implements Serializable {

    private Pila<Memento> historialDeshacer = new Pila<>();
    private Pila<Memento> historialRehacer = new Pila<>();

    /**
     * Guarda el estado actual del reproductor en un objeto
     * Luego, el objeto Memento se coloca en una pila llamada historialDeshacer y se limpia la pila historialRehacer
     * @param reproductor
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void guardarEstado(Reproductor reproductor) throws IOException, ClassNotFoundException {
        Memento memento = reproductor.crearMemento();
        historialDeshacer.push(memento);
        historialRehacer.borrarPila();
    }

    /**
     * Deshace la última acción realizada en el reproductor.
     * @param reproductor
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void deshacer(Reproductor reproductor) throws IOException, ClassNotFoundException {
        if (!historialDeshacer.estaVacia()) {
            Memento memento = historialDeshacer.pop();
            historialRehacer.push(reproductor.crearMemento());
            reproductor.restaurarDesdeMemento(memento.deepCopy());
            System.out.println("Deshaciendo la acción anterior.");
        } else {
            System.out.println("No hay más acciones para deshacer.");
        }
    }

    /**
     * Rehace la última acción deshecha en el reproductor
     * @param reproductor
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public void rehacer(Reproductor reproductor) throws IOException, ClassNotFoundException {
        if (!historialRehacer.estaVacia()) {
            Memento memento = historialRehacer.pop();
            historialDeshacer.push(reproductor.crearMemento());
            reproductor.restaurarDesdeMemento(memento.deepCopy());
            System.out.println("Rehaciendo la última acción.");
        } else {
            System.out.println("No hay más acciones para rehacer.");
        }
    }
}
