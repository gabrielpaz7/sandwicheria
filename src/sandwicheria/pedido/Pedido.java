/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.pedido;

import java.util.ArrayList;
import java.util.Date;
import sandwicheria.Model;
import sandwicheria.cliente.Cliente;

/**
 *
 * @author Gabriel
 */
public class Pedido implements Model{
    
    int numero;
    Date fecha;
    double total;
    Cliente cliente;
    ArrayList<LineaPedido> lineasPedido;

    public Pedido() {
        this.lineasPedido = new ArrayList<LineaPedido>();
        this.fecha = new Date();
    }   
    
    public Pedido(int numero, Date fecha, double total, Cliente cliente, ArrayList<LineaPedido> lineasPedido) {
        this.numero = numero;
        this.fecha = fecha;
        this.total = total;
        this.cliente = cliente;
        this.lineasPedido = lineasPedido;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(ArrayList<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }
    
    
    public double calcularTotal(){
        double subtotal = 0.00;
        
        for(LineaPedido li : lineasPedido) {
            subtotal += li.getSubtotal();
        }
        setTotal(subtotal);
        return subtotal;
    }
}
