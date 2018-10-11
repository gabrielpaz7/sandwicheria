/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.producto;

import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class Producto {
    int codigo;
    String descripcion;
    boolean agregado;
    ArrayList<Producto> detalles;
    Rubro rubro;
    Stock stock;
    Double precio;
    
    boolean incluido;

    public Producto() {
        detalles = new ArrayList<Producto>();
    }

    public Producto(int codigo, String descripcion, boolean esAgregado, ArrayList<Producto> detalles, Rubro rubro, Stock stock, Double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.agregado = esAgregado;
        this.detalles = detalles;
        this.rubro = rubro;
        this.stock = stock;
        this.precio = precio;
        this.incluido = true;
    }
   

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public boolean isAgregado() {
        return agregado;
    }

    public void setAgregado(boolean agregado) {
        this.agregado = agregado;
    }

    public ArrayList<Producto> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<Producto> detalles) {
        this.detalles = detalles;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isIncluido() {
        return incluido;
    }

    public void setIncluido(boolean incluido) {
        this.incluido = incluido;
    }
    
    
    public String toString(){
        String str = "{" 
                + "\nCodigo: " + codigo
                + "\nDescipcion: " + descripcion
                + "\nStock: " + stock.getCantidad()
                + "\nEsAgregado: " + isAgregado()
                + "\nRubo: " + rubro.getDescripcion()
                + "\nPrecio: " + precio
                +"\n}";
        
        return str;
    }
    
    public String getDetallesToString(){
        String str = "\n[";
        
        for(Producto p : getDetalles()){
            String detalle = "" 
                + (p.isIncluido()? "✓" : "✕")
                + " " + p.getDescripcion()                
                +" - ";
            
            str += detalle;
        }
        
        str += "\n]";
        
        return str;
    }
    
}
