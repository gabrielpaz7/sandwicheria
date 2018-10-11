/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.pedido;

import sandwicheria.Model;
import sandwicheria.Presenter;
import sandwicheria.View;
import sandwicheria.persistencia.Repositorio;
import sandwicheria.producto.Producto;

/**
 *
 * @author Gabriel
 */
public class CrearPedidoPresenter implements Presenter{
    Pedido model;
    CrearPedidoView view;

    public CrearPedidoPresenter(CrearPedidoView view, Pedido pedido) {
        this.view = view;
        this.model = pedido;
        
        //Seleccionar consumidor final por defecto
        model.setCliente(Repositorio.getClientes().get(0));
        view.setPresenter(this);
        
        System.out.println(Repositorio.getClientes().get(0));
    }
    
    
    @Override
    public void setModel(Model model) {
        this.model = (Pedido) model;
    }
    
    @Override
    public Pedido getModel() {
        return model;
    }
    
    @Override
    public void setView(View view) {
        this.view = (CrearPedidoView) view;
    }
    
    @Override
    public CrearPedidoView getView() {
        return view;
    }

    @Override
    public void updateView() {
        //TO-DO
    }

    @Override
    public void updateModel() {
        //TO-DO
    }    
    
    
    public Producto buscarProductoPorCodigo(int codigo){
        for(Producto p : Repositorio.getProductos()){
            if(p.getCodigo() == codigo){
                return p;
            }
        }
        return null;
    }
    
    public void agregarLineaPedido(Producto producto, int cantidad){
        LineaPedido linea = new LineaPedido(producto, cantidad);
        model.getLineasPedido().add(linea);
        
         model.setTotal(calcularTotal());
    }
    
    public double calcularTotal(){
        double total = 0;
        for(LineaPedido lineaPedido : model.getLineasPedido()){
            total += lineaPedido.getSubtotal();
        }
        return total;
    }
    
    public void realizarPedido(){
        Repositorio.getPedidos().add(model);
    }
    
    private void generarComanda(){
        
    }   

    

    


    
    
}
