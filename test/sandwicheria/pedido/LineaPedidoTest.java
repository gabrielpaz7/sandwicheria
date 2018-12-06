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
import sandwicheria.persistencia.Repositorio;
import sandwicheria.producto.Producto;

/**
 *
 * @author Gabriel
 */
public class LineaPedidoTest {
    LineaPedido lineaPedido;
    
    public LineaPedidoTest() {
    }
    
    @Before
    public void setUp() {
        Repositorio.iniciar();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetSubtotal(){
        Producto producto = Repositorio.getProductos().get(7); // Sandwich mila comun
        System.out.println(producto.toString());
        lineaPedido = new LineaPedido(producto, 2);        
        double expected = 27.4;
        double actual = lineaPedido.getSubtotal();
        assertEquals(expected, actual, 0);
    }
    
}
