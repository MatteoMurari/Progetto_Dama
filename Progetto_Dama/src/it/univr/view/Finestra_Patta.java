package it.univr.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Finestra_Patta extends JFrame {
	
	public Finestra_Patta(boolean accettata)
	{
		this.setLayout(new BorderLayout());
		this.setSize(625, 100);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel etichetta;
		
		if(accettata)
			etichetta=new JLabel("Accetto la tua proposta di pareggio. Speriamo che ti sia divertito.");
		else
			etichetta=new JLabel("Non posso tirarmi indietro proprio adesso! Continuiamo la partita!");
		
		etichetta.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(etichetta);
		setVisible(true);
	}

}
