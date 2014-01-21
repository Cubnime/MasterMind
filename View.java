//THIS IS THE GRID VIEW
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.Color;	
import javax.swing.BorderFactory;
import javax.swing.border.*;

class View extends JPanel implements IView {

	//private JLabel instruction = new JLabel("Green=Must, Yellow=Maybe, Red=Not");
	// the view's main user interface
	private JButton[] buttons; 
	
	// the model that this view is showing
	private Model model;
	public static final Color[] COLOURS = {
    null, Color.GREEN, Color.ORANGE, Color.RED};

	View(Model model_) {
		// create the view UI
		//button = new JButton("?");
		buttons = new JButton[26];
		for (int i = 0; i < 26; i++) {
			//int ascii = i + 65;
			String letter = "" + (char)(i+65);

			buttons[i] = new JButton(letter);
			buttons[i].setMaximumSize(new Dimension(100, 25));
			buttons[i].setPreferredSize(new Dimension(100, 25));
			buttons[i].setForeground(Color.BLACK);
		}

		this.setLayout(new GridLayout(13,2));

		// install buttons

		//this.add(instruction);
		for (int i = 0; i < 26; i++) {
			this.add(buttons[i]);
			final int myi=i;

			// install listener for each alphabet
			buttons[i].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//model.insertGuessChar((char)(myi+65));
				
				if (buttons[myi].getForeground() == Color.BLACK) {
					buttons[myi].setForeground(Color.GREEN);
				} else if (buttons[myi].getForeground() == Color.GREEN) {
					buttons[myi].setForeground(Color.ORANGE);
				} else if (buttons[myi].getForeground() == Color.ORANGE) {
					buttons[myi].setForeground(Color.RED);
				} else {
					buttons[myi].setForeground(Color.BLACK);
				}
				
			}
		});	
		}
		
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
														"G=Must, Y=Maybe, R=Not",
														TitledBorder.CENTER,
														TitledBorder.TOP));
		// set the model 
		model = model_;
		

	} 

	// IView interface 
	public void updateView() {
		//System.out.println(" View: updateView");
		// if myguess is empty then change all colours to null
		if (this.model.getMyGuess().equals("") && this.model.getAttempt() == 0) {
			for (int i =0; i < 26; i++) {
				buttons[i].setForeground(Color.BLACK);
			}
		}

		if (this.model.getHintWanted()) {
			int[] h = this.model.getHint();
			for (int i=0; i<26; i++) {
				if (h[i] == 1) {
					buttons[i].setForeground(Color.CYAN);
				}
			}
		}

	}
		
} 
