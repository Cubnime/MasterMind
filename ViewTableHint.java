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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

class ViewTableHint extends JPanel implements IView {
	private Model model;
	private String[] columnName = {"Matches", "Guessed"};
	private Object[][] data = {};
	private JTable table = new JTable(data, columnName);
	private DefaultTableModel defaultmodel = new DefaultTableModel(data,columnName);
	
	ViewTableHint(Model model_) {

		super(new GridLayout(1,0));

		
		
		table.setModel(this.defaultmodel);
		table.setPreferredScrollableViewportSize(new Dimension(150, 100));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);

		this.add(scrollPane);

		// add title
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
														"Valid Matches",
														TitledBorder.CENTER,
														TitledBorder.TOP));
		model=model_;
	}

// IView interface
	public void updateView() {

		// remove existing list of words
		int sizeoftablemodel =defaultmodel.getRowCount();
		for (int i = sizeoftablemodel - 1; i >= 0; i--) {
			defaultmodel.removeRow(i);
		}
		if (this.model.getMyGuess() == null || this.model.getMyGuess().isEmpty() || this.model.getMatches() == null) {
			return;
		}

		
		/*Object[][] myData = new Object[this.model.getMatcheSize()][2];
		for (String past: this.model.getMyGuesses()) {
				System.out.println(" my past guesses: " + past);
			}
			*/
		
		for (int i = 0; i < this.model.getMatcheSize(); i++){

			Vector newRow = new Vector();
			String word = this.model.getMatches()[i];
			//System.out.println("  AOIDFJOAJDOISFJD    " + word);
			//myData[i][0] = word; 
			String s = new String(word);

			newRow.add(s);

			
			if (this.model.getMyGuesses() == null) {
				//this.table.getModel().setValueAt(new Boolean(false), i, 1);
				//myData[i][1]=new Boolean(false);
				newRow.add(new Boolean(false));
				defaultmodel.addRow(newRow);
				continue;
			}
			//check in past guesses
			boolean inpast = false;
			for (String item: this.model.getMyGuesses()) {
				
				if (word.equals(item)) {
					inpast=true;
					break;
				} 
			}
			newRow.add(new Boolean(inpast));
			defaultmodel.addRow(newRow);
			
			
		} 
		//System.out.println( " ViewTableHint: updateView");
	}
}
