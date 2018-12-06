/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.pedido;

import java.util.Iterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sandwicheria.Model;
import sandwicheria.View;
import sandwicheria.persistencia.Repositorio;
import sandwicheria.producto.Producto;

/**
 *
 * @author Gabriel
 */
public class EditarPedidoPresenterTest {
    
    public EditarPedidoPresenterTest() {
    }
    
    @Before
    public void setUp() {
        Repositorio.iniciar();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addProductoDetalle method, of class EditarPedidoPresenter.
     */
    @Test
    public void testAddProductoDetalle() {
        System.out.println("addProductoDetalle");
        Producto producto = Repositorio.getProductos().get(7); //Sandwich de Mila Comun
        int cantidad = 2;
        CrearPedidoPresenter pedido = pedido = new CrearPedidoPresenter(new Pedido());
        pedido.agregarLineaPedido(producto, cantidad);
        
        EditarPedidoPresenter instance = new EditarPedidoPresenter(pedido.getModel().getLineasPedido().get(0));
        Producto detalle1 = Repositorio.getProductos().get(4); // Agregado Jamon
        Producto detalle2 = Repositorio.getProductos().get(5); // Agregado Queso
        instance.addProductoDetalle(detalle1);
        
        Iterator iter = instance.getModel().getProducto().getDetalles().iterator();
        
        boolean encontrado = false;
        
        while(iter.hasNext()){
            Producto d = (Producto) iter.next();
            if(d.equals(detalle1)){
                encontrado = true;
            }
        }
        
        assertTrue(encontrado);        
    }
    
}
