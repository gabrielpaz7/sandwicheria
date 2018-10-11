/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.producto;

/**
 *
 * @author Gabriel
 */
public class Stock {
    int cantidad;
    int min;
    int max;

    public Stock(int cantidad, int min, int max) {
        this.cantidad = cantidad;
        this.min = min;
        this.max = max;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    
    
}
