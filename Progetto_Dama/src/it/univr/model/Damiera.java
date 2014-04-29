package it.univr.model;

public class Damiera {
	
	private Posizione damiera[][]=new Posizione[8][8];
	int numeroPezzi[]=new int[2];	//il primo numero per i neri, il secondo per i bianchi.
	
	public Damiera()
	{
		numeroPezzi[0]=12;
		numeroPezzi[1]=12;
		
		for(int riga=0;riga<8;riga++)
			for(int colonna=0;colonna<8;colonna++)
				if((riga+colonna)%2==0)
				{
					damiera[riga][colonna]=new Posizione(riga, colonna, false);
					
					if(riga<3)
						damiera[riga][colonna].aggiungi(new Pezzo(false));
					
					if(riga>4)
						damiera[riga][colonna].aggiungi(new Pezzo(true));
				}
				else
					damiera[riga][colonna]=new Posizione(riga, colonna, true);
	}
	
	public Damiera(Damiera copia)
	{
		numeroPezzi[0]=copia.numeroPezzi[0];
		numeroPezzi[1]=copia.numeroPezzi[1];
		
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				damiera[i][j]=new Posizione(copia.damiera[i][j]);
	}
	
	public Posizione getPosizione(int riga, int colonna) throws FuoriDamException
	{
		//ritorna la casella della damiera specificata dagli argomenti.
		
		if(riga<0 || riga>7 || colonna<0 || colonna>7)
			throw new FuoriDamException();
		else
			return damiera[riga][colonna];
	}
	
	public void muovi(Posizione inizio, Posizione fine)
	{
		muoviDam(getPosizione(inizio.getRiga(), inizio.getColonna()), getPosizione(fine.getRiga(), fine.getColonna()));
	}
	
	public void muoviDam(Posizione inizio, Posizione fine)
	{
		fine.aggiungi(inizio.getPezzo());
		inizio.elimina();
		promozione(fine);
	}
	
	private void promozione(Posizione fine)
	{
		if(!(fine.getPezzo() instanceof Dama) && (fine.getRiga()==0 || fine.getRiga()==7))
			fine.aggiungi(new Dama(fine.getColorePezzo()));		
	}

	public void presa(Posizione inizio, Posizione mang, Posizione fine)
	{
		presaDam(getPosizione(inizio.getRiga(), inizio.getColonna()), getPosizione(mang.getRiga(), mang.getColonna()), getPosizione(fine.getRiga(), fine.getColonna()));
	}
	
	public void presaDam(Posizione inizio, Posizione mang, Posizione fine)
	{
		muovi(inizio, fine);
		
		if(damiera[mang.getRiga()][mang.getColonna()].getColorePezzo())
			numeroPezzi[1]--;
		else
			numeroPezzi[0]--;
		
		damiera[mang.getRiga()][mang.getColonna()].elimina();
		promozione(fine);
	}
	
	public int[] getNumPezzi()
	{
		return numeroPezzi;
	}

}
