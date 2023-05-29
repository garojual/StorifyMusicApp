package com.example.storifymusic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Controlador que se encarga de la vista del usuario
 * @author Juliana
 * @author Juan
 */
public class UsuarioVistaController {


    private HelloApplication aplicacion;
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

    @FXML
    private RadioButton gTitulo;

    @FXML
    private RadioButton gArtista;

    @FXML
    private RadioButton gGenero;

    @FXML
    private Label lblGeneroPopular;

    private ToggleGroup toggleGroup;


    /**
     * Asigna el nombre de usuario
     * @param userName
     */
    public void setUserName(Usuario userName) {
        this.userName = userName;
        AnchorPaneUsuario.setStyle("-fx-background-color: #f6f0ef");
    }

    /**
     * Establece una instancia de la aplicacion
     * Actualiza la vista
     * @param aplicacion
     */
    public void setAplicacion(HelloApplication aplicacion) {
        this.aplicacion = aplicacion;
        lblUsuario.setText(userName.getUserName());
        actualizarTabla();
        actualizarTablaFavoritos();

        toggleGroup = new ToggleGroup();
        gTitulo.setToggleGroup(toggleGroup);
        gArtista.setToggleGroup(toggleGroup);
        gGenero.setToggleGroup(toggleGroup);
        imprimirGeneroMasPopular();

    }

    @FXML
    public void initialize() {


    }

    /**
     * Actualiza la tabla general de las canciones
     */
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

    /**
     * Actualiza la tabla segun los criterios de busqueda, a traves de la modificacion de una ObservableList de canciones
     * que dictan cuales aparecen en la tabla, solamente agregando a la misma las que coincidan con lo buscado
     * @param criterio el criterio con el cual se quiere realizar la busqueda [Nombre de la cancion, Artista, Genero]
     * @param busqueda la palabra clave que se va a buscar en los criterios
     */
    public void actualizarSegunCriterio(String criterio, String busqueda){
        ObservableList<Artista> listaArtistas = FXCollections.observableArrayList();
        inOrderTraversal(aplicacion.getArtistas(), listaArtistas::addAll);
        switch(criterio){
            case "nombre":
                for (Artista artista: listaArtistas) {
                    for (Cancion cancion: artista.getCancionesArtista()) {
                        if(cancion.getNombreCancion().toLowerCase().contains(busqueda.toLowerCase())){
                            listaCanciones.add(cancion);
                        }
                    }
                }
                break;

            case "artista":
                for (Artista artista: listaArtistas) {
                    for (Cancion cancion: artista.getCancionesArtista()) {
                        if(cancion.getArtista().getNombre().toLowerCase().contains(busqueda.toLowerCase())){
                            listaCanciones.add(cancion);
                        }
                    }
                }
                break;

            case "genero":
                for (Artista artista: listaArtistas) {
                    for (Cancion cancion: artista.getCancionesArtista()) {
                        if(cancion.getGenero().toString().toLowerCase().contains(busqueda.toLowerCase())){
                            listaCanciones.add(cancion);
                        }
                    }
                }
                break;
        }

        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nombreCancion"));
        colArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("nombreAlbum"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tblCanciones.setItems(listaCanciones);

    }

    /**
     * Busca el genero mas popular a traves de la iteracion de los generos y su almacenamiento en un hashmap donde
     * si la clave ya existe, le suma uno a su valor asociado, y si no existe, agrega la clave con un valor
     * predeterminado de cero. Una vez agregados los datos, se itera sobre el hashmap y se comparan sus valores para
     * hallar el mayor, sacar su calve y agregarla al label en la interfaz.
     */
    public void imprimirGeneroMasPopular(){
        HashMap<String, Integer> recuentoGeneros = new HashMap<>();

        ArrayList<Cancion> listaCancionesAux = new ArrayList();
        ObservableList<Artista> listaArtistas = FXCollections.observableArrayList();
        inOrderTraversal(aplicacion.getArtistas(), listaArtistas::addAll);

        for (Artista artista: listaArtistas
        ) {
            for (Cancion cancion: artista.getCancionesArtista()) {
                listaCancionesAux.add(cancion);
            }
        }

        for(Cancion cancion: listaCancionesAux){
            String genero = cancion.getGenero().toString();
            recuentoGeneros.put(genero, recuentoGeneros.getOrDefault(genero, 0) + 1);
        }

        String generoMasRepetido = "";
        int maxRecuento = 0;

        for (Map.Entry<String, Integer> entry : recuentoGeneros.entrySet()) {
            if (entry.getValue() > maxRecuento) {
                maxRecuento = entry.getValue();
                generoMasRepetido = entry.getKey();
            }
        }
        //System.out.println("El género que más se repite es: " + generoMasRepetido);

        lblGeneroPopular.setText("Genero mas popular: " + generoMasRepetido);
    }

    /**
     * actualiza la tabla de las canciones del usuario
     */
    public void actualizarTablaFavoritos(){
        tblCancionesUsuario.getItems().clear();
        ListaDobleCircular<Cancion> cancionesUsuario = userName.getListaCanciones();
        agregarCancionesMias(cancionesUsuario);
        tblCancionesUsuario.setItems(listaCancionesUsuario);
    }

    /**
     *Actualiza la lista de canciones del usuario
     * y luego actualiza la tabla en la interfaz de usuario
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     *Se encarga de actualizar la tabla canciones del usuario actual
     */
    public void actualizarTablaMiLista() {
        tblCanciones.getItems().clear();
        agregarCancionesMias(userName.getListaCanciones());
        tblCanciones.setItems(listaCancionesUsuario);
        tblCanciones.refresh();
    }


    /**
     * Agrega canciones a la lista de canciones del usuario
     * @param listaCanciones
     */
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

    /**
     * Añade la funcionalidad al boton de reproducir
     * Abre una ventana nueva con la cancion
     * @param actionEvent
     */
    @FXML
    public void reproducir(ActionEvent actionEvent){
        String url = cancionSeleccionada.getURL();
        String ulrCurada = url.substring(32);
        System.out.println(ulrCurada);
        YoutubePlayer youtubePlayer = new YoutubePlayer(ulrCurada);
        Stage stage = new Stage();
        youtubePlayer.start(stage);
    }

    /**
     * Toma la cancion seleccionada de la tabla de canciones generales
     * @param mouseEvent
     */
    public void getCancionOnClick(MouseEvent mouseEvent) {
        cancionSeleccionada = tblCanciones.getSelectionModel().getSelectedItem();

    }

    /**
     * Toma la cancion seleccionada de la tabla de canciones del usuario
     * @param mouseEvent
     */
    public void getCancionUsuarioOnClick(MouseEvent mouseEvent) {
        cancionSeleccionada = tblCancionesUsuario.getSelectionModel().getSelectedItem();
        cancionSeleccionadaUsuario = tblCancionesUsuario.getSelectionModel().getSelectedItem();

    }

    /**
     * Elimina una cancion de la lista de canciones del usuario
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void eliminarCancionUsuario(ActionEvent event) throws IOException, ClassNotFoundException {
        if (cancionSeleccionadaUsuario != null) {
            aplicacion.eliminarCancionUser(userName, cancionSeleccionadaUsuario);
            cancionSeleccionadaUsuario=null;
            actualizarTablaMiLista();
            actualizarTabla();
        } else {
            System.out.println("Ninguna cancion ha sido seleccionada");
        }
    }

    /**
     * Actualiza la tabla para que realice la funcion deshacer
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void deshacer(ActionEvent event) throws IOException, ClassNotFoundException {
        aplicacion.deshacer();
        userName= aplicacion.reemplazarUsuario(userName);
        actualizarTablaMiLista();
        actualizarTabla();
    }

    /**
     * Actualiza la tabla para que realice la funcion rehacer
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void rehacer(ActionEvent event) throws IOException, ClassNotFoundException {
        aplicacion.rehacer();
        userName= aplicacion.reemplazarUsuario(userName);
        actualizarTablaMiLista();
        actualizarTabla();
    }

    /**
     * Comprueba y llena la informacion digitada en los campos por el usuario, para luego llamar a la funcion
     * que actualiza la tabla para reflejar la busqueda. Si no se tienen todos los criterios completos, simplemente
     * actualizara a la tabla base.
     * @param event evento activo al hacer click el boton de busqueda.
     */
    public void comprobarInfoBuscar(ActionEvent event){
        String textoBusqueda = buscar.getText();
        System.out.println(textoBusqueda);

        String criterioBusqueda = "";

        if(gTitulo.isSelected()){
            criterioBusqueda = "nombre";
        }
        else if(gArtista.isSelected()){
            criterioBusqueda = "artista";
        }
        else if(gGenero.isSelected()){
            criterioBusqueda = "genero";
        }

        System.out.println(criterioBusqueda);

        if(criterioBusqueda != "" && textoBusqueda != ""){
            tblCanciones.getItems().clear();
            actualizarSegunCriterio(criterioBusqueda, textoBusqueda);
        }
        else{
            tblCanciones.getItems().clear();
            actualizarTabla();
        }

    }


    public void inOrderTraversal(ArbolBinario<Artista> arbol,Consumer<Artista> action) {
        inOrderTraversal(arbol.getRaiz(), action);
    }

    /**
     * Recorrido in orden
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





}
