package it.univr.controller;

import it.univr.model.*;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Mosse_Possibili implements Iterable<Mossa> {
	
	private Damiera damiera;
	private int riga;
	private int colonna;
	private boolean turno;
	private SortedSet<Mossa> listaMosse=new TreeSet<Mossa>();
	
	public Mosse_Possibili(Damiera dam, int r, int c, boolean t)
	{
		damiera=dam;
		riga=r;
		colonna=c;
		turno=t;
		
		cercaMosse();
	}
	
	public Mosse_Possibili(Damiera dam, int r, int c)
	{
		damiera=dam;
		riga=r;
		colonna=c;
		turno=false;
	}
	
	public SortedSet<Mossa> getLista()	//ritorna la lista di mosse.
	{
		return listaMosse;
	}
	
	public boolean vuota()	//controlla se la lista è vuota.
	{
		if(listaMosse.isEmpty())
			return true;
		else
			return false;
	}
	
	public void cercaMosse()	//in base alla categioria di pezzo specificato, lancia l'opportuna analisi. 
	{
		if(damiera.getPosizione(riga, colonna).getPezzo() instanceof Dama)
		{
			analisiPedinaBianca(turno);
			analisiPedinaNera(turno);	
		}
		else
			if(damiera.getPosizione(riga, colonna).getColorePezzo())
				analisiPedinaBianca(turno);
			else
				analisiPedinaNera(turno);
	}

	public void analisiPedinaBianca(boolean tur)	//controlla le mosse semplici per i pezzi bianchi.
	{
		boolean vicini=false;
		
		try
		{
			if(damiera.getPosizione(riga-1, colonna+1).vuota())
				listaMosse.add(new Mossa(damiera.getPosizione(riga, colonna), damiera.getPosizione(riga-1, colonna+1), null, 0));
			else
				vicini=true;
		}
		catch(FuoriDamException e)
		{}
		
		try
		{
			if(damiera.getPosizione(riga-1, colonna-1).vuota())
				listaMosse.add(new Mossa(damiera.getPosizione(riga, colonna), damiera.getPosizione(riga-1, colonna-1), null, 0));
			else
				vicini=true;
		}
		catch(FuoriDamException e)
		{}
		
		if(vicini)
			controlloPreseBianche(riga, colonna, 0, null, tur);
	}

	public void analisiPedinaNera(boolean tur)	//controlla le mosse semplici per i pezzi neri.
	{
		boolean vicini=false;
		
		try
		{
			if(damiera.getPosizione(riga+1, colonna+1).vuota())
				listaMosse.add(new Mossa(damiera.getPosizione(riga, colonna), damiera.getPosizione(riga+1, colonna+1), null, 0));
			else
				vicini=true;
		}
		catch(FuoriDamException e)
		{}
		
		try
		{
			if(damiera.getPosizione(riga+1, colonna-1).vuota())
				listaMosse.add(new Mossa(damiera.getPosizione(riga, colonna), damiera.getPosizione(riga+1, colonna-1), null, 0));
			else
				vicini=true;
		}
		catch(FuoriDamException e)
		{}
		
		if(vicini)
			controlloPreseNere(riga, colonna, 0, null, tur);
	}
	
	public void controlloPreseBianche(int x, int y, int pun, Posizione[] perc, boolean tur)
	{
		//controlla le eventuali prese possibili da parte del giocatore bianco.
		
		int punti;
		Posizione[] nuovoPercorso;
		
		try
		{
			if(perc==null)
			{
				nuovoPercorso=new Posizione[2];
				nuovoPercorso[0]=damiera.getPosizione(x, y);
			}
			else
			{
				nuovoPercorso=new Posizione[perc.length+2];
				copiaPercorso(perc, nuovoPercorso);
				nuovoPercorso[nuovoPercorso.length-2]=damiera.getPosizione(x, y);
			}
			
			if(damiera.getPosizione(x-1, y+1).vuota()==false)
				if(damiera.getPosizione(x-1, y+1).getColorePezzo()!=nuovoPercorso[0].getColorePezzo())
					if(damiera.getPosizione(x-2, y+2).vuota())
					{
						nuovoPercorso[nuovoPercorso.length-1]=damiera.getPosizione(x-1, y+1);
						punti=damiera.getPosizione(x-1, y+1).getPezzo() instanceof Dama ? pun+5 : pun+1;
						listaMosse.add(new Mossa(damiera.getPosizione(x, y), damiera.getPosizione(x-2, y+2), nuovoPercorso, punti));
						
						if(tur)
						{
							if(damiera.getPosizione(x,y).getPezzo() instanceof Dama)
								controlloPreseNere(x-2, y+2, punti, nuovoPercorso, tur);
							
								controlloPreseBianche(x-2, y+2, punti,nuovoPercorso, tur);
						}
							
					}
		}
		catch(FuoriDamException e)
		{}
		
		try
		{
			if(perc==null)
			{
				nuovoPercorso=new Posizione[2];
				nuovoPercorso[0]=damiera.getPosizione(x, y);
			}
			else
			{
				nuovoPercorso=new Posizione[perc.length+2];
				copiaPercorso(perc, nuovoPercorso);
				nuovoPercorso[nuovoPercorso.length-2]=damiera.getPosizione(x, y);
			}
			
			if(damiera.getPosizione(x-1, y-1).vuota()==false)
				if(damiera.getPosizione(x-1, y-1).getColorePezzo()!=nuovoPercorso[0].getColorePezzo())
					if(damiera.getPosizione(x-2, y-2).vuota())
					{
						nuovoPercorso[nuovoPercorso.length-1]=damiera.getPosizione(x-1, y-1);
						punti=damiera.getPosizione(x-1, y-1).getPezzo() instanceof Dama ? pun+5 : pun+1;
						listaMosse.add(new Mossa(damiera.getPosizione(x, y), damiera.getPosizione(x-2, y-2), nuovoPercorso, punti));
						
						if(tur)
						{
							if(damiera.getPosizione(x,y).getPezzo() instanceof Dama)
								controlloPreseNere(x-2, y-2, punti, nuovoPercorso, tur);
							
								controlloPreseBianche(x-2, y-2, punti,nuovoPercorso, tur);
						}
							
					}
		}
		catch(FuoriDamException e)
		{}
	}
	
	public void controlloPreseNere(int x, int y, int pun, Posizione[] perc, boolean tur)
	{
		//controlla le eventuali prese possibili da parte del giocatore nero.
		
		int punti;
		Posizione[] nuovoPercorso;
		
		try
		{
			if(perc==null)
			{
				nuovoPercorso=new Posizione[2];
				nuovoPercorso[0]=damiera.getPosizione(x, y);
			}
			else
			{
				nuovoPercorso=new Posizione[perc.length+2];
				copiaPercorso(perc, nuovoPercorso);
				nuovoPercorso[nuovoPercorso.length-2]=damiera.getPosizione(x, y);
			}
			
			if(damiera.getPosizione(x+1, y+1).vuota()==false)
				if(damiera.getPosizione(x+1, y+1).getColorePezzo()!=nuovoPercorso[0].getColorePezzo())
					if(damiera.getPosizione(x+2, y+2).vuota())
					{
						nuovoPercorso[nuovoPercorso.length-1]=damiera.getPosizione(x+1, y+1);
						punti=damiera.getPosizione(x+1, y+1).getPezzo() instanceof Dama ? pun+5 : pun+1;
						listaMosse.add(new Mossa(damiera.getPosizione(x, y), damiera.getPosizione(x+2, y+2), nuovoPercorso, punti));
						
						if(tur)
						{
							if(damiera.getPosizione(x,y).getPezzo() instanceof Dama)
								controlloPreseBianche(x+2, y+2, punti, nuovoPercorso, tur);
							
								controlloPreseNere(x+2, y+2, punti,nuovoPercorso, tur);
						}
							
					}
		}
		catch(FuoriDamException e)
		{}
		
		try
		{
			if(perc==null)
			{
				nuovoPercorso=new Posizione[2];
				nuovoPercorso[0]=damiera.getPosizione(x, y);
			}
			else
			{
				nuovoPercorso=new Posizione[perc.length+2];
				copiaPercorso(perc, nuovoPercorso);
				nuovoPercorso[nuovoPercorso.length-2]=damiera.getPosizione(x, y);
			}
			
			if(damiera.getPosizione(x+1, y-1).vuota()==false)
				if(damiera.getPosizione(x+1, y-1).getColorePezzo()!=nuovoPercorso[0].getColorePezzo())
					if(damiera.getPosizione(x+2, y-2).vuota())
					{
						nuovoPercorso[nuovoPercorso.length-1]=damiera.getPosizione(x+1, y-1);
						punti=damiera.getPosizione(x+1, y-1).getPezzo() instanceof Dama ? pun+5 : pun+1;
						listaMosse.add(new Mossa(damiera.getPosizione(x, y), damiera.getPosizione(x+2, y-2), nuovoPercorso, punti));
						
						if(tur)
						{
							if(damiera.getPosizione(x,y).getPezzo() instanceof Dama)
								controlloPreseBianche(x+2, y-2, punti, nuovoPercorso, tur);
							
								controlloPreseNere(x+2, y-2, punti, nuovoPercorso, tur);
						}
							
					}
		}
		catch(FuoriDamException e)
		{}
	}

	private void copiaPercorso(Posizione[] perc, Posizione[] nuovo)	//copia nell'array nuovo l'intero array perc.
	{
		for(int i=0;i<perc.length;i++)
			nuovo[i]=new Posizione(perc[i]);
	}

	public Iterator<Mossa> iterator()
	{
		return listaMosse.iterator();
	}

}
