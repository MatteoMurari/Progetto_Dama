package it.univr.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import it.univr.controller.*;
import it.univr.model.Damiera;
import it.univr.model.Mossa;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Pannello_Gioco extends JPanel {
	
	private Giocatore_Computer computer;
	private Fai_Mossa arbitro;
	private Damiera damiera;
	private Mosse_Possibili listaMosse;
	private Quadrato[][] damieraGrafica;
	private Quadrato[] mossa;
	
	public Pannello_Gioco()	//costruttore che inizializza i campi del pannello.
	{
		setLayout(new GridLayout(8,8));
		reset();	
	}

	private void costruisciDamiera() //si occupa della costruzione della damiera del pannello.
	{	
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				damieraGrafica[i][j]=new Quadrato(damiera, i, j);
				this.add(damieraGrafica[i][j]);
				aggiungiListener(i, j, damieraGrafica[i][j]);
			}
		
		try	//una volta costruita la damiera, con i listener adeguati, si verifica se l'utente può compiere almeno una mossa.
		{
			for(int i=0;i<8;i++)
				for(int j=0;j<8;j++)
					if(damiera.getPosizione(i, j).getPezzo()!=null && damiera.getPosizione(i, j).getColorePezzo())
						if(new Mosse_Possibili(damiera, i, j, true).vuota()==false)
							return;
			
			throw new NoSuchElementException();
		}
		catch(NoSuchElementException e) //si attiva quando l'utente non può muovere (di conseguenza, perde).
		{
			new Finestra_Sconfitta();
			return;
		}
		
	}
	
	private void aggiungiListener(int x, int y, Quadrato quad)	//il metodo si incarica di aggiungere un adeguato listener ad una certa posizione.
	{
		final int i=x;
		final int j=y;
		
		damieraGrafica[i][j].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(damiera.getPosizione(i, j).vuota()==false && damiera.getPosizione(i, j).getColorePezzo() && damieraGrafica[i][j].getAttivo()==false)
				{
					//se pezzo bianco non ancora selezionato: si imposta come attiva
					damieraGrafica[i][j].setAttivo(true);
					damieraGrafica[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
					
					if(mossa[0]==null)	//se non era già stata impostata una mossa
					{
						mossa[0]=damieraGrafica[i][j];
						mossa[1]=null;
						listaMosse=new Mosse_Possibili(damiera, i, j, true);
						illuminaDestinazioni(listaMosse, true);
					}
					else
					{
						mossa[1]=damieraGrafica[i][j];
						verifica();
					}
				}
				else
					if(damiera.getPosizione(i, j).vuota()==false && damiera.getPosizione(i, j).getColorePezzo() && damieraGrafica[i][j].getAttivo())
					{
						//se pezzo bianco già selezionato: si resetta la mossa precedentemente memorizzata.
						mossa[0]=null;
						mossa[1]=null;
						illuminaDestinazioni(listaMosse, false);
						damieraGrafica[i][j].setAttivo(false);
						damieraGrafica[i][j].setBorder(null);
						listaMosse=null;
					}
					else
						if(!damiera.getPosizione(i, j).vuota() && !damiera.getPosizione(i, j).getColorePezzo()){}	//Se pezzo nero: non si compie nessuna azione.
						else
						{
							//casella vuota: si imposta come destinazione.
							damieraGrafica[i][j].setAttivo(true);
							mossa[1]=damieraGrafica[i][j];
							verifica();
						}
					}
			
			private void verifica()	//verifica che la mossa salvata in mossa[] sia valida e, se lo è, la attua.
			{
				if(mossaLegale())
				{
					Mossa corrente=null;
					for(Mossa mos : listaMosse)
						if(mos.getFine().getRiga()==mossa[1].getRiga() && mos.getFine().getColonna()==mossa[1].getColonna())
							corrente=mos;
					
					arbitro.esegui(damiera, corrente); //esecuzione mossa
					
					mossa[0].setBorder(null);
					illuminaDestinazioni(listaMosse, false);
					mossa[0].setAttivo(false);
					mossa[1].setAttivo(true);
					
					aggiornaDam();
					
					if(corrente.getMangiabili()>0) //simula possibili prese multiple creando una nuova lista mosse riferita alla posizione finale della
												   //precendente mossa.
					{
						mossa[0]=mossa[1];
						mossa[1]=null;
						mossa[0].setAttivo(true);
						mossa[0].setBorder(BorderFactory.createLineBorder(Color.GREEN.brighter(), 4));
						listaMosse=new Mosse_Possibili(damiera, mossa[0].getRiga(), mossa[0].getColonna());
						listaMosse.controlloPreseBianche(mossa[0].getRiga(), mossa[0].getColonna(), 0, null, true);
						
						if(listaMosse.vuota()==false)
						{
							illuminaDestinazioni(listaMosse, true);
							verifica();
						}
						else	//se non ci sono altre prese da fare, il computer si attiva e muove.
						{
							mossa[0]=null;
							computer.turnoComputer();
							aggiornaDam();
						}
					}
					else	//mossa del computer (nel caso per l'utente non vi fossero prese multiple da fare).
					{
						mossa[0]=null;
						mossa[1]=null;
						aggiornaDam();
						computer.turnoComputer();
						aggiornaDam();
					}
				}
				else	//se la mossa specificata dall'utente non risulta legale, vengono resettate le opportune varibili
				{
					if(damiera.getPosizione(mossa[1].getRiga(), mossa[1].getColonna()).getPezzo()==null)	//caso 1: destinazione vuota.
					{
						damieraGrafica[mossa[1].getRiga()][mossa[1].getColonna()].setBorder(null);
						mossa[1].setAttivo(false);
						mossa[1]=null;
					}
					else
					{
						if(damiera.getPosizione(mossa[1].getRiga(), mossa[1].getColonna()).getColorePezzo()==
								damiera.getPosizione(mossa[0].getRiga(), mossa[0].getColonna()).getColorePezzo())	//caso 2: pezzi dello stesso colore.
						{
							illuminaDestinazioni(listaMosse, false);
							damieraGrafica[mossa[0].getRiga()][mossa[0].getColonna()].setBorder(null);
							damieraGrafica[mossa[0].getRiga()][mossa[0].getColonna()].setAttivo(false);
							mossa[0]=mossa[1];
							mossa[1]=null;
							listaMosse=new Mosse_Possibili(damiera, mossa[0].getRiga(), mossa[0].getColonna(), true);
							illuminaDestinazioni(listaMosse, true);
						}
					}
				}
			}		
								
		});
	}	

	private boolean mossaLegale()	//verifica se all'interno della lista di mosse possibili (creata con l'aggiunta dei listener),
									//esista una mossa compatibile con quella scelta.
	{
		try
		{
			for(Mossa mos : listaMosse)
				if(mos.getFine().equals(damiera.getPosizione(mossa[1].getRiga(), mossa[1].getColonna())))
					return true;
			
			return false;
		}
		catch(NullPointerException e)
		{
			return true;
		}
	}

	protected void aggiornaDam()	//aggiorna la damiera, rimuovendo e ricostruendo le caselle.
	{
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				this.remove(damieraGrafica[i][j]);
		
		costruisciDamiera();
	}

	public void reset()	//riporta il pannello alla situazione di inizio partita.
	{
		this.removeAll();
		
		damiera=new Damiera();
		computer=new Giocatore_Computer(damiera);
		damieraGrafica=new Quadrato[8][8];
		arbitro=new Fai_Mossa();
		mossa=new Quadrato[2];
		
		costruisciDamiera();
	}

	protected void illuminaDestinazioni(Mosse_Possibili mos, boolean acceso)	//si occupa di illuminare o spegnere le caselle destinazione,
																				//secondo quanto indicato dall'argomento booleano acceso.
	{
		for(Mossa m : mos)
		{
			if(acceso)
				damieraGrafica[m.getFine().getRiga()][m.getFine().getColonna()].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));
			else
				damieraGrafica[m.getFine().getRiga()][m.getFine().getColonna()].setBorder(null);
		}		
	}
	
	public Damiera getDamiera()	//ritorna la damiera (logica).
	{
		return damiera;
	}

}
