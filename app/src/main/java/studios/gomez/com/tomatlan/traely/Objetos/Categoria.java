package studios.gomez.com.tomatlan.traely.Objetos;

/**
 * Created by gomez on 5/11/17.
 */

public class Categoria {
    String nombre,icono;

    public Categoria(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
