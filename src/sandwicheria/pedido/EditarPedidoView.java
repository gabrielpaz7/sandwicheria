/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.pedido;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import sandwicheria.Presenter;
import sandwicheria.View;
import sandwicheria.aplicacion.AplicacionView;
import sandwicheria.producto.BuscarProductoPresenter;
import sandwicheria.producto.BuscarProductoView;
import sandwicheria.producto.Producto;

/**
 *
 * @author Gabriel
 */
public class EditarPedidoView extends javax.swing.JInternalFrame implements View {
    
    AplicacionView parentFrame;
    EditarPedidoPresenter presenter;
    Producto producto;

    /**
     * Creates new form EditarPedidoView
     */
    public EditarPedidoView() {
        initComponents();
        this.bindEvents();
        
        fdCodigo.requestFocus();
    }

    @Override
    public void setParentFrame(View v) {
        parentFrame = (AplicacionView) v;
    }

    @Override
    public AplicacionView getParentFrame() {
        return parentFrame;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = (EditarPedidoPresenter) presenter;
    }

    @Override
    public EditarPedidoPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void updateView() {
        LineaPedido model = presenter.getModel();
        fdLineaPedidoDescripcion.setText(model.getProducto().getDescripcion());
        fdCantidad.setValue(model.getCantidad());
        txtTotal.setText(String.format("$ %,.2f", model.getSubtotal()));
        mostrarDetalles(model);
        
        
    }

    @Override
    public void bindEvents() {
        
        fdCodigo.addActionListener(e -> buscarProductoPorCodigo());

        fdCantidad.addChangeListener((ChangeEvent e) -> {
            setCantidad();
        });

        btnGuardarDetallesProducto.addActionListener(e -> {
            dispose();
        });
        
        btnCancelar.addActionListener(e -> { 
            dispose();
        });
        
        btnAddProductoDetalle.addActionListener(e -> agregarProductoDetalle());
        
        btnBuscarProducto.addActionListener(e -> buscarProducto());
        
    }
    
    private void setCantidad(){
        LineaPedido model = presenter.getModel();
        System.out.println("antes: " + model.getCantidad());
        model.setCantidad((int) fdCantidad.getValue());
        System.out.println("despues: " + model.getCantidad());
        System.out.println(String.format("$ %,.2f", model.getSubtotal()));
        
        
        updateView();                
    }
    
      public void setProductoIncluido(int productoIndex, boolean value){
        LineaPedido model = presenter.getModel();
        Producto producto = model.getProducto().getDetalles().get(productoIndex);
        
        if(producto.isAgregado() && value == false){
        
            borrarDetalleProducto(productoIndex);
            //return;
        }
        
        producto.setIncluido(value);

        updateView();
    }  
      
     private void borrarDetalleProducto(int productoIndex){
         LineaPedido model = presenter.getModel();
         
         if(productoIndex > -1){
                int confirm = JOptionPane.showConfirmDialog(this, "borrar detalle?", "Confirmar", 0);

                if(confirm == JOptionPane.YES_OPTION){
                    model.getProducto().getDetalles().remove(productoIndex);
                    //tbLineasPedido.remove(rowIndex);
                   //updateView();
                }  
            } 
     } 
    
    private void mostrarDetalles(LineaPedido lineaPedido){
        //DefaultTableModel tableModel = (DefaultTableModel) tbDetallesProducto.getModel();
        /**
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        tableModel.addColumn("Codigo");
        tableModel.addColumn("Producto");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Incluido");
        */

        DefaultTableModel tableModel = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Producto", "Precio", "Tipo", "Incluido"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            
            
        };
        
        tableModel.addTableModelListener(new TableModelListener() {
            int BOOLEAN_COLUMN = 4;
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == BOOLEAN_COLUMN) {
                    TableModel model = (TableModel) e.getSource();
                    String columnName = model.getColumnName(column);
                    Boolean checked = (Boolean) model.getValueAt(row, column);
                    if (checked) {
                        System.out.println(columnName + ": " + true);
                        setProductoIncluido(row, true);
                    } else {
                        setProductoIncluido(row, false);
                    }
                }
            }
        });

        
        for(Producto producto : lineaPedido.getProducto().getDetalles()){
            Object[] object = new Object[5];
            object[0] = producto.getCodigo();
            object[1] = producto.getDescripcion();
            object[2] = String.format("$ %,.2f", producto.getPrecio());
            object[3] = producto.isAgregado() ? "AGREGADO ESPECIAL" : "POR DEFECTO";
            object[4] = producto.isIncluido();
            
            tableModel.addRow(object);
        }
        
        tbDetallesProducto.setModel(tableModel); 
    }
    
    
    private void buscarProductoPorCodigo(){
        
        int codigo = Integer.valueOf(fdCodigo.getText());
        System.out.println("Codigo: " + codigo);

        this.producto = presenter.buscarProductoPorCodigo(codigo);
        //System.out.println(producto.toString());

        if(this.producto != null){
            fdDescripcion.setText(this.producto.getDescripcion());
            fdPrecio.setText(String.valueOf(this.producto.getPrecio()));
            fdCantidad.setValue(1);

            btnAddProductoDetalle.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this, "Codigo inexistente");
        }   
    }
    
    private void buscarProducto(){
        //Producto model = new Producto();
        BuscarProductoView view = new BuscarProductoView(parentFrame, true);        
        Presenter buscarProductoPresenter = new BuscarProductoPresenter(producto, view);
        
        producto  = view.getProducto();

        if(producto != null){
            fdCodigo.setText(String.valueOf(producto.getCodigo()));
            fdDescripcion.setText(producto.getDescripcion());
            fdPrecio.setText(String.valueOf(producto.getPrecio()));
            fdCantidad.setValue(1);

            tbDetallesProducto.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this, "Producto inexistente");
        }        
    }    
    
    private void agregarProductoDetalle(){
        if(producto != null){
            presenter.addProductoDetalle(producto);
            
            fdCodigo.setText("");
            fdDescripcion.setText("");
            fdPrecio.setText("");
            
            fdCodigo.requestFocus();
            producto = null;
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un producto para agregar");
        }
        
        updateView();
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbDetallesProducto = new javax.swing.JTable(){

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5)
                return Boolean.class;
                return super.getColumnClass(columnIndex);
            }

            /*@Override
            public Class<?> getColumnClass(int columnIndex) {
                return data[0][columnIndex].getClass();
            }*/

            public Component prepareRenderer( TableCellRenderer renderer, int row, int column ){
                Component c = super.prepareRenderer( renderer, row, column );
                if (!isRowSelected( row )){
                    c.setBackground( row % 2 == 0 ? getBackground() : Color.decode("#EAEAEA"));
                }
                return c;
            }

        };
        panelHeader = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        panelTerminal = new javax.swing.JPanel();
        btnGuardarDetallesProducto = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        fdLineaPedidoDescripcion = new javax.swing.JTextField();
        fdCantidad = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        txtTotal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fdCodigo = new javax.swing.JTextField();
        btnAddProductoDetalle = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        fdPrecio = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        fdDescripcion = new javax.swing.JTextField();
        btnBuscarProducto = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Editar Pedido");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        tbDetallesProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Producto", "Precio", "Tipo", "Incluido"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbDetallesProducto);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );

        panelHeader.setBackground(new java.awt.Color(255, 255, 255));
        panelHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/food_32.png"))); // NOI18N
        jLabel8.setText("Detalles Producto");

        panelTerminal.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTerminalLayout = new javax.swing.GroupLayout(panelTerminal);
        panelTerminal.setLayout(panelTerminalLayout);
        panelTerminalLayout.setHorizontalGroup(
            panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 132, Short.MAX_VALUE)
        );
        panelTerminalLayout.setVerticalGroup(
            panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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
            .addComponent(jLabel8)
            .addComponent(panelTerminal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnGuardarDetallesProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/disk_16.png"))); // NOI18N
        btnGuardarDetallesProducto.setText("Guardar");
        btnGuardarDetallesProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarDetallesProductoActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/cancel.png"))); // NOI18N
        btnCancelar.setText("Guardar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        fdLineaPedidoDescripcion.setEditable(false);
        fdLineaPedidoDescripcion.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(fdLineaPedidoDescripcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fdCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fdLineaPedidoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fdCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotal.setText("$ 00.00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(txtTotal)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtTotal)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel3.setText("Cod.");

        btnAddProductoDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/add_16.png"))); // NOI18N
        btnAddProductoDetalle.setText("Agregar");
        btnAddProductoDetalle.setToolTipText("Agregar producto");
        btnAddProductoDetalle.setActionCommand("btnAgregarProducto");

        jLabel4.setText("Descripción");

        jLabel10.setText("Precio");

        fdPrecio.setEditable(false);
        fdPrecio.setFocusable(false);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(330, 38));

        fdDescripcion.setEditable(false);
        fdDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        fdDescripcion.setBorder(null);
        fdDescripcion.setFocusable(false);

        btnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/zoom_16.png"))); // NOI18N
        btnBuscarProducto.setToolTipText("Buscar producto");
        btnBuscarProducto.setActionCommand("btnAgregarProducto");
        btnBuscarProducto.setAlignmentY(0.0F);
        btnBuscarProducto.setBorder(null);
        btnBuscarProducto.setBorderPainted(false);
        btnBuscarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarProducto.setDefaultCapable(false);
        btnBuscarProducto.setFocusPainted(false);
        btnBuscarProducto.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(fdDescripcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnBuscarProducto)
            .addComponent(fdDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fdCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(119, 119, 119))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(fdPrecio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddProductoDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fdCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fdPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddProductoDetalle)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarDetallesProducto))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarDetallesProducto)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarDetallesProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarDetallesProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarDetallesProductoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProductoDetalle;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarDetallesProducto;
    private javax.swing.JSpinner fdCantidad;
    private javax.swing.JTextField fdCodigo;
    private javax.swing.JTextField fdDescripcion;
    private javax.swing.JTextField fdLineaPedidoDescripcion;
    private javax.swing.JTextField fdPrecio;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelTerminal;
    private javax.swing.JTable tbDetallesProducto;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables
}
