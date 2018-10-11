/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria;

/**
 *
 * @author Gabriel
 */
public interface Presenter {
   public abstract void setView(View view);
   public abstract View getView();
    
   public abstract void setModel(Model model);
   public abstract Model getModel();
   
   public abstract void updateView();
   public abstract void updateModel();
}
