package it.univr.model;

public class Mossa implements Comparable<Mossa> {
	
	private Posizione inizio;
	private Posizione fine;
	private int mangiabili=0;
	private Posizione[] percorso;
	
	public Mossa(Posizione x, Posizione y, Posizione[] p, int m)
	{
		inizio=x;
		fine=y;
		mangiabili=m;
		percorso=p;
	}
	
	public boolean equals(Object other)
	{
		if(other==null || !(other instanceof Mossa))
			return false;
		
		Mossa mos=(Mossa) other;
		
		if(percorso.length!=mos.percorso.length)
			return false;
		
		for(int i=0;i<percorso.length;i++)
		{
			if(percorso[i].getRiga()!=mos.percorso[i].getRiga() || percorso[i].getColonna()!=mos.percorso[i].getColonna())
				return false;
		}
		
		return true;
	}

	public int compareTo(Mossa other)
	{
		if(mangiabili-other.mangiabili==0)
			return 1;
		else
			return (mangiabili-other.mangiabili);
	}
	
	public void setMangiabili(int nuova)
	{
		mangiabili=nuova;
	}

	public int getMangiabili()
	{
		return mangiabili;
	}

	public Posizione getFine()
	{
		return fine;
	}

	public Posizione getInizio()
	{
		return inizio;
	}	

	public Posizione[] getPercorso()
	{
		return percorso;
	}

}
