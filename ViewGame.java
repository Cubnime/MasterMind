import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

class ViewGame extends JPanel implements IView {
	private Model model;
	private String[] columnName = {"Guess#", "My Guess", "Exact", "Partial", "Message"};
	private Object[][] data = {};
	private JTable table = new JTable(data, columnName);
	private DefaultTableModel defaultmodel = new DefaultTableModel(data,columnName);
	
	ViewGame(Model model_) {

		super(new GridLayout(1,0));

		
		
		table.setModel(this.defaultmodel);
		table.setFont(new Font("Serif", Font.BOLD, 12));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);

		this.add(scrollPane);

		// add title
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
														"Jotto Game",
														TitledBorder.CENTER,
														TitledBorder.TOP));

		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		model=model_;
	}

// IView interface
	public void updateView() {

		// remove existing list of words
		int sizeoftablemodel =defaultmodel.getRowCount();
		for (int i = sizeoftablemodel - 1; i >= 0; i--) {
			defaultmodel.removeRow(i);
		}

		if (this.model.getLog() == null || this.model.getLog().length == 0) {
			return;
		}

		for (String m: this.model.getLog()) {
			//System.out.println (" ViewGame: updateView log is " + m);
			Vector newRow = new Vector();
			String[] split = m.split(",");
			for (String c: split) {
				newRow.add(c);
			}
			defaultmodel.addRow(newRow);
		}
	}
}