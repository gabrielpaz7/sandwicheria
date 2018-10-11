/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.pedido;

import sandwicheria.Model;
import sandwicheria.Presenter;
import sandwicheria.View;

/**
 *
 * @author Gabriel
 */
public class EditarPedidoPresenter implements Presenter{
    LineaPedido model;
    EditarPedidoView view;

    public EditarPedidoPresenter(LineaPedido model, EditarPedidoView view) {
        this.model = model;
        this.view = view;
        
        this.view.setPresenter(this);
        this.updateView();
    }

    @Override
    public void setModel(Model model) {
        this.model = (LineaPedido) model;
    }
    
    @Override
    public LineaPedido getModel() {
        return model;
    }

    @Override
    public void setView(View view) {
        this.view = (EditarPedidoView) view;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void updateView() {
        view.updateView();
    }

    @Override
    public void updateModel() {
        //TO-DO
    }
        
    
}
