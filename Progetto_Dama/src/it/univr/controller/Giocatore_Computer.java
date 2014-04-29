package it.univr.controller;

import it.univr.model.*;
import it.univr.view.Finestra_Sconfitta;
import it.univr.view.Finestra_Vittoria;

import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

public class Giocatore_Computer {
	
	private SortedSet<Mossa> prese;
	private SortedSet<Mossa> normali;
	private Damiera damiera;
	private Fai_Mossa arbitro=new Fai_Mossa();
	
	public Giocatore_Computer(Damiera dam)
	{
		damiera=dam;
	}
	
	public void turnoComputer()
	{
		//metodo che scorre la damiera e riempie, per ogni mossa valida trovata, 2 liste di mosse differenti.
		//Al termine di un'analisi (delegata a un'altra funzione), sceglie, dalle 2 liste, la mossa che reca
		//maggiore vantaggio. Dopodichè, la esegue.
		
		//Nel caso non fossero disponibili delle mosse, informa l'utente riguardo la sua vittoria.
		
		prese=new TreeSet<Mossa>();
		normali=new TreeSet<Mossa>();
		
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if(damiera.getPosizione(i, j).vuota()==false && damiera.getPosizione(i, j).getColorePezzo()==false)
					analisiMossa(i, j);
		
		try
		{
			if(!prese.isEmpty())
				arbitro.esegui(damiera, prese.last());
			else
				arbitro.esegui(damiera, normali.last());
		}
		catch(NoSuchElementException e)
		{
			new Finestra_Vittoria();
		}
		
	}

	private void analisiMossa(int riga, int colonna)
	{
		//analizza le possibili mosse del pezzo specificato dagli argomenti, calcola tutte le possibili contromosse dell'avversario,
		//aggiornandone il punteggio, e aggiunge tali mosse alle liste precedentemente dichiarate.
		
		try
		{
			Mossa candidata;
			SortedSet<Mossa> listaB=new TreeSet<Mossa>();
			Damiera copia=new Damiera(damiera);
			
			Mosse_Possibili nere=new Mosse_Possibili(damiera, riga, colonna, true);
			
			if (!nere.vuota())
			{
				for(Mossa mos : nere)
				{
					copia=new Damiera(damiera);
					candidata=mos;
					arbitro.esegui(copia, candidata);
					
					for(int i=0;i<8;i++)
						for(int j=0;j<8;j++)
						{
							if(!copia.getPosizione(i, j).getSfondo() && !copia.getPosizione(i, j).vuota() && copia.getPosizione(i, j).getColorePezzo())
							{
								Mosse_Possibili bianche=new Mosse_Possibili(copia, i, j, true);
								if(!bianche.vuota())
									listaB.add(bianche.getLista().last());
				
							}
						}
					
					if(candidata.getPercorso()!=null)
					{
						candidata.setMangiabili(candidata.compareTo(listaB.last()));
						prese.add(candidata);
					}
					else
					{
						candidata.setMangiabili(candidata.compareTo(listaB.last()));
						normali.add(candidata);
					}
				}
			}
		}
		catch(NoSuchElementException e)
		{
			if(damiera.getNumPezzi()[0]==0)
				new Finestra_Vittoria();
			
			if(damiera.getNumPezzi()[1]==0)
				new Finestra_Sconfitta();
		}
	}

}
