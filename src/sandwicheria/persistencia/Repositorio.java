/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.persistencia;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sandwicheria.cliente.Cliente;
import sandwicheria.cliente.CondicionTributaria;
import sandwicheria.pedido.Pedido;
import sandwicheria.producto.Producto;
import sandwicheria.usuario.Cajero;
import sandwicheria.turno.Turno;
import sandwicheria.producto.Rubro;
import sandwicheria.producto.Stock;

/**
 *
 * @author Gabriel
 */
public class Repositorio {
    private static ArrayList<Cajero> cajeros;
    private static ArrayList<Rubro> rubros;
    private static ArrayList<Producto> productos;
    private static ArrayList<Pedido> pedidos;
    private static ArrayList<Cliente> clientes;
    
    public static void iniciar(){
        iniciarCajeros();
        iniciarubros();
        iniciarProductos();
        iniciarClientes();        
    }
    
    private static void iniciarCajeros(){
        cajeros = new ArrayList<Cajero>();
        
        Cajero cajero = new Cajero(1, "Gabriel Paz", "admin", "admin", Turno.MAÑANA);
        cajeros.add(cajero);
        
        cajero = new Cajero(2, "Usuario X", "user", "user", Turno.NOCHE);
        cajeros.add(cajero);
    }
    
    private static void iniciarubros(){
        rubros = new ArrayList<Rubro>();
        
        Rubro rubro = new Rubro(1, "Sandwiches");
        rubros.add(rubro);
        
        rubro = new Rubro(2, "Pizzas");
        rubros.add(rubro);
        
        rubro = new Rubro(3, "Bebidas");
        rubros.add(rubro);
    }
    
    private static void iniciarProductos(){

        try {
                productos = new ArrayList<Producto>();

                //AGREGADOS POR DEFECTO
                Producto producto = new Producto(1, "Lechuga", false, null, rubros.get(0), new Stock(10, 0, 0), 0.0);
                productos.add(producto);

                producto = new Producto(2, "Tomate", false, null, rubros.get(0), new Stock(10, 0, 0), 0.0);
                productos.add(producto);

                producto = new Producto(3, "Cebollas", false, null, rubros.get(0), new Stock(10, 0, 0), 0.0);
                productos.add(producto);

                // ADEREZOS
                producto = new Producto(4, "Picante", false, null, rubros.get(0), new Stock(10, 0, 0), 0.0);
                producto.setIncluido(false);
                productos.add(producto);


                //AGREGADOS ESPECIALES
                producto = new Producto(10, "Jamon", true, null, rubros.get(0), new Stock(10, 0, 0), 5.00);
                productos.add(producto);

                producto = new Producto(20, "Queso", true, null, rubros.get(0), new Stock(10, 0, 0), 5.00);
                productos.add(producto);

                producto = new Producto(30, "Huevo", true, null, rubros.get(0), new Stock(10, 0, 0), 5.00);
                productos.add(producto);


                ArrayList<Producto> detallesComun = new ArrayList<Producto>();
                for(int i = 0; i < 4; i++){
                    detallesComun.add((Producto) productos.get(i).clone()); 
                }
                producto = new Producto(100, "Sandwich Milanesa", false, detallesComun, rubros.get(0), new Stock(10, 0, 0), 13.7);
                productos.add(producto);

                ArrayList<Producto> detallesEspecial = new ArrayList<Producto>();
                for(int i = 0; i < 6; i++){
                    detallesEspecial.add((Producto) productos.get(i).clone()); 
                }
                producto = new Producto(200, "Sandwich Milanesa Especial", false, detallesEspecial, rubros.get(0), new Stock(10, 0, 0), 20.50);
                productos.add(producto);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        
        
    }
    
    private static void iniciarClientes(){
        clientes = new ArrayList<Cliente>();
        
        Cliente cliente = new Cliente("Consumidor Final", 999999999, CondicionTributaria.CONSUMIDOR_FINAL);
        clientes.add(cliente);
        System.out.println(clientes.get(0));
        
        cliente = new Cliente("UTN-FRT", 888888888, CondicionTributaria.IVA_SUJETO_EXENTO);
        clientes.add(cliente);
        System.out.println(clientes.get(1));
    }
    
    private static void iniciarPedidos(){
        pedidos = new ArrayList<Pedido>();
    }

    public static ArrayList<Cajero> getCajeros() {
        return cajeros;
    }

    public static ArrayList<Rubro> getRubros() {
        return rubros;
    }

    public static ArrayList<Producto> getProductos() {
        return productos;
    }    

    public static ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public static ArrayList<Cliente> getClientes() {
        return clientes;
    }
    
    
    
}
