package sandwicheria.librerias;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class StripedTable extends JTable
{

    public StripedTable() {
        super();
    }

    
    public StripedTable(DefaultTableModel defaultTableModel) {
        super(defaultTableModel);
    }

    
  public StripedTable( String[][] data, String[] fields )
  {
    super( data, fields );
    setFillsViewportHeight( true ); //to show the empty space of the table 
  }


  @Override
  public void paintComponent( Graphics g )
  {
    super.paintComponent( g );

    paintEmptyRows( g );
  }


  public void paintEmptyRows( Graphics g )
  {
    Graphics newGraphics = g.create();
    newGraphics.setColor( UIManager.getColor( "Table.gridColor" ) );

    Rectangle rectOfLastRow = getCellRect( getRowCount() - 1, 0, true );
    int firstNonExistentRowY = rectOfLastRow.y; //the top Y-coordinate of the first empty tablerow

    if ( getVisibleRect().height > firstNonExistentRowY ) //only paint the grid if empty space is visible
    {
      //fill the rows alternating and paint the row-lines:
      int rowYToDraw = (firstNonExistentRowY - 1) + getRowHeight(); //minus 1 otherwise the first empty row is one pixel to high
      int actualRow = getRowCount() - 1; //to continue the stripes from the area with table-data

      while ( rowYToDraw < getHeight() )
      {
        if ( actualRow % 2 == 0 )
        {
          newGraphics.setColor( Color.ORANGE ); //change this to another color (Color.YELLOW, anyone?) to show that only the free space is painted
          newGraphics.fillRect( 0, rowYToDraw, getWidth(), getRowHeight() );
          newGraphics.setColor( UIManager.getColor( "Table.gridColor" ) );
        }

        newGraphics.drawLine( 0, rowYToDraw, getWidth(), rowYToDraw );

        rowYToDraw += getRowHeight();
        actualRow++;
      }


      //paint the column-lines:
      int x = 0;
      for ( int i = 0; i < getColumnCount(); i++ )
      {
        TableColumn column = getColumnModel().getColumn( i );
        x += column.getWidth(); //add the column width to the x-coordinate

        newGraphics.drawLine( x - 1, firstNonExistentRowY, x - 1, getHeight() );
      }

      newGraphics.dispose();

    } //if empty space is visible

  } //paintEmptyRows



  public Component prepareRenderer( TableCellRenderer renderer, int row, int column )
  {
    Component c = super.prepareRenderer( renderer, row, column );

    if ( !isRowSelected( row ) )
    {
      c.setBackground( row % 2 == 0 ? getBackground() : Color.ORANGE );
    }

    return c;
  }

}