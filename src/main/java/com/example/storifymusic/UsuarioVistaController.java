package com.example.storifymusic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.function.Consumer;


public class UsuarioVistaController {

    HelloApplication aplicacion;
    private Usuario userName;
    private Cancion cancionSeleccionada;
    private Cancion cancionSeleccionadaUsuario;
    private ObservableList<Cancion> listaCancionesUsuario = FXCollections.observableArrayList();
    private ObservableList<Cancion> listaCanciones = FXCollections.observableArrayList();


    @FXML
    private TableView<Cancion> tblCanciones;


    @FXML
    private TableColumn<ImageView,Cancion> colImage;

    @FXML
    private TableColumn<Cancion,String> colTitulo;

    @FXML
    private TableColumn<String,Cancion> colArtista;

    @FXML
    private TableColumn<String,Cancion> colAlbum;

    @FXML
    private TableColumn<String,Cancion> colGenero;

    @FXML
    private AnchorPane AnchorPaneUsuario;

    @FXML
    private TableView<Cancion> tblCancionesUsuario;

    @FXML
    private TableColumn<String,Cancion> favoritos;

    @FXML
    private Label lblUsuario ;

    @FXML
    private TextField buscar;




    public void setUserName(Usuario userName) {
        this.userName = userName;
        AnchorPaneUsuario.setStyle("-fx-background-color: #f6f0ef");
    }

    public void setAplicacion(HelloApplication aplicacion) {
        this.aplicacion = aplicacion;
        lblUsuario.setText(userName.getUserName());
        actualizarTabla();
        actualizarTablaFavoritos();



    }

    @FXML
    public void initialize() {
    }

    public void actualizarTabla(){
        ObservableList<Artista> listaArtistas = FXCollections.observableArrayList();
        inOrderTraversal(aplicacion.getArtistas(), listaArtistas::addAll);

        for (Artista artista: listaArtistas
        ) {
            for (Cancion cancion: artista.getCancionesArtista()) {
                listaCanciones.add(cancion);
            }
        }

        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nombreCancion"));
        colArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("nombreAlbum"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tblCanciones.setItems(listaCanciones);
    }

    public void actualizarTablaFavoritos(){
        tblCancionesUsuario.getItems().clear();
        ListaDobleCircular<Cancion> cancionesUsuario = userName.getListaCanciones();
        agregarCancionesMias(cancionesUsuario);
        tblCancionesUsuario.setItems(listaCancionesUsuario);
    }

    @FXML
    public void actualizarFavoritos(ActionEvent event) throws IOException, ClassNotFoundException {
        if (cancionSeleccionada != null) {
            aplicacion.agregarCancionListaUser(userName, cancionSeleccionada);
            cancionSeleccionada=null;
            actualizarTablaMiLista();
        }
        favoritos.setCellValueFactory(new PropertyValueFactory<>("nombreCancion"));
        tblCancionesUsuario.setItems(listaCancionesUsuario);
    }

    private void actualizarTablaMiLista() {
        tblCanciones.getItems().clear();
        agregarCancionesMias(userName.getListaCanciones());
        tblCanciones.setItems(listaCancionesUsuario);
        tblCanciones.refresh();
    }



    public void agregarCancionesMias(ListaDobleCircular<Cancion> listaCanciones){
        listaCancionesUsuario.clear();
        NodoLista<Cancion> currentNode = listaCanciones.getNodoPrimero();

        if (currentNode !=null) {

            do {
                listaCancionesUsuario.add(currentNode.getDato());
                currentNode = currentNode.getSiguiente();
            } while (currentNode != listaCanciones.getNodoPrimero());
        }
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

    public void getCancionOnClick(MouseEvent mouseEvent) {
        cancionSeleccionada = tblCanciones.getSelectionModel().getSelectedItem();

    }

    public void getCancionUsuarioOnClick(MouseEvent mouseEvent) {
        cancionSeleccionadaUsuario = tblCancionesUsuario.getSelectionModel().getSelectedItem();

    }

    @FXML
    public void eliminarCancionUsuario(ActionEvent event) throws IOException, ClassNotFoundException {
        if (cancionSeleccionadaUsuario != null) {
            aplicacion.eliminarCancionUser(userName, cancionSeleccionadaUsuario);
            cancionSeleccionadaUsuario=null;
            actualizarTablaMiLista();
        } else {
            System.out.println("Ninguna cancion ha sido seleccionada");
        }
    }
    @FXML
    public void deshacer(ActionEvent event) throws IOException, ClassNotFoundException {
        aplicacion.deshacer();
        userName= aplicacion.reemplazarUsuario(userName);
        actualizarTablaMiLista();
    }

    @FXML
    public void rehacer(ActionEvent event) throws IOException, ClassNotFoundException {
        aplicacion.rehacer();
        userName= aplicacion.reemplazarUsuario(userName);
        actualizarTablaMiLista();
    }

    public void buscar(ActionEvent event){
        String cancion = buscar.getText();

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





}
