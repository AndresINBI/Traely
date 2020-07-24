package studios.gomez.com.tomatlan.traely.Objetos;

/**
 * Created by gomez on 5/11/17.
 */

public class Negocio {

    String nombre,telefono,categoria1,categoria2,imagen_producto_estrella,imagen_negocio_fisico,direccion;

    int cantidad_de_productos;
    int tiempo_min;
    int tiempo_max;
    int horario_abre;
    int horario_cierra;

    public Negocio(String nombre, String telefono, String categoria1, String categoria2,
                   String imagen_producto_estrella, String imagen_negocio_fisico,
                   String direccion, String precio, int cantidad_de_productos,
                   int tiempo_min, int tiempo_max, int horario_abre, int horario_cierra) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.categoria1 = categoria1;
        this.categoria2 = categoria2;
        this.imagen_producto_estrella = imagen_producto_estrella;
        this.imagen_negocio_fisico = imagen_negocio_fisico;
        this.direccion = direccion;
        this.cantidad_de_productos = cantidad_de_productos;
        this.tiempo_min = tiempo_min;
        this.tiempo_max = tiempo_max;
        this.horario_abre = horario_abre;
        this.horario_cierra = horario_cierra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCategoria() {
        return categoria1;
    }

    public void setCategoria(String categoria) {
        this.categoria1 = categoria;
    }

    public String getImagen_producto_estrella() {
        return imagen_producto_estrella;
    }

    public void setImagen_producto_estrella(String imagen_producto_estrella) {
        this.imagen_producto_estrella = imagen_producto_estrella;
    }

    public String getImagen_negocio_fisico() {
        return imagen_negocio_fisico;
    }

    public void setImagen_negocio_fisico(String imagen_negocio_fisico) {
        this.imagen_negocio_fisico = imagen_negocio_fisico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCantidad_de_productos() {
        return cantidad_de_productos;
    }

    public void setCantidad_de_productos(int cantidad_de_productos) {
        this.cantidad_de_productos = cantidad_de_productos;
    }

    public int getTiempo_min() {
        return tiempo_min;
    }

    public void setTiempo_min(int tiempo_min) {
        this.tiempo_min = tiempo_min;
    }

    public int getTiempo_max() {
        return tiempo_max;
    }

    public void setTiempo_max(int tiempo_max) {
        this.tiempo_max = tiempo_max;
    }

    public int getHorario_abre() {
        return horario_abre;
    }

    public void setHorario_abre(int horario_abre) {
        this.horario_abre = horario_abre;
    }

    public int getHorario_cierra() {
        return horario_cierra;
    }

    public void setHorario_cierra(int horario_cierra) {
        this.horario_cierra = horario_cierra;
    }
}
