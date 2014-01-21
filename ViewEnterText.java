// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import java.awt.Color;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.*;	

class ViewEnterText extends JPanel implements IView {

	private JLabel instruction;
	// the view's main user interface
	private JButton guess;
	//private JTextField texts = new JTextField[5];
	private JTextField text;
	// the model that this view is showing
	private Model model;

	private JButton hint;
	
	ViewEnterText(Model model_) {

		// set the model 
		model = model_;

		// create the view UI
		instruction = new JLabel("Input Alphabetical Characters: ");
		guess = new JButton("Guess!");
		text = new JTextField(5);
		hint = new JButton("Hint = Cyan");
		/*
		for (int i =0 ; i < model_.NUM_LETTERS; i++) {
			texts[i] = new JTextField(1);
		
		}*/
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBackground(Color.GRAY);
		// install buttons
		/*
		for (int i =0 ; i < model_.NUM_LETTERS; i++) {
			this.add(texts[i]);
		}*/
		this.add(instruction);
		this.add(text);
		this.add(guess);
		this.add(hint);


		//install listeners	

		hint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.hint();
			}
		});

		text.setFocusable(true);
		text.addKeyListener(new KeyAdapter() {

			
			//FIX THIS!!!!
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode()>= 65 && e.getKeyCode() <= 90) {
					char c = (char)e.getKeyCode();
					model.insertGuessChar(c);
				} else if (e.getKeyCode()>= 97 && e.getKeyCode() <= 122) {
					e.setKeyCode(e.getKeyCode() -32);
					char c = (char)e.getKeyCode();

					model.insertGuessChar(c);
				} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					// backspace
					model.removeGuessChar();

				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					model.guess();
					

				} else {
					//model.addToLog("Invalid Character - I'm not going to read it!");
				}
				//text.setText(model.getMyGuess());
			}
			

		});

		guess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				//System.out.println(" ViewEnterText: Pressed Guess! myGuess is " + model.getMyGuess());
				model.guess();
				//text.setText("");
			}
		});
		
		

	} 

	// IView interface 
	public void updateView() {
		//String s = model.getMyGuess();
		//this.text.setText(s);
		

		if (model.getNeedRestart()) {
			text.setEditable(false);
			instruction.setText("Game Over: Select a MENU OPTION ");
			//text.setText("Please Select a MENU OPTION");
		} else {
			instruction.setText("Input Alphabetical Characters: ");
			text.setEditable(true);
		}

		if(model.getMyGuess().equals("")) {
			this.text.setText("");

		} else {
		}
		
		//System.out.println(" ViewEnterText: updateView");
		
	}
} 
