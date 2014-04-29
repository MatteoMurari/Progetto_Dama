package it.univr.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Finestra_Regole extends JFrame {
	
	private final static String regole="Ogni informazione sul gioco della dama italiana è disponibile all'indirizzo:\n" +
			   "http://it.wikipedia.org/wiki/Dama_italiana";
	
	public Finestra_Regole()
	{
		this.setLayout(new BorderLayout());
		this.setSize(900, 100);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel etichetta=new JLabel(regole);
		etichetta.setHorizontalAlignment(JLabel.CENTER);
		this.add(etichetta);
		setVisible(true);
	}

}
