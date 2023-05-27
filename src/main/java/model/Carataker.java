package model;


import java.io.IOException;
import java.util.Stack;

public class Carataker {

    private Stack<Memento> historialDeshacer = new Stack<>();
    private Stack<Memento> historialRehacer = new Stack<>();

    public void guardarEstado(Reproductor reproductor) throws IOException, ClassNotFoundException {
        Memento memento = reproductor.crearMemento();
        historialDeshacer.push(memento);
        historialRehacer.clear();
    }

    public void deshacer(Reproductor reproductor) throws IOException, ClassNotFoundException {
        if (!historialDeshacer.isEmpty()) {
            Memento memento = historialDeshacer.pop();
            historialRehacer.push(reproductor.crearMemento());
            reproductor.restaurarDesdeMemento(memento.deepCopy());
            System.out.println("Deshaciendo la acción anterior.");
        } else {
            System.out.println("No hay más acciones para deshacer.");
        }
    }

    public void rehacer(Reproductor reproductor) throws IOException, ClassNotFoundException {
        if (!historialRehacer.isEmpty()) {
            Memento memento = historialRehacer.pop();
            historialDeshacer.push(reproductor.crearMemento());
            reproductor.restaurarDesdeMemento(memento.deepCopy());
            System.out.println("Rehaciendo la última acción.");
        } else {
            System.out.println("No hay más acciones para rehacer.");
        }
    }
}
