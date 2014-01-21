// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

/**
 * Two views with integrated controllers.
 */

import javax.swing.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.*;	

public class Main{

	public static void main(String[] args){	
		JFrame frame = new JFrame("Jotto - y22yin");
		
		// create Model and initialize it
		Model model = new Model();
		
		// create View, tell it about model (and controller)
		View view = new View(model);
		model.addView(view);
		
		//create menu view
		ViewMenu viewmenu = new ViewMenu(model);
		model.addView(viewmenu);

		//create table hint view
		ViewTableHint viewtablehint = new ViewTableHint(model);
		model.addView(viewtablehint);

		// create text enter view
		ViewEnterText viewentertext = new ViewEnterText(model);
		model.addView(viewentertext);

		//create game view
		ViewGame viewgame = new ViewGame(model);
		model.addView(viewgame);

		// create the window
		JPanel p = new JPanel(new BorderLayout());
		frame.getContentPane().add(p);
		p.add(view, BorderLayout.EAST);
		p.add(viewmenu, BorderLayout.NORTH);
		p.add(viewtablehint, BorderLayout.WEST);
		p.add(viewentertext, BorderLayout.SOUTH);
		p.add(viewgame, BorderLayout.CENTER);

		frame.setPreferredSize(new Dimension(1000,600));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} 
}
