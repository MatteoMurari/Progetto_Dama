package it.univr.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Finestra_Sconfitta extends JFrame {
	
	public Finestra_Sconfitta()
	{
		this.setLayout(new BorderLayout());
		this.setSize(625, 100);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel etichetta=new JLabel("Hai perso la partita. Speriamo che ti sia divertito.");
		etichetta.setHorizontalAlignment(JLabel.CENTER);
		this.add(etichetta);
		setVisible(true);
	}

}
