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
public interface View {
    
    public abstract void setParentFrame(View v);
    
    public abstract View getParentFrame();
    
    public abstract void setPresenter(Presenter presenter);
    
    public abstract Presenter getPresenter();
    
    public abstract void updateView();
    
    public abstract void bindEvents();
}
