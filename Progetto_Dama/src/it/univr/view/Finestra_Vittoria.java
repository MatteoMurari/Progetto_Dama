package it.univr.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Finestra_Vittoria extends JFrame {
	
	public Finestra_Vittoria()
	{
		this.setLayout(new BorderLayout());
		this.setSize(625, 100);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel etichetta=new JLabel("Complimenti! Hai vinto! Speriamo che ti sia divertito.");
		etichetta.setHorizontalAlignment(JLabel.CENTER);
		this.add(etichetta);
		setVisible(true);
	}

}
