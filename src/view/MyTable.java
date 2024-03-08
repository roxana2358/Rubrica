package view;

import model.Contatto;
import model.RubricaBusiness;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * Classe della tabella dei contatti.
 * Contiene al suo interno una classe privata per l'implementazione del modello della tabella: MyTableModel.
 */
public class MyTable extends JPanel implements Observer {

    private static final long serialVersionUID = -860582511173596607L;
    
	/**
     * Campi privati del Panel.
     */
    private JTable table;
    private MyTableModel myTableModel;

    /**
     * Costruttore del Panel.
     */
    public MyTable() {
        setLayout(new BorderLayout());
        myTableModel = new MyTableModel();

        table = new JTable(myTableModel);
        table.setRowHeight(30);
        table.setFocusable(false);
        Color backgroundColor = new Color(52, 168,83, 255);
        Color gridColor = new Color(0, 103, 29, 255);
        table.setBackground(backgroundColor);
        table.getTableHeader().setBackground(gridColor);
        table.getTableHeader().setForeground(Color.white);
        table.setGridColor(gridColor);

        RubricaBusiness.getInstance().setObserver(this);

        add(new JScrollPane(table));
    }

    /**
     * Metodo che restituisce l'indice della riga selezionata.
     * @return
     */
    public int getSelectedRow() { return this.table.getSelectedRow(); }

    /**
     * Metodo che aggiorna la tabella in caso di modifiche.
     */
    @Override
    public void update() { myTableModel.fireTableDataChanged(); }

    /**
     * Classe di struttura della tabella dei contatti.
     */
    private class MyTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 9103922112746742981L;
        
		/**
         * Campi privati del modello.
         */
        private List<Contatto> contacts;
        private String[] columns;

        /**
         * Costruttore del modello.
         */
        public MyTableModel() {
            columns = new String[]{ "Nome", "Cognome", "Telefono" };
            contacts = RubricaBusiness.getInstance().getContatti();
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Class getColumnClass(int column) {
            return String.class;
        }

        @Override
        public int getRowCount() {
            return contacts.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Contatto p = contacts.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> p.getNome();
                case 1 -> p.getCognome();
                case 2 -> p.getTelefono();
                default -> null;
            };
        }
        
        @Override
       	public void fireTableDataChanged() {
        	this.contacts = RubricaBusiness.getInstance().getContatti();
       		super.fireTableDataChanged();
       	}
    }
}
