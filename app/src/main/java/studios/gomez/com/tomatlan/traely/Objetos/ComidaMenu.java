package studios.gomez.com.tomatlan.traely.Objetos;

/**
 * Created by gomez on 17/11/17.
 */

public class ComidaMenu {

    String imagen,nombre;
    int precio,cantidad;


    public ComidaMenu(String imagen, String nombre, int precio,int cantidad) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
