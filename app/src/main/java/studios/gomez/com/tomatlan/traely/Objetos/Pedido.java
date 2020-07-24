package studios.gomez.com.tomatlan.traely.Objetos;

import com.orm.SugarRecord;

/**
 * Created by gomez on 3/12/17.
 */

public class Pedido extends SugarRecord {

    String nombre,precioTotal,Ubicacion,Fecha;
    int numeroDePedido;
    Boolean completado;

    public Pedido() {
    }

    public Pedido(String nombre, String precioTotal, String ubicacion, String fecha,int numeroDePedido,Boolean completado) {
        this.nombre = nombre;
        this.precioTotal = precioTotal;
        this.Ubicacion = ubicacion;
        this.Fecha = fecha;
        this.numeroDePedido = numeroDePedido;
        this.completado = completado;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(String precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getNumeroDePedido() {
        return numeroDePedido;
    }

    public void setNumeroDePedido(int numeroDePedido) {
        this.numeroDePedido = numeroDePedido;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }
}
