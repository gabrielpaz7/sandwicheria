/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.aplicacion;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuBar;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.plaf.DesktopPaneUI;
import sandwicheria.pedido.CrearPedidoView;

/**
 *
 * @author Cesar
 */
public class AplicacionView extends javax.swing.JFrame {

    /**
     * Creates new form TerminalAplicacionView
     */
    public AplicacionView() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIcon();
        setVisible(true);
        
        crearMenus();
        
    }
    

    public JMenuBar getMainMenuBar(){
        return menuBar;
    }
    
    public JDesktopPane getDesktopPane(){
        return desktopPane;
    }
    
    public JPanel getStatusBar(){
        return statusBar; 
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sandwicheria/resources/app_48.png")));
    }

    
    public JDesktopPane getDesktopPaneParent(){
        return this.getDesktopPane();
    }
    
    public void crearMenus(){
        crearMenuInicio();
        crearMenuPedido();
        //crearMenuUsuarios();
        crearMenuAyuda();
    }
    
    public void crearMenuInicio(){
        JMenu menuInicio = new JMenu();
        menuInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/house_16.png"))); // NOI18N
        menuInicio.setText("Inicio   ");

        /*
        JMenuItem itemIniciarSesion = new JMenuItem();
        itemIniciarSesion.setText("Iniciar Sesion");
        itemIniciarSesion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        menuInicio.add(itemIniciarSesion);
        menuInicio.addSeparator();
        */
        
        JMenuItem itemSalir= new JMenuItem();
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });        

        
        menuInicio.add(itemSalir); 
        
        this.getMainMenuBar().add(menuInicio);
    }
    
    public void crearMenuPedido(){
        JMenu menuInicio = new JMenu();
        menuInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/food_16.png"))); // NOI18N
        menuInicio.setText("Pedido   ");

        /*
        JMenuItem itemIniciarSesion = new JMenuItem();
        itemIniciarSesion.setText("Iniciar Sesion");
        itemIniciarSesion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        menuInicio.add(itemIniciarSesion);
        menuInicio.addSeparator();
        */
        
        JMenuItem itemCrearPedido= new JMenuItem();
        itemCrearPedido.setText("Crear Pedido");
        itemCrearPedido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CrearPedidoView window = new CrearPedidoView();
                
                
                
                getDesktopPane().add(window);
                
                /*Centrar JInternalFrame y Agregar al DesktopPane*/
                Dimension desktopSize = getSize();
                Dimension jInternalFrameSize = window.getSize();
                window.setLocation((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/2);
                
                
                window.setVisible(true);
            }
        });        

        
        menuInicio.add(itemCrearPedido); 
        
        this.getMainMenuBar().add(menuInicio);
    }
    
    public void crearMenuAyuda(){
        JMenu menuAyuda = new JMenu();
        menuAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/help_16.png"))); // NOI18N
        menuAyuda.setText("Ayuda   ");

        //Crear Paquete
        JMenuItem itemAcerca = new JMenuItem();
        itemAcerca.setText("Acerca de...");
        itemAcerca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AcercaView window = new AcercaView();
                
                
                
                getDesktopPane().add(window);
                
                /*Centrar JInternalFrame y Agregar al DesktopPane*/
                Dimension desktopSize = getSize();
                Dimension jInternalFrameSize = window.getSize();
                window.setLocation((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/2);
                
                
                window.setVisible(true);
            }
        });
        
        menuAyuda.add(itemAcerca);
        
         
        JMenuItem itemReset = new JMenuItem();
        itemReset.setText("Reiniciar Repositorio");
        itemReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Repositorio.Iniciar();
            }
        });
        
        menuAyuda.add(itemReset);
        
        getMainMenuBar().add(menuAyuda);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        statusBar = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sandwicheria");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });

        desktopPane.setBackground(new java.awt.Color(240, 240, 240));
        desktopPane.setAutoscrolls(true);

        statusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        statusBar.setPreferredSize(new java.awt.Dimension(884, 15));

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 889, Short.MAX_VALUE)
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        menuBar.setMaximumSize(new java.awt.Dimension(0, 20));
        menuBar.setMinimumSize(new java.awt.Dimension(0, 20));
        menuBar.setPreferredSize(new java.awt.Dimension(0, 30));
        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowIconified

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AplicacionView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AplicacionView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AplicacionView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AplicacionView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AplicacionView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel statusBar;
    // End of variables declaration//GEN-END:variables

}
