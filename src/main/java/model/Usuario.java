package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.Serializable;
import java.util.Optional;

/**
 * Representa al usuario con sus respectivos atributos y la lista de canciones asociadas
 * @author Juliana
 * @author Juan
 */
public class Usuario implements Serializable {

    private String userName;
    private String contrasenia;
    private String email;
    private ListaDobleCircular<Cancion> listaCanciones = new ListaDobleCircular<>();

    private static final long serialVersionUID = 1L;

    public Usuario(String userName, String contrasenia, String email) {
        this.userName = userName;
        this.contrasenia = contrasenia;
        this.email = email;
    }

    /**
     * Agrega una cancion a la lista de canciones
     * @param cancion
     */
    public void agregarCancionLista(Cancion cancion){
        int posicion = listaCanciones.buscar(cancion);
        if (posicion !=-1){
        }else {
            listaCanciones.agregarFinal(cancion);

        }

    }

    /**
     * Elimina una cancion de la lista
     * @param cancion
     */
    public void eliminarCancionLista(Cancion cancion){
        listaCanciones.eliminar(cancion);
        mostrarMensajeInformacion("La cancion fue eliminada");

    }

    /**
     * Muestra mensaje de alerta
     * @param mensaje
     * @return
     */
    private boolean mostrarMensajeInformacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }


    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ListaDobleCircular<Cancion> getListaCanciones() {
        return listaCanciones;
    }

    public void setListaCanciones(ListaDobleCircular<Cancion> listaCanciones) {
        this.listaCanciones = listaCanciones;
    }
}
