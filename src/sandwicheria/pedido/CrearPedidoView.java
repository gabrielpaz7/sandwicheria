/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.pedido;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import sandwicheria.Presenter;
import sandwicheria.View;
import sandwicheria.aplicacion.AplicacionView;
import sandwicheria.persistencia.Session;
import sandwicheria.producto.Producto;
import sandwicheria.librerias.StripedTable;

/**
 *
 * @author Gabriel
 */
public class CrearPedidoView extends javax.swing.JInternalFrame implements View{

    private AplicacionView parentFrame;
    
    private CrearPedidoPresenter presenter;
    
    private Producto producto;
    /**
     * Creates new form CrearPaqueteView
     */
    public CrearPedidoView() {
        initComponents();
        
        
        this.mostrarInfoTerminal();
        this.bindEvents();        
    }

    @Override
    public void setParentFrame(View v) {
        parentFrame = (AplicacionView) v;
    }

    @Override
    public AplicacionView getParentFrame() {
        return  parentFrame;
    }

    @Override
    public void updateView() {
        fdCodigo.setText("");
        fdDescripcion.setText("");
        fdPrecio.setText("");
        fdCantidad.setValue(1);

        txtTotal.setText(String.format("$%,.2f", presenter.getModel().calcularTotal()));
        mostrarTablaLineasPedido();
        
        fdCodigo.requestFocus();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = (CrearPedidoPresenter) presenter;
        actualizarCliente();
    }

    @Override
    public CrearPedidoPresenter getPresenter() {
        return presenter;
    }
    



    
    
    private void showTime(){
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                txtFechaHora.setText(s.format(d));
            }
        }).start();
    }
    
    private void mostrarInfoTerminal(){
        this.showTime();
        txtTerminal.setText("Terminal: " + 1);
    }
    
    private void buscarProductoPorCodigo(){
        int codigo = Integer.valueOf(fdCodigo.getText());
        System.out.println("Codigo: " + codigo);
        
        producto = presenter.buscarProductoPorCodigo(codigo);
        //System.out.println(producto.toString());
        
        if(producto != null){
            fdDescripcion.setText(producto.getDescripcion());
            fdPrecio.setText(String.valueOf(producto.getPrecio()));
            fdCantidad.setValue(1);
        
            fdCantidad.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this, "Codigo inexistente");
        }
        
        
        
    }
    
    private void buscarProducto(){
        //abrir interfaz
    }
    
    private void agregarLineaPedido(){
        int cantidad = (int) fdCantidad.getValue();
        
        
        if(cantidad >= 1 && producto != null){
            for(int i = 1; i <= cantidad; i++){
                presenter.agregarLineaPedido(producto, 1);
            }

            producto = null;
            this.updateView();
        }else{
            JOptionPane.showMessageDialog(this, "Producto/cantidad incorrectos.");
        }        
    }
    
    
    
    private void actualizarCliente(){
        txtCuit.setText(String.valueOf(presenter.getModel().getCliente().getCuit()));
        txtRazonSocial.setText(presenter.getModel().getCliente().getNombreApellido());
        txtTipoResponsabilidad.setText(String.valueOf(presenter.getModel().getCliente().getCondicionTributaria()));
    }
    
    private void mostrarTablaLineasPedido(){
                
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        tableModel.addColumn("Codigo");
        tableModel.addColumn("Producto");
        tableModel.addColumn("Detalle");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Cantidad");
        tableModel.addColumn("Subtotal");

        
        for(LineaPedido lineaProducto : presenter.getModel().getLineasPedido()){
            Object[] object = new Object[6];
            object[0] = lineaProducto.getProducto().getCodigo();
            object[1] = lineaProducto.getProducto().getDescripcion();
            object[2] = lineaProducto.getProducto().getDetallesToString();
            
            double precioUnidad = lineaProducto.getSubtotal() / lineaProducto.getCantidad();
            object[3] = String.format("$%,.2f", precioUnidad);
            
            object[4] = lineaProducto.getCantidad();
            object[5] = String.format("$%,.2f", lineaProducto.getSubtotal());
            
            tableModel.addRow(object);
        }
        
        tbLineasPedido.setModel(tableModel);        
    }
    
    private void editarLineaPedido() {
        int selectedRow = tbLineasPedido.getSelectedRow();
        
        if(selectedRow > -1){
            EditarPedidoView view = new EditarPedidoView();
            view.setParentFrame(this.getParentFrame());
            
            
            LineaPedido model = presenter.getModel().getLineasPedido().get(selectedRow);
            System.out.println(model.toString());
            
            EditarPedidoPresenter editarPedidoPresenter;
            editarPedidoPresenter = new EditarPedidoPresenter(model, view);
            
            
            createView(view);
            
            view.addInternalFrameListener(new InternalFrameListener() {
                @Override
                public void internalFrameOpened(InternalFrameEvent e) {
                    //
                }

                @Override
                public void internalFrameClosing(InternalFrameEvent e) {
                    //
                }

                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    updateView();
                }

                @Override
                public void internalFrameIconified(InternalFrameEvent e) {
                    //
                }

                @Override
                public void internalFrameDeiconified(InternalFrameEvent e) {
                }

                @Override
                public void internalFrameActivated(InternalFrameEvent e) {
                }

                @Override
                public void internalFrameDeactivated(InternalFrameEvent e) {
                }
            });
        }
        
    }
    
    private void borrarLineaPedido(){
        int rowIndex = tbLineasPedido.getSelectedRow();
        
        if(rowIndex > -1){
            int confirm = JOptionPane.showConfirmDialog(this, "Desea eliminar el servicio?", "Confirmar", 0);
        
            if(confirm == JOptionPane.YES_OPTION){
                presenter.getModel().getLineasPedido().remove(rowIndex);
                //tbLineasPedido.remove(rowIndex);
               updateView();
            }  
        } 
    }
    
    @Override
    public void bindEvents(){
        /*fdCodigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigo();
            }
        });*/
        
        fdCodigo.addActionListener(e -> buscarProductoPorCodigo());
        
        btnAgregarLinea.addActionListener(e -> agregarLineaPedido());
        
        btnEditarLineaPedido.addActionListener(e -> editarLineaPedido());
        
        btnBorrarLineaPedido.addActionListener(e -> borrarLineaPedido());
    }
    
    public void createView(JInternalFrame iframe){
                getParentFrame().getDesktopPane().add(iframe);
                /*Centrar JInternalFrame y Agregar al DesktopPane*/
                Dimension desktopSize = getParentFrame().getSize();
                Dimension jInternalFrameSize = iframe.getSize();
                iframe.setLocation((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/2);

                iframe.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLineasPedido = new javax.swing.JPanel();
        btnEditarLineaPedido = new javax.swing.JButton();
        scrollPaneLineasPedido = new javax.swing.JScrollPane();
        tbLineasPedido = new javax.swing.JTable(){
            public boolean isCellEditable(int d, int c){
                return false;
            }

            public Component prepareRenderer( TableCellRenderer renderer, int row, int column ){
                Component c = super.prepareRenderer( renderer, row, column );
                if (!isRowSelected( row )){
                    c.setBackground( row % 2 == 0 ? getBackground() : Color.decode("#EAEAEA"));
                }
                return c;
            }

        };
        btnBorrarLineaPedido = new javax.swing.JButton();
        btnAceptarPedido = new javax.swing.JButton();
        btnCancelarPedido = new javax.swing.JButton();
        panelHeader = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        panelTerminal = new javax.swing.JPanel();
        txtFechaHora = new javax.swing.JLabel();
        txtTerminal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fdCodigo = new javax.swing.JTextField();
        fdDescripcion = new javax.swing.JTextField();
        btnAgregarLinea = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnBuscarProducto = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        fdPrecio = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        fdCantidad = new javax.swing.JSpinner();
        panelTotales = new javax.swing.JPanel();
        lbCodigoTributario = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JLabel();
        btnBuscarCliente = new javax.swing.JButton();
        txtCuit = new javax.swing.JLabel();
        txtTipoResponsabilidad = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Pedido");
        setFrameIcon(null);

        panelLineasPedido.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles Pedido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        btnEditarLineaPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/pencil_16.png"))); // NOI18N
        btnEditarLineaPedido.setText("Editar");

        tbLineasPedido.setAutoCreateRowSorter(true);
        tbLineasPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cod.", "Producto", "Detalles", "Precio", "Cantidad", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scrollPaneLineasPedido.setViewportView(tbLineasPedido);

        btnBorrarLineaPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/delete_16.png"))); // NOI18N
        btnBorrarLineaPedido.setText("Borrar");

        javax.swing.GroupLayout panelLineasPedidoLayout = new javax.swing.GroupLayout(panelLineasPedido);
        panelLineasPedido.setLayout(panelLineasPedidoLayout);
        panelLineasPedidoLayout.setHorizontalGroup(
            panelLineasPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLineasPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLineasPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPaneLineasPedido, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                    .addGroup(panelLineasPedidoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEditarLineaPedido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrarLineaPedido)))
                .addContainerGap())
        );
        panelLineasPedidoLayout.setVerticalGroup(
            panelLineasPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLineasPedidoLayout.createSequentialGroup()
                .addGroup(panelLineasPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarLineaPedido)
                    .addComponent(btnBorrarLineaPedido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPaneLineasPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAceptarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/accept_16.png"))); // NOI18N
        btnAceptarPedido.setText("Aceptar");

        btnCancelarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/cancel.png"))); // NOI18N
        btnCancelarPedido.setText("Cancelar");

        panelHeader.setBackground(new java.awt.Color(255, 255, 255));
        panelHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/food_32.png"))); // NOI18N
        jLabel8.setText("Crear Pedido");

        panelTerminal.setBackground(new java.awt.Color(255, 255, 255));

        txtFechaHora.setText("19/09/2018 - 07:07 AM");

        txtTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/computer_16.png"))); // NOI18N
        txtTerminal.setText("TerminaL 1");

        javax.swing.GroupLayout panelTerminalLayout = new javax.swing.GroupLayout(panelTerminal);
        panelTerminal.setLayout(panelTerminalLayout);
        panelTerminalLayout.setHorizontalGroup(
            panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTerminalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTerminal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaHora, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        panelTerminalLayout.setVerticalGroup(
            panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTerminalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTerminal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFechaHora)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addComponent(panelTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel3.setText("Cod.");

        fdDescripcion.setEditable(false);
        fdDescripcion.setFocusable(false);

        btnAgregarLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/add_16.png"))); // NOI18N
        btnAgregarLinea.setText("Agregar");
        btnAgregarLinea.setToolTipText("Agregar producto");
        btnAgregarLinea.setActionCommand("btnAgregarProducto");

        jLabel4.setText("Descripción");

        btnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/zoom_16.png"))); // NOI18N
        btnBuscarProducto.setText("Buscar");
        btnBuscarProducto.setToolTipText("Buscar producto");
        btnBuscarProducto.setActionCommand("btnAgregarProducto");

        jLabel10.setText("Precio");

        fdPrecio.setEditable(false);
        fdPrecio.setFocusable(false);

        jLabel13.setText("Cantidad");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(fdCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fdDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarProducto))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fdPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(fdCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnAgregarLinea)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fdDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarProducto)
                    .addComponent(fdCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fdPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fdCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAgregarLinea))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTotales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        lbCodigoTributario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbCodigoTributario.setText("CUIT");

        txtRazonSocial.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtRazonSocial.setText("Razon Social");

        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/zoom_16.png"))); // NOI18N
        btnBuscarCliente.setText("Buscar");
        btnBuscarCliente.setToolTipText("Buscar producto");
        btnBuscarCliente.setActionCommand("btnAgregarProducto");

        txtCuit.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCuit.setText("00-000000-0");

        txtTipoResponsabilidad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTipoResponsabilidad.setText("Tipo Responsabilidad");

        javax.swing.GroupLayout panelTotalesLayout = new javax.swing.GroupLayout(panelTotales);
        panelTotales.setLayout(panelTotalesLayout);
        panelTotalesLayout.setHorizontalGroup(
            panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTipoResponsabilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTotalesLayout.createSequentialGroup()
                        .addComponent(lbCodigoTributario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCuit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTotalesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBuscarCliente)))
                .addContainerGap())
        );
        panelTotalesLayout.setVerticalGroup(
            panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalesLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCodigoTributario)
                    .addComponent(txtCuit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazonSocial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTipoResponsabilidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarCliente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel1.setText("Subtotal ...................................");

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTotal.setText("$ 00.00");

        jLabel2.setText("Total");

        jLabel5.setText("Impuestos ................................");

        jLabel6.setText("Descuentos ...............................");

        jLabel7.setText("$00.00");

        jLabel9.setText("$00.00");

        jLabel11.setText("$00.00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotal))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(jLabel7))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel9)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel11)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(21, 21, 21)))
                .addContainerGap(160, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAceptarPedido))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelLineasPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelTotales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTotales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAceptarPedido)
                            .addComponent(btnCancelarPedido))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelLineasPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(42, 42, 42))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarPedido;
    private javax.swing.JButton btnAgregarLinea;
    private javax.swing.JButton btnBorrarLineaPedido;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelarPedido;
    private javax.swing.JButton btnEditarLineaPedido;
    private javax.swing.JSpinner fdCantidad;
    private javax.swing.JTextField fdCodigo;
    private javax.swing.JTextField fdDescripcion;
    private javax.swing.JTextField fdPrecio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbCodigoTributario;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelLineasPedido;
    private javax.swing.JPanel panelTerminal;
    private javax.swing.JPanel panelTotales;
    private javax.swing.JScrollPane scrollPaneLineasPedido;
    private javax.swing.JTable tbLineasPedido;
    private javax.swing.JLabel txtCuit;
    private javax.swing.JLabel txtFechaHora;
    private javax.swing.JLabel txtRazonSocial;
    private javax.swing.JLabel txtTerminal;
    private javax.swing.JLabel txtTipoResponsabilidad;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables


    
    
}
