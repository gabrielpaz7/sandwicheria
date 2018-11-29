/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.producto;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import sandwicheria.Presenter;
import sandwicheria.View;
import sandwicheria.aplicacion.AplicacionView;
import sandwicheria.persistencia.Repositorio;

/**
 *
 * @author Gabriel
 */
public class BuscarProductoView extends javax.swing.JDialog implements View{

    AplicacionView parentFrame;
    BuscarProductoPresenter presenter;
    Producto producto;
    ArrayList<Producto> productos;
    /**
     * Creates new form BuscarProductoView
     */
    public BuscarProductoView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        bindEvents();
        
        setLocationRelativeTo(null); 
        
        productos = new ArrayList<>();
    }
    
    public Producto getProducto(){
        setVisible(true);
        return producto;
    }

    @Override
    public void setParentFrame(View v) {
        parentFrame = (AplicacionView) v;
    }

    @Override
    public View getParentFrame() {
        return parentFrame;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = (BuscarProductoPresenter) presenter;
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void updateView() {
        //
    }

    @Override
    public void bindEvents() {
        fdCodigo.addActionListener(e -> buscarProductoPorCodigo());
        fdDescripcion.addActionListener(e -> buscarProductosPorDescripcion());
        btnBuscar.addActionListener(e -> buscarProductosPorDescripcion());
        btnSeleccionarProducto.addActionListener(e -> seleccionarProducto());
        btnCancelar.addActionListener(e -> dispose());
        
        tbProductos.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    seleccionarProducto();
                }
            }
        });
    }
    
    private void buscarProductoPorCodigo(){
        int codigo = Integer.valueOf(fdCodigo.getText());
        fdDescripcion.setText("");
        
        if(codigo > 0){
           productos = presenter.buscarProductoPorCodigo(codigo);
        showProductos(productos); 
        }else{
            JOptionPane.showMessageDialog(this, "Cóodigo vacio.");
        }
    }
    
    private void buscarProductosPorDescripcion() {
        String descripcion = fdDescripcion.getText();
        fdCodigo.setText("");
        
        if(!descripcion.isEmpty()){
            productos = presenter.buscarProductoPorDescripcion(descripcion);
            showProductos(productos);  
        }else{
            JOptionPane.showMessageDialog(this, "Descripción vacia.");
        }

               
    }
    
    private void showProductos(ArrayList<Producto> productos) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        tableModel.addColumn("Codigo");
        tableModel.addColumn("Descripcion");
        tableModel.addColumn("Rubro");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Es Agreado");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Detalles");
        
        for(Producto producto : productos){
            Object[] object = new Object[7];
            object[0] = producto.getCodigo();
            object[1] = producto.getDescripcion();
            object[2] = producto.getRubro().getDescripcion();
            object[3] = producto.getStock().getCantidad();
            object[4] = producto.isAgregado();
            object[5] = producto.getPrecio();
            object[6] = producto.getDetallesToString();
            
            tableModel.addRow(object);
        }
        
        tbProductos.setModel(tableModel);
        
        tbProductos.setRowSorter(new TableRowSorter(tableModel){
            @Override
            public boolean isSortable(int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
    }
    
    
    public void seleccionarProducto(){
        int rowIndex = tbProductos.getSelectedRow();
        
        if(rowIndex > -1){
            producto = productos.get(rowIndex);
            presenter.setModel(productos.get(rowIndex));
            
        }
        
        this.setVisible(false);
        dispose();
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
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fdCodigo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        fdDescripcion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProductos = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int d, int c){
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4)
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
        btnSeleccionarProducto = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar Producto");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Calibri Light", 1, 12)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/food_32.png"))); // NOI18N
        jLabel8.setText("Buscar Producto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel3.setText("Cod.");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/zoom_16.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setToolTipText("Agregar producto");
        btnBuscar.setActionCommand("btnAgregarProducto");

        jLabel4.setText("Descripción");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(fdCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(fdDescripcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fdCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fdDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Rubro", "Stock", "Es Agregado", "Precio", "Detalles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbProductos);

        btnSeleccionarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sandwicheria/resources/accept_16.png"))); // NOI18N
        btnSeleccionarProducto.setText("Seleccionar");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSeleccionarProducto)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSeleccionarProducto)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSeleccionarProducto;
    private javax.swing.JTextField fdCodigo;
    private javax.swing.JTextField fdDescripcion;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbProductos;
    // End of variables declaration//GEN-END:variables

    
}
