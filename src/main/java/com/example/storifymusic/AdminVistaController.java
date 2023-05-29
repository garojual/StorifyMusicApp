package com.example.storifymusic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ArbolBinario;
import model.Artista;
import model.NodoArbol;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.function.Consumer;

/**
 * Controlador que se encarga de la vista del admin
 * @author Juliana
 * @author Juan
 */
public class AdminVistaController {
    @FXML
    public TableColumn<Artista, String> columnaNombre;
    public TableColumn<Artista, String> columnaCodigo;
    private Artista artistaSeleccionado;
    @FXML
    private TableView tablaArtistas;
    HelloApplication aplicacion;

    private ObservableList<Artista> listaArtistas;

    /**
     * Establece una instancia de la aplicacion
     * @param aplicacion
     */
    public void setAplicacion(HelloApplication aplicacion) {
        this.aplicacion = aplicacion;
        actualizarTabla();
    }

    /**
     * Se encarga de actualizar la tabla de los artistas agregados
     */
    @FXML
    public void actualizarTabla(){
        listaArtistas = FXCollections.observableArrayList();
        inOrderTraversal(aplicacion.getArtistas(), artista -> listaArtistas.addAll(artista));

        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tablaArtistas.setItems(listaArtistas);
    }

    /**
     * Crea un artista en la interfaz
     * @param event
     */
    @FXML
    void crearArtista(ActionEvent event) {
        aplicacion.showArtista();

    }

    /**
     * funcionalidad para volver a la ventana de inicio
     * @param event
     * @throws IOException
     */
    @FXML
    void devolver (ActionEvent event) throws IOException {
        aplicacion.devolverLogin();
    }

    /**
     * Agrega una nueva cancion al artista
     * @param event
     */
    public void agregarCancion(ActionEvent event) {
        if(artistaSeleccionado == null){
            System.out.println("Seleccionar un artista");
        }
        else{
            System.out.println("correcto");
            aplicacion.showCrearCancion(artistaSeleccionado);
        }
    }

    public void inOrderTraversal(ArbolBinario<Artista> arbol, Consumer<Artista> action) {
        inOrderTraversal(arbol.getRaiz(), action);
    }

    /**
     * realiza el recorrido in orden
     * @param node
     * @param action
     */
    private void inOrderTraversal(NodoArbol<Artista> node, Consumer<Artista> action) {
        if (node != null) {
            inOrderTraversal(node.getIzquierdo(), action);
            action.accept(node.getElemento());
            inOrderTraversal(node.getDerecho(), action);
        }
    }

    public void getDatoTabla(MouseEvent mouseEvent) {
        artistaSeleccionado = (Artista) tablaArtistas.getSelectionModel().getSelectedItem();
    }
}
