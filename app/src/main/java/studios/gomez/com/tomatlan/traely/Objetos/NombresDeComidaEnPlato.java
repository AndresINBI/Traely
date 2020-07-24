package studios.gomez.com.tomatlan.traely.Objetos;

import com.orm.SugarRecord;

/**
 * Created by gomez on 3/12/17.
 */

public class NombresDeComidaEnPlato extends SugarRecord {

    String nombre;
    int numeroDePedido,numeroDePlato;
    Boolean completado;

    public NombresDeComidaEnPlato() {
    }

    public NombresDeComidaEnPlato(String nombre, int numeroDePedido,int numeroDePlato,Boolean completado) {
        this.nombre = nombre;
        this.numeroDePedido = numeroDePedido;
        this.numeroDePlato = numeroDePlato;
        this.completado = completado;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroDePedido() {
        return numeroDePedido;
    }

    public void setNumeroDePedido(int numeroDePedido) {
        this.numeroDePedido = numeroDePedido;
    }

    public int getNumeroDePlato() {
        return numeroDePlato;
    }

    public void setNumeroDePlato(int numeroDePlato) {
        this.numeroDePlato = numeroDePlato;
    }


    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }
}
