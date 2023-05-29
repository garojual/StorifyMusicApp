package model;

/**
 * estructura propia pila generica
 * @author Juliana
 * @author Juan
 * @param <T>
 */
public class Pila<T> extends ListaSimple{
    private NodoLista<T> cima;
    private int tamano;

    /**
     * Verifica si la Pila está vacía
     * @return true si está vacía
     */
    public boolean estaVacia() {
        return cima==null;
    }

    /**
     * Inserta un elemento en la Pila - push
     * @param dato elemento a guardar en la pila
     */
    public void push(T dato) {

        NodoLista<T> nodo = new NodoLista<>(dato);
        nodo.setSiguiente(cima);
        cima = nodo;

        tamano++;
    }

    /**
     * Retorna y elimina el elemento que está en la cima de la Pila - pop
     * @return Elemento de la cima
     */
    public T pop() {

        if(estaVacia()) {
            throw new RuntimeException("La Pila está vacía");
        }

        T dato = cima.getDato();
        cima = cima.getSiguiente();
        tamano--;

        return dato;
    }

    /**
     * Borra completamente la Pila
     */
    public void borrarPila() {
        cima = null;
        tamano = 0;
    }

    /**
     * @return the cima - peek
     */
    public T obtenerCima() {
        return cima.getDato();
    }

    /**
     * @return the cima - peek
     */
    public NodoLista<T> getCima() {
        return cima;
    }

    /**
     * @return the tamano
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * Agrega una Pila nueva
     * @param pila nueva Pila
     */
    public void agregar(Pila<T> pila) {

        Pila<T> clon = pila.clone();
        Pila<T> aux = new Pila<>();

        while(!clon.estaVacia()) {
            aux.push(clon.pop());
        }

        while(!aux.estaVacia()) {
            push(aux.pop());
        }

    }



    @Override
    protected Pila<T> clone(){

        Pila<T> pilaFinal = new Pila<>();
        NodoLista<T> nodoCima = null;

        for (NodoLista<T> aux = cima; aux!=null; aux = aux.getSiguiente()) {

            NodoLista<T> nuevo = new NodoLista<>( aux.getDato() );

            if(pilaFinal.estaVacia()) {
                pilaFinal.cima = nuevo;
                nodoCima = nuevo;
            }else {
                pilaFinal.cima.setSiguiente(nuevo);
                pilaFinal.cima = nuevo;
            }
            pilaFinal.tamano++;

        }

        pilaFinal.cima = nodoCima;

        return pilaFinal;
    }
}
