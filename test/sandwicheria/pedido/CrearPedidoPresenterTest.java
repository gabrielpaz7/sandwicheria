/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.pedido;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class CrearPedidoPresenterTest {
    
    public CrearPedidoPresenterTest() {
    }
       
    @Before
    public void setUp() {
        Repositorio.iniciar();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of buscarProductoPorCodigo method, of class CrearPedidoPresenter.
     */
    @Test
    public void testBuscarProductoPorCodigo() {
        System.out.println("buscarProductoPorCodigo");
        int codigo = 100; // Sandwich de Mila Comun
        CrearPedidoPresenter instance = new CrearPedidoPresenter();
        Producto expResult = Repositorio.getProductos().get(7); //Sandwich de Mila Comun
        Producto result = instance.buscarProductoPorCodigo(100);
        assertEquals(expResult, result);
    }

    /**
     * Test of agregarLineaPedido method, of class CrearPedidoPresenter.
     */
    @Test
    public void testAgregarLineaPedido() {
        System.out.println("agregarLineaPedido");
        Producto producto = Repositorio.getProductos().get(7); //Sandwich de Mila Comun
        int cantidad = 2;
        CrearPedidoPresenter instance = instance = new CrearPedidoPresenter(new Pedido());
        instance.agregarLineaPedido(producto, cantidad);
        
        assertFalse(instance.getModel().getLineasPedido().isEmpty());
    }

    /**
     * Test of calcularTotal method, of class CrearPedidoPresenter.
     */
    @Test
    public void testCalcularTotal() {
        System.out.println("calcularTotal");
        Producto producto = Repositorio.getProductos().get(7); //Sandwich de Mila Comun
        int cantidad = 2;
        CrearPedidoPresenter instance = instance = new CrearPedidoPresenter(new Pedido());
        instance.agregarLineaPedido(producto, cantidad);
        double expResult = 27.4;
        double result = instance.calcularTotal();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of realizarPedido method, of class CrearPedidoPresenter.
     */
    @Test
    public void testRealizarPedido() {
        System.out.println("realizarPedido");
        Producto producto = Repositorio.getProductos().get(7); //Sandwich de Mila Comun
        int cantidad = 2;
        CrearPedidoPresenter instance = instance = new CrearPedidoPresenter(new Pedido());
        instance.agregarLineaPedido(producto, cantidad);
        instance.realizarPedido();
        
        assertFalse(Repositorio.getPedidos().isEmpty());
    }
    
}
