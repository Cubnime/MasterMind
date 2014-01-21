// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import java.awt.Color;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.*;	

class ViewMenu extends JPanel implements IView {

	// the view's main user interface
	private JLabel option;
	private JButton newGame; 
	private JButton newGame0; 
	private JButton newGame1; 
	private JButton newGame2; 
	
	
	// the model that this view is showing
	private Model model;
	
	ViewMenu(Model model_) {
		// create the view UI
		option = new JLabel("MENU OPTIONS: ");
		newGame = new JButton("Quit & Start New Game");
		newGame0 = new JButton("New Game Easy");
		newGame1 = new JButton("New Game Medium");
		newGame2 = new JButton("New Game Hard");
		

		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBackground(Color.GRAY);
		// install buttons
		this.add(option);
		this.add(newGame);
		this.add(newGame0);
		this.add(newGame1);
		this.add(newGame2);
		

		// install listeners
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.newGame();
			}
		});

		newGame0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.newGame(0);
			}
		});

		newGame1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.newGame(1);
			}
		});

		newGame2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.newGame(2);
			}
		});
		
		
		// set the model 
		model = model_;

	} 

	// IView interface 
	public void updateView() {
		//System.out.println(" ViewMenu: updateView");
		
	}
} 
