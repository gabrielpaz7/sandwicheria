/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.producto;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sandwicheria.Model;
import sandwicheria.Presenter;
import sandwicheria.View;
import sandwicheria.persistencia.Repositorio;

/**
 *
 * @author Gabriel
 */
public class BuscarProductoPresenter implements Presenter{
    Producto model;
    BuscarProductoView view;

    public BuscarProductoPresenter(Producto model, BuscarProductoView view) {
        this.model = model;
        this.view = view;
        
        this.view.setPresenter(this);
    }
    

    @Override
    public void setView(View view) {
        this.view = (BuscarProductoView) view;
    }

    @Override
    public View getView() {
        return (View) view;
    }

    @Override
    public void setModel(Model model) {
        this.model = (Producto) model;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public void updateView() {
        //TO-DO
    }

    @Override
    public void updateModel() {
        //TO-DO
    }
    
    public ArrayList<Producto> buscarProductoPorCodigo(int codigo){
        ArrayList<Producto> productos = new ArrayList<Producto>();
        
        try{
            Producto producto = new Producto();
                    
            for(Producto p : Repositorio.getProductos()){
                if(p.getCodigo() == codigo){
                    producto = p.clone();
                    productos.add(producto);
                }
            }
            
            System.out.println(producto.toString());
            return productos;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return productos;
    }
    
    public ArrayList<Producto> buscarProductoPorDescripcion(String descripcion){
        ArrayList<Producto> productos = new ArrayList<Producto>();
        
        try {
            for(Producto p : Repositorio.getProductos()){
                if(p.getDescripcion().toLowerCase().contains(descripcion.toLowerCase())){
                    Producto producto = p.clone();
                    productos.add(producto);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return productos;
    }
}
