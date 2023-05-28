package com.example.storifymusic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;


public class UsuarioVistaController {

    HelloApplication aplicacion;
    private Usuario userName;
    private Cancion cancionSeleccionada;
    private ObservableList<Cancion> listaCancionesUsuario;


    @FXML
    private TableView<Cancion> tblCancionesUsuario;

    @FXML
    private TableColumn<Boolean,Cancion> colFavorito;

    @FXML
    private TableColumn<ImageView,Cancion> colImage;

    @FXML
    private TableColumn<Cancion,String> colTitulo;

    @FXML
    private TableColumn<String,Cancion> colArtista;

    @FXML
    private TableColumn<String,Cancion> colAlbum;

    @FXML
    private TableColumn<String,Cancion> colDuracion;

    @FXML
    private TableColumn<String,Cancion> colGenero;

    @FXML
    private AnchorPane AnchorPaneUsuario;


    public void setUserName(Usuario userName) {
        AnchorPaneUsuario.setStyle("-fx-background-color: #f6f0ef");
        this.userName = userName;
    }

    public void setAplicacion(HelloApplication aplicacion) {
        this.aplicacion = aplicacion;
        actualizarTabla();
    }

    @FXML
    public void initialize(){

    }

    public void actualizarTabla(){
        ObservableList<Artista> listaArtistas = FXCollections.observableArrayList();
        inOrderTraversal(aplicacion.getArtistas(), listaArtistas::addAll);

        listaCancionesUsuario = FXCollections.observableArrayList();

        for (Artista artista: listaArtistas
        ) {
            for (Cancion cancion: artista.getCancionesArtista()) {
                listaCancionesUsuario.add(cancion);
            }
        }

        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nombreCancion"));
        colArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("nombreAlbum"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tblCancionesUsuario.setItems(listaCancionesUsuario);
    }




    @FXML
    public void reproducir(ActionEvent actionEvent){
        String url = cancionSeleccionada.getURL();
        String ulrCurada = url.substring(32, url.length());
        System.out.println(ulrCurada);
        YoutubePlayer youtubePlayer = new YoutubePlayer(ulrCurada);
        Stage stage = new Stage();
        youtubePlayer.start(stage);
    }

    public void inOrderTraversal(ArbolBinario<Artista> arbol,Consumer<Artista> action) {
        inOrderTraversal(arbol.getRaiz(), action);
    }

    private void inOrderTraversal(NodoArbol<Artista> node, Consumer<Artista> action) {
        if (node != null) {
            inOrderTraversal(node.getIzquierdo(), action);
            action.accept(node.getElemento());
            inOrderTraversal(node.getDerecho(), action);
        }
    }


    public void getCancionOnClick(MouseEvent mouseEvent) {
        cancionSeleccionada = tblCancionesUsuario.getSelectionModel().getSelectedItem();

    }
}
