package studios.gomez.com.tomatlan.traely.Objetos;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by gomez on 27/11/17.
 */

public class PlatoBD extends SugarRecord {

    int numeroDePedido,precio,numeroDePlato;
    Boolean completado;


    public PlatoBD() {
    }

    public PlatoBD(int numeroDePedido,int precio,int numeroDePlato,Boolean completado) {
        this.numeroDePedido = numeroDePedido;
        this.precio = precio;
        this.numeroDePlato = numeroDePlato;
        this.completado = completado;
    }


    public int getNumeroDePedido() {
        return numeroDePedido;
    }

    public void setNumeroDePedido(int numeroDePedido) {
        this.numeroDePedido = numeroDePedido;
    }


    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
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
