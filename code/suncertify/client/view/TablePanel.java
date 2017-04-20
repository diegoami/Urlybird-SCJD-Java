package suncertify.client.view;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


/**
 * The panel containing the table, inside a scrollpane,
 * listing all the rooms which can be booked
 *
 * @author Diego Amicabile
 *
 */
public class TablePanel extends JPanel {
    private JTable table;
    private ListSelectionListener listSelectionListener;

    /**
     * The table panel constructor
     * @param tableModel the table model for the contained table
     * @param newListSelectionListener the listener for a selection event in the JTAble
     */
    public TablePanel(TableModel tableModel,
        ListSelectionListener newListSelectionListener) {
        super(new GridLayout(1, 0));
        listSelectionListener = newListSelectionListener;
        table = new JTable(tableModel);

        setupTable();

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
    }

    /**
    * Getter for the selected row in the table
    * @return the selected row
    */
    public int getSelectedRow() {
        return this.table.getSelectedRow();
    }

    private void setupTable() {
        TableColumnModel columnModel = table.getColumnModel();

        columnModel.getColumn(0).setMinWidth(160);
        columnModel.getColumn(1).setMinWidth(80);
        columnModel.getColumn(4).setMinWidth(60);
        columnModel.getColumn(5).setMinWidth(70);
        columnModel.getColumn(6).setMinWidth(70);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel rowSelectionModel = table.getSelectionModel();

        rowSelectionModel.addListSelectionListener(listSelectionListener);
    }
}
