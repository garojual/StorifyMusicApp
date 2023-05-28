package com.example.storifymusic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ArbolBinario;
import model.Artista;
import model.NodoArbol;

import java.io.IOException;
import java.util.function.Consumer;

public class AdminVistaController {
    @FXML
    public TableColumn<Artista, String> columnaNombre;
    public TableColumn<Artista, String> columnaCodigo;
    @FXML
    private TableView tablaArtistas;
    HelloApplication aplicacion;

    private ObservableList<Artista> listaArtistas;



    public void setAplicacion(HelloApplication aplicacion) {
        this.aplicacion = aplicacion;
        actualizarTabla();
    }

    @FXML
    public void actualizarTabla(){
        listaArtistas = FXCollections.observableArrayList();
        inOrderTraversal(aplicacion.getArtistas(), artista -> listaArtistas.addAll(artista));

        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tablaArtistas.setItems(listaArtistas);
    }

    @FXML
    void crearArtista(ActionEvent event) {
        aplicacion.showArtista();

    }

    @FXML
    void devolver (ActionEvent event) throws IOException {
        aplicacion.devolverLogin();
    }

    public void agregarCancion(ActionEvent event) {
    }

    public void inOrderTraversal(ArbolBinario<Artista> arbol, Consumer<Artista> action) {
        inOrderTraversal(arbol.getRaiz(), action);
    }

    private void inOrderTraversal(NodoArbol<Artista> node, Consumer<Artista> action) {
        if (node != null) {
            inOrderTraversal(node.getIzquierdo(), action);
            action.accept(node.getElemento());
            inOrderTraversal(node.getDerecho(), action);
        }
    }
}
