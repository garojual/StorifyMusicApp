package com.example.storifymusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import serializacion.Persistencia;

import java.io.IOException;
import java.util.HashMap;

/**
 * Se encarga de las conexiones con la logica y las administracion de ventanas
 * @author Juliana
 * @author Juan
 */
public class HelloApplication extends Application {

    private Reproductor reproductor = Reproductor.getInstance();
    private Stage primaryStage;
    private Carataker caretaker = new Carataker();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Storify Music");
        this.primaryStage.setResizable(false);
        showLogin();
    }

    /**
     * Muestra la ventana de iniciar sesion
     * @throws IOException
     */
    private void showLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("/com/example/storifymusic/LoginVista.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();
        LoginVistaController loginVistaController = loader.getController();
        loginVistaController.setAplicacion(this);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Muestra la ventana de crear usuario
     * @throws IOException
     */
    public void showCrearUsuario() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("/com/example/storifymusic/CrearUsuarioVista.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();
        CrearUsuarioVistaController crearUsuarioVistaController = loader.getController();
        crearUsuarioVistaController.setAplicacion(this);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Muestra la ventana de cada usuario
     * @param userName
     * @throws IOException
     */
    private void showUsuario(String userName) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("/com/example/storifymusic/UsuarioVista.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();
        UsuarioVistaController usuarioVistaController = loader.getController();
        usuarioVistaController.setUserName(reproductor.getTablaUsuarios().get(userName));
        usuarioVistaController.setAplicacion(this);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Muestra la ventana del admin
     * @throws IOException
     */
    public void showAdminView() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("/com/example/storifymusic/AdminVista.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();
        AdminVistaController adminVistaController = loader.getController();
        adminVistaController.setAplicacion(this);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Muestra la ventana para crear artistas
     */
    private void showCrearArtistaView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/com/example/storifymusic/CrearArtistaVista.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            CrearArtistaVistaController crearArtistaVistaController = loader.getController();
            crearArtistaVistaController.setAplicacion(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * muestra la ventana para crear canciones
     * @param artista
     */
    private void showCrearCancionView(Artista artista){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/com/example/storifymusic/CrearCancionVista.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            CrearCancionVistaController crearCancionVistaController = loader.getController();
            crearCancionVistaController.setArtista(artista);
            crearCancionVistaController.setAplicacion(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * muestra el artista
     */
    public void showArtista(){
        showCrearArtistaView();

    }

    /**
     * Se encarga de volver a la pagina inicial
     * @throws IOException
     */
    public void devolverLogin() throws IOException {
        showLogin();
    }

    public void devolverAdmin() throws IOException {
        showAdminView();
    }

    /**
     * muestra la cancion
     * @param artista
     */
    public void showCrearCancion(Artista artista){
        showCrearCancionView(artista);
    }


    /**
     * Da acceso para ingresar como admin
     * @param userName
     * @param contrasenia
     * @throws IOException
     */
    public void ingresarAdmin(String userName, String contrasenia) throws IOException {
        boolean verificar = reproductor.ingresarAdmin(userName,contrasenia);
        if (verificar){
            showAdminView();
        }else {
            //Mostrar error
        }

    }

    /**
     * Da acceso para ingresar como usuario
     * @param userName
     * @param contrasenia
     * @throws IOException
     */
    public void ingresarUsuario(String userName, String contrasenia) throws IOException {
        boolean verificar = reproductor.ingresarUser(userName, contrasenia);
        if (verificar){
            showUsuario(userName);
        }else {
            //Mostrar mensaje
        }
    }

    /**
     * Elimina la cancion de la lista de usuarios y guarda el estado
     * @param usuario
     * @param cancionSeleccionadaMias
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void eliminarCancionUser(Usuario usuario, Cancion cancionSeleccionadaMias) throws IOException, ClassNotFoundException {
        caretaker.guardarEstado(reproductor);
        System.out.println("Almacenado estado reproductor");
        reproductor.eliminarCancionListaUser(usuario, cancionSeleccionadaMias);
        Persistencia.serializar(reproductor);
    }

    /**
     * Deshace la ultima accion realizada
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void deshacer() throws IOException, ClassNotFoundException {
        caretaker.deshacer(reproductor);
        Persistencia.serializar(reproductor);
    }

    /**
     * Rehace la ultima accion realizada
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void rehacer() throws IOException, ClassNotFoundException {
        caretaker.rehacer(reproductor);
        Persistencia.serializar(reproductor);
    }

    public Usuario reemplazarUsuario(Usuario usuario){
        return reemplazarUsuario(getTablaUsuarios(),usuario);
    }

    /**
     * Reemplaza un usuario
     * @param tablaUsuarios
     * @param usuario
     * @return
     */
    private Usuario reemplazarUsuario(HashMap<String,Usuario> tablaUsuarios,Usuario usuario){
        return tablaUsuarios.get(usuario.getUserName());
    }



    /**
     * Da el acceso para crear un nuevo usuario
     * @param nombre
     * @param clave
     * @param correo
     * @throws IOException
     */
    public void crearUsuario(String nombre, String clave, String correo) throws IOException {
        boolean verify = reproductor.crearUser(nombre, clave, correo);
        if (verify) {
            Persistencia.serializar(reproductor);
            showLogin();
        } else {
            System.out.println("El usuario ya existe");
            //mostrarMensajeError("El usuario no puede ser creado");
        }

    }

    /**
     *
     * @param usuario
     * @param cancionSeleccionada
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void agregarCancionListaUser(Usuario usuario, Cancion cancionSeleccionada) throws IOException, ClassNotFoundException {
        caretaker.guardarEstado(reproductor);
        reproductor.agregarCancionListaUser(usuario,cancionSeleccionada);
        Persistencia.serializar(reproductor);

    }

    /**
     * Crea un nuevo artista
     * @param nombre
     * @param nacionalidad
     * @param codigo
     * @param isGrupo
     * @throws IOException
     */
    public void crearArtista(String nombre, String nacionalidad, String codigo, Boolean isGrupo) throws IOException {
        boolean verify = reproductor.crearArtista(nombre,nacionalidad,codigo,isGrupo);
        if (verify){
            System.out.println("Se creo el artista");
            Persistencia.serializar(reproductor);
            showAdminView();
        }else {
            System.out.println("Error");
        }
    }

    /**
     * Crea el codigo aleatoriamente de la cancion
     * @return
     */
    public String crearCodigo(){
        String codigo="";
        for(int i=0;i<6;i++){
            double j = Math.random()*(6);
            codigo= codigo + j;
        }
        return codigo;
    }

    /**
     * Crea una nueva cancion validando los campos
     * @param nombre
     * @param album
     * @param duracion
     * @param anio
     * @param url
     * @param genero
     * @param artista
     * @throws IOException
     */
    public void crearCancion(String nombre,String album, String duracion, String anio, String url, Genero genero, Artista artista) throws IOException {
        String codigo = crearCodigo();
        boolean verify = reproductor.crearCancion(artista,codigo,nombre,album,anio,genero,url);
        if(verify){
            Persistencia.serializar(reproductor);
            showAdminView();
        }

    }

    public ArbolBinario<Artista> getArtistas(){
        return reproductor.getArbolArtista();
    }

    public HashMap<String, Usuario> getTablaUsuarios() {
        return reproductor.getTablaUsuarios();
    }


}