package it.univr.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Finestra_Gioco extends JFrame {
	
	private Pannello_Gioco pannello;
	
	public Finestra_Gioco()	//costruisce una finestra che si compone di un pannello damiera e da una barra dei menu.
	{
		setLayout(new BorderLayout());
		setSize(625, 645);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		this.setJMenuBar(barraMenu());
		pannello=new Pannello_Gioco();
		this.add(pannello, BorderLayout.CENTER);
		
	}

	public JMenuBar barraMenu()	//costruisce la barra dei menu.
	{
		JMenu partita=new JMenu("Partita");
		JMenuItem nuova=new JMenuItem("Nuova partita");
		nuova.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)	//riporta alla situazione iniziale il pannello.
			{
				pannello.reset();
				setContentPane(pannello);
				return;				
			}
			
		});
		
		JMenuItem patta=new JMenuItem("Chiedi patta");
		patta.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//valuta, empiricamente, la possibilità di accettare una proposta di pareggio da parte
				//dell'utente. Si ricorda che l'intelligenza artificiale non ha facoltà di compiere la
				//medesima azione. Questa scelta progettuale è volta a non infastidire l'utente durante
				//la partita.
				
				if(pannello.getDamiera().getNumPezzi()[0]<5 || pannello.getDamiera().getNumPezzi()[1]<5)
					if(pannello.getDamiera().getNumPezzi()[0]<=pannello.getDamiera().getNumPezzi()[1])
					{
						new Finestra_Patta(true);
						pannello.reset();
						setContentPane(pannello);
						return;
					}
				
				new Finestra_Patta(false);
			}
			
		});
		
		JMenuItem resa=new JMenuItem("Resa");
		resa.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//informa l'utente della sua disfatta e resetta il campo di gioco.
				
				new Finestra_Sconfitta();
				pannello.reset();
				setContentPane(pannello);
				return;
			}
			
		});
		
		JMenuItem esci=new JMenuItem("Esci");
		esci.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//permette di terminare il programma.
				System.exit(0);				
			}
			
		});
		
		partita.add(nuova);
		partita.add(patta);
		partita.add(resa);
		partita.add(esci);
		
		JMenu info=new JMenu("Informazioni");
		JMenuItem regole=new JMenuItem("Regole");
		regole.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Finestra_Regole();
			}
			
		});
		
		JMenuItem autori=new JMenuItem("Autori");
		autori.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Finestra_Autori();
			}
			
		});
		
		info.add(regole);
		info.add(autori);
		
		JMenuBar barra=new JMenuBar();
		barra.add(partita);
		barra.add(info);
		
		return barra;
	}
	
	public static void main(String[] args)
	{	
		Finestra_Gioco fin=new Finestra_Gioco();
		fin.setVisible(true);
	}

}
