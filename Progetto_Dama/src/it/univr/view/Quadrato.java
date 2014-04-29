package it.univr.view;

import java.awt.Color;

import it.univr.model.Dama;
import it.univr.model.Damiera;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Quadrato extends JButton {
	
	private Damiera damiera;
	private int riga;
	private int colonna;
	private boolean attivo;
	
	public Quadrato(Damiera dam, int r, int c)
	{
		damiera=dam;
		riga=r;
		colonna=c;
		attivo=false;
		
		this.setOpaque(true);
		
		if(damiera.getPosizione(riga, colonna).getSfondo()==false)	//assegnamento del background.
			this.setBackground(Color.BLACK);
		else
		{
			setEnabled(false);
			this.setBackground(new Color(191, 175, 50));
		}
		
		if(damiera.getPosizione(riga, colonna).vuota()==false)
			if(damiera.getPosizione(riga, colonna).getColorePezzo()==false)
				if((damiera.getPosizione(riga, colonna).getPezzo() instanceof Dama)==false)	//assegnamento immagine corretta.
					this.setIcon(new ImageIcon("immagini/Lannister.png"));
				else
					this.setIcon(new ImageIcon("immagini/LannisterDama.png"));
			else
				if((damiera.getPosizione(riga, colonna).getPezzo() instanceof Dama)==false)
					this.setIcon(new ImageIcon("immagini/Stark.png"));
				else
					this.setIcon(new ImageIcon("immagini/StarkDama.png"));
		
		this.setBorder(null);
	}
	
	public int getRiga()	//ritorna la riga.
	{
		return riga;
	}
	
	public int getColonna()	//ritorna la colonna.
	{
		return colonna;
	}
	
	public boolean getAttivo()	//ritorno l'attributo attivo.
	{
		return attivo;
	}
	
	public void setAttivo(boolean x)	//imposta come selezionato o meno un quadrato.
	{
		attivo=x;
	}
	

}
