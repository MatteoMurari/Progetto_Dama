package it.univr.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Finestra_Autori extends JFrame {
	
	public Finestra_Autori()
	{
		this.setLayout(new BorderLayout());
		this.setSize(625, 100);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel etichetta=new JLabel("Un programma realizzato da Matteo Murari e Kevin Mansoldo.");
		etichetta.setHorizontalAlignment(JLabel.CENTER);
		this.add(etichetta);
		setVisible(true);
	}

}
