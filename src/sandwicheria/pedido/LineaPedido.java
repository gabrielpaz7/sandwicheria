/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.pedido;

import sandwicheria.Model;
import sandwicheria.producto.Producto;

/**
 *
 * @author Gabriel
 */
public class LineaPedido implements Model, Cloneable{
    Producto producto;
    int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    public double getSubtotal(){
       double subtotal = 0.00; 
       double precio = producto.getPrecio();
       cantidad = cantidad < 1 ? 1 : cantidad;
       
       if(producto.getDetalles() == null){
           return precio * cantidad;
       }
       
       double agregados = 0.00;
       for(Producto d : producto.getDetalles()) {
           if (d.isAgregado() && d.isIncluido()) {
               agregados += d.getPrecio();
           }
       }
       
       
       subtotal = (precio + agregados) * cantidad;

        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString() {
        return String.format("\nProducto: %s \nCantidad: %d", producto, cantidad);
    }
    
    
}
