package it.univr.model;

public class Posizione {
	
	private boolean sfondo;
	private Pezzo pezzo;
	private int riga;
	private int colonna;
	
	public Posizione(int x, int y, boolean s)
	{
		sfondo=s;
		riga=x;
		colonna=y;
		pezzo=null;
	}
	
	public Posizione(Posizione altra)
	{
		riga=altra.riga;
		colonna=altra.colonna;
		sfondo=altra.sfondo;
		
		if (altra.pezzo!=null)
		{
			if(altra.pezzo instanceof Dama)
				pezzo=new Dama(altra.pezzo.getColore());
			else
				pezzo=new Pezzo(altra.pezzo.getColore());
		}
		else
			pezzo=null;
	}
	
	public Posizione (Pezzo pedina)
	{
		aggiungi(pedina);
		sfondo=false;
	}
	
	public void elimina()
	{
		pezzo=null;
	}

	public boolean vuota()
	{
		return pezzo==null;
	}
	
	public void aggiungi(Pezzo p)
	{
		pezzo=p;
	}
	
	public boolean equals(Object other)
	{
		if(other==null || !(other instanceof Posizione))
			return false;
		
		Posizione altra=(Posizione) other;
		
		if(riga==altra.riga && colonna==altra.colonna)
			return true;
		
		return false;
	}
	
	public int getRiga()
	{
		return riga;
	}
	
	public int getColonna()
	{
		return colonna;
	}
	
	public Pezzo getPezzo()
	{
		return pezzo;
	}
	
	public boolean getColorePezzo()
	{
		return pezzo.getColore();
	}
	
	public boolean getSfondo()
	{
		return sfondo;
	}

}
